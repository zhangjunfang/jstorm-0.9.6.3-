package backtype.storm.drpc;

import backtype.storm.Constants;
import backtype.storm.ILocalDRPC;
import backtype.storm.coordination.BatchBoltExecutor;
import backtype.storm.coordination.CoordinatedBolt;
import backtype.storm.coordination.CoordinatedBolt.FinishedCallback;
import backtype.storm.coordination.CoordinatedBolt.IdStreamSpec;
import backtype.storm.coordination.CoordinatedBolt.SourceArgs;
import backtype.storm.coordination.IBatchBolt;
import backtype.storm.generated.StormTopology;
import backtype.storm.generated.StreamInfo;
import backtype.storm.grouping.CustomStreamGrouping;
import backtype.storm.topology.BaseConfigurationDeclarer;
import backtype.storm.topology.BasicBoltExecutor;
import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.InputDeclarer;
import backtype.storm.topology.OutputFieldsGetter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Trident subsumes the functionality provided by this class, so it's deprecated
@Deprecated
@SuppressWarnings({"unchecked","rawtypes"})
public class LinearDRPCTopologyBuilder {
	String _function;
	List<Component> _components = new ArrayList<Component>();

	public LinearDRPCTopologyBuilder(String function) {
		_function = function;
	}

	public LinearDRPCInputDeclarer addBolt(IBatchBolt bolt, Number parallelism) {
		return addBolt(new BatchBoltExecutor(bolt), parallelism);
	}

	public LinearDRPCInputDeclarer addBolt(IBatchBolt bolt) {
		return addBolt(bolt, 1);
	}

	@Deprecated
	public LinearDRPCInputDeclarer addBolt(IRichBolt bolt, Number parallelism) {
		if (parallelism == null)
			parallelism = 1;
		Component component = new Component(bolt, parallelism.intValue());
		_components.add(component);
		return new InputDeclarerImpl(component);
	}

	@Deprecated
	public LinearDRPCInputDeclarer addBolt(IRichBolt bolt) {
		return addBolt(bolt, null);
	}

	public LinearDRPCInputDeclarer addBolt(IBasicBolt bolt, Number parallelism) {
		return addBolt(new BasicBoltExecutor(bolt), parallelism);
	}

	public LinearDRPCInputDeclarer addBolt(IBasicBolt bolt) {
		return addBolt(bolt, null);
	}

	public StormTopology createLocalTopology(ILocalDRPC drpc) {
		return createTopology(new DRPCSpout(_function, drpc));
	}

	public StormTopology createRemoteTopology() {
		return createTopology(new DRPCSpout(_function));
	}

	private StormTopology createTopology(DRPCSpout spout) {
		final String SPOUT_ID = "spout";
		final String PREPARE_ID = "prepare-request";

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(SPOUT_ID, spout);
		builder.setBolt(PREPARE_ID, new PrepareRequest())
				.noneGrouping(SPOUT_ID);
		int i = 0;
		for (; i < _components.size(); i++) {
			Component component = _components.get(i);

			Map<String, SourceArgs> source = new HashMap<String, SourceArgs>();
			if (i == 1) {
				source.put(boltId(i - 1), SourceArgs.single());
			} else if (i >= 2) {
				source.put(boltId(i - 1), SourceArgs.all());
			}
			IdStreamSpec idSpec = null;
			if (i == _components.size() - 1
					&& component.bolt instanceof FinishedCallback) {
				idSpec = IdStreamSpec.makeDetectSpec(PREPARE_ID,
						PrepareRequest.ID_STREAM);
			}
			BoltDeclarer declarer = builder.setBolt(boltId(i),
					new CoordinatedBolt(component.bolt, source, idSpec),
					component.parallelism);

			for (Map conf : component.componentConfs) {
				declarer.addConfigurations(conf);
			}

			if (idSpec != null) {
				declarer.fieldsGrouping(idSpec.getGlobalStreamId()
						.get_componentId(), PrepareRequest.ID_STREAM,
						new Fields("request"));
			}
			if (i == 0 && component.declarations.isEmpty()) {
				declarer.noneGrouping(PREPARE_ID, PrepareRequest.ARGS_STREAM);
			} else {
				String prevId;
				if (i == 0) {
					prevId = PREPARE_ID;
				} else {
					prevId = boltId(i - 1);
				}
				for (InputDeclaration declaration : component.declarations) {
					declaration.declare(prevId, declarer);
				}
			}
			if (i > 0) {
				declarer.directGrouping(boltId(i - 1),
						Constants.COORDINATED_STREAM_ID);
			}
		}

		IRichBolt lastBolt = _components.get(_components.size() - 1).bolt;
		OutputFieldsGetter getter = new OutputFieldsGetter();
		lastBolt.declareOutputFields(getter);
		Map<String, StreamInfo> streams = getter.getFieldsDeclaration();
		if (streams.size() != 1) {
			throw new RuntimeException(
					"Must declare exactly one stream from last bolt in LinearDRPCTopology");
		}
		String outputStream = streams.keySet().iterator().next();
		List<String> fields = streams.get(outputStream).get_output_fields();
		if (fields.size() != 2) {
			throw new RuntimeException(
					"Output stream of last component in LinearDRPCTopology must contain exactly two fields. The first should be the request id, and the second should be the result.");
		}

		builder.setBolt(boltId(i), new JoinResult(PREPARE_ID))
				.fieldsGrouping(boltId(i - 1), outputStream,
						new Fields(fields.get(0)))
				.fieldsGrouping(PREPARE_ID, PrepareRequest.RETURN_STREAM,
						new Fields("request"));
		i++;
		builder.setBolt(boltId(i), new ReturnResults()).noneGrouping(
				boltId(i - 1));
		return builder.createTopology();
	}

