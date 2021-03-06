package storm.trident.drpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.thrift7.TException;

import storm.trident.drpc.ReturnResultsReducer.ReturnResultsState;
import storm.trident.operation.MultiReducer;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentMultiReducerContext;
import storm.trident.tuple.TridentTuple;
import backtype.storm.Config;
import backtype.storm.drpc.DRPCInvocationsClient;
import backtype.storm.generated.DistributedRPCInvocations;
import backtype.storm.utils.ServiceRegistry;
import backtype.storm.utils.Utils;

@SuppressWarnings({"unchecked","rawtypes"})
public class ReturnResultsReducer implements MultiReducer<ReturnResultsState> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3609246467551371634L;

	public static class ReturnResultsState {
        List<TridentTuple> results = new ArrayList<TridentTuple>();
        String returnInfo;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }
    boolean local;

    Map<List, DRPCInvocationsClient> _clients = new HashMap<List, DRPCInvocationsClient>();
    
    
    @Override
    public void prepare(Map conf, TridentMultiReducerContext context) {
        local = conf.get(Config.STORM_CLUSTER_MODE).equals("local");
    }

    @Override
    public ReturnResultsState init(TridentCollector collector) {
        return new ReturnResultsState();
    }

    @Override
    public void execute(ReturnResultsState state, int streamIndex, TridentTuple input, TridentCollector collector) {
        if(streamIndex==0) {
            state.returnInfo = input.getString(0);
        } else {
            state.results.add(input);
        }
    }

    @Override
    public void complete(ReturnResultsState state, TridentCollector collector) {
        // only one of the multireducers will receive the tuples
        if(state.returnInfo!=null) {
            String result = Utils.to_json(state.results);
            Map retMap = (Map) Utils.from_json(state.returnInfo);
            final String host = (String) retMap.get("host");
            final int port = Utils.getInt(retMap.get("port"));
            String id = (String) retMap.get("id");
            DistributedRPCInvocations.Iface client;
            if(local) {
                client = (DistributedRPCInvocations.Iface) ServiceRegistry.getService(host);
            } else {
				List server = new ArrayList() {/**
					 * 
					 */
					private static final long serialVersionUID = -2178334393663496091L;

				{
                    add(host);
                    add(port);
                }};

                if(!_clients.containsKey(server)) {
                    _clients.put(server, new DRPCInvocationsClient(host, port));
                }
                client = _clients.get(server);
            }

            try {
                client.result(id, result);
            } catch(TException e) {
                collector.reportError(e);
            }
        }
    }

    @Override
    public void cleanup() {
        for(DRPCInvocationsClient c: _clients.values()) {
            c.close();
        }
    }
    
}
