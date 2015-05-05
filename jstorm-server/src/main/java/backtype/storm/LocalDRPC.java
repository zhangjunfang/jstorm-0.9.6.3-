package backtype.storm;


import org.apache.log4j.Logger;
import org.apache.thrift7.TException;

import backtype.storm.generated.DRPCRequest;
import backtype.storm.utils.ServiceRegistry;

import com.alibaba.jstorm.drpc.Drpc;

public class LocalDRPC implements ILocalDRPC {
	private static final Logger LOG = Logger.getLogger(LocalDRPC.class);
	
	private Drpc handler = new Drpc();
	private Thread thread;
	
	private final String serviceId;
	
	public LocalDRPC() {
		
		
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				LOG.info("Begin to init local Drpc");
				try {
					handler.init();
				} catch (Exception e) {
					LOG.info("Failed to  start local drpc");
					System.exit(-1);
				}
				LOG.info("Successfully start local drpc");
			}
		});
		thread.start();

		serviceId = ServiceRegistry.registerService(handler);
	}

	@Override
	public String execute(String functionName, String funcArgs)
			{
		try {
			return handler.execute(functionName, funcArgs);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void result(String id, String result) throws TException {
		
		handler.result(id, result);
	}

	@Override
	public DRPCRequest fetchRequest(String functionName) throws TException {
		
		return handler.fetchRequest(functionName);
	}

	@Override
	public void failRequest(String id) throws TException {
		
		handler.failRequest(id);
	}

	@Override
	public void shutdown() {
		
		ServiceRegistry.unregisterService(this.serviceId);
		this.handler.shutdown();
	}

	@Override
	public String getServiceId() {
		
		return serviceId;
	}

}
