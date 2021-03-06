package com.pcs.analytics.util.test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.Message;

import storm.kafka.KafkaUtils;
import kafka.consumer.KafkaStream;

import com.pcs.deviceframework.datadist.consumer.CoreConsumer;
import com.pcs.deviceframework.datadist.consumer.listener.CoreListener;
import com.pcs.deviceframework.datadist.core.DistributionManager;
import com.pcs.deviceframework.datadist.enums.ConsumerType;
import com.pcs.deviceframework.datadist.enums.DistributorMode;

public class Tester {
	public static void main(String[] args) {

		try{
			String name = "distributor";
			Registry registry = LocateRegistry.getRegistry("localhost",1099);
			//Registry registry = LocateRegistry.getRegistry("localhost",1099);
			DistributionManager brokerManager = (DistributionManager) registry.lookup(name);
			CoreConsumer coreConsumer = brokerManager.getConsumer(DistributorMode.KAFKA, ConsumerType.HIGH_LEVEL);
			coreConsumer.setTopic("live-feed-stream");
			coreConsumer.setThreadCount(1);
			Properties props = new Properties();
			props.put("group.id", "newgroup");
			coreConsumer.setProperties(props);
			TestListener testing = new TestListener();
			testing.setConsumer(coreConsumer);
			testing.init();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	
	}
	
	static class TestListener implements CoreListener{

		private CoreConsumer consumer;

		public CoreConsumer getConsumer() {
			return consumer;
		}

		public void setConsumer(CoreConsumer consumer) {
			consumer.setMessageListener(this);
			this.consumer = consumer;
		}
		
		@Override
		public void consumeData1(List<Message> messages) {
			
		}

		@Override
		public void onMessage(Message message) {
			
		}

		@Override
		public void consumeData(List<KafkaStream<byte[], byte[]>> streams) {

			int i=0;
			for(final KafkaStream stream : streams){
				System.out.println("Started consuming");
				ExecutorService executor;
				executor = Executors.newFixedThreadPool(10);
				executor.submit(new MessageUtil(stream,i));
				i++;
			}
		
		}

		@Override
		public void consumeData(byte[] bytes, long offset) {
			
		}
		public void init(){
			consumer.listen();
		}
		
	}

}