	private static String boltId(int index) {
		return "bolt" + index;
	}

	private static class Component {
		public IRichBolt bolt;
		public int parallelism;
		public List<Map> componentConfs;
		public List<InputDeclaration> declarations = new ArrayList<InputDeclaration>();

		public Component(IRichBolt bolt, int parallelism) {
			this.bolt = bolt;
			this.parallelism = parallelism;
			this.componentConfs = new ArrayList();
		}
	}

	private static interface InputDeclaration {
		public void declare(String prevComponent, InputDeclarer declarer);
	}

	private class InputDeclarerImpl extends
			BaseConfigurationDeclarer<LinearDRPCInputDeclarer> implements
			LinearDRPCInputDeclarer {
		Component _component;

		public InputDeclarerImpl(Component component) {
			_component = component;
		}

		@Override
		public LinearDRPCInputDeclarer fieldsGrouping(final Fields fields) {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.fieldsGrouping(prevComponent, fields);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer fieldsGrouping(final String streamId,
				final Fields fields) {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.fieldsGrouping(prevComponent, streamId, fields);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer globalGrouping() {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.globalGrouping(prevComponent);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer globalGrouping(final String streamId) {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.globalGrouping(prevComponent, streamId);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer shuffleGrouping() {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.shuffleGrouping(prevComponent);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer shuffleGrouping(final String streamId) {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.shuffleGrouping(prevComponent, streamId);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer localOrShuffleGrouping() {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.localOrShuffleGrouping(prevComponent);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer localOrShuffleGrouping(
				final String streamId) {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.localOrShuffleGrouping(prevComponent, streamId);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer noneGrouping() {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.noneGrouping(prevComponent);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer noneGrouping(final String streamId) {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.noneGrouping(prevComponent, streamId);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer allGrouping() {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.allGrouping(prevComponent);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer allGrouping(final String streamId) {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.allGrouping(prevComponent, streamId);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer directGrouping() {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.directGrouping(prevComponent);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer directGrouping(final String streamId) {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.directGrouping(prevComponent, streamId);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer customGrouping(
				final CustomStreamGrouping grouping) {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.customGrouping(prevComponent, grouping);
				}
			});
			return this;
		}

		@Override
		public LinearDRPCInputDeclarer customGrouping(final String streamId,
				final CustomStreamGrouping grouping) {
			addDeclaration(new InputDeclaration() {
				@Override
				public void declare(String prevComponent, InputDeclarer declarer) {
					declarer.customGrouping(prevComponent, streamId, grouping);
				}
			});
			return this;
		}

		private void addDeclaration(InputDeclaration declaration) {
			_component.declarations.add(declaration);
		}

		@Override
		public LinearDRPCInputDeclarer addConfigurations(Map conf) {
			_component.componentConfs.add(conf);
			return this;
		}
	}
}
