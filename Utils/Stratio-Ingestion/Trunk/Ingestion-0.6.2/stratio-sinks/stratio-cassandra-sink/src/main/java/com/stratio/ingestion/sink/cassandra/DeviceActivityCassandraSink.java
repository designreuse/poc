/**
 * Copyright (C) 2014 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.ingestion.sink.cassandra;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.CounterGroup;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.conf.ConfigurationException;
import org.apache.flume.instrumentation.SinkCounter;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.net.HostAndPort;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DeviceActivityCassandraSink extends AbstractSink implements
		Configurable {

	private static final Logger log = LoggerFactory
			.getLogger(DeviceActivityCassandraSink.class);

	private static final int DEFAULT_PORT = 9042;
	private static final String DEFAULT_HOST = "localhost:9042";
	private static final int DEFAULT_BATCH_SIZE = 100;
	private static final String CONF_KEY_SPACE = "keyspace";
	private static final String CONF_HOSTS = "hosts";
	private static final String CONF_USERNAME = "username";
	private static final String CONF_PASSWORD = "password";
	private static final String CONF_BATCH_SIZE = "batchSize";
	private static final String CONF_CONSISTENCY_LEVEL = "consistency";
	private static final String DEFAULT_CONSISTENCY_LEVEL = ConsistencyLevel.QUORUM.name();
	Cluster cluster;
	Session session;
	List<CassandraTable> tables;
	private SinkCounter sinkCounter;
	private int batchSize;
	private List<InetSocketAddress> contactPoints;
	private String username;
	private String password;
	private String keyspace;
	private String consistency;
	private final CounterGroup counterGroup = new CounterGroup();
	private final JsonParser parser = new JsonParser();

	public DeviceActivityCassandraSink() {
		super();
	}

	@Override
	public void configure(Context context) {
		contactPoints = new ArrayList<InetSocketAddress>();
		final String hosts = context.getString(CONF_HOSTS, DEFAULT_HOST);
		for (final String host : Splitter.on(',').split(hosts)) {
			try {
				final HostAndPort hostAndPort = HostAndPort.fromString(host)
						.withDefaultPort(DEFAULT_PORT);
				contactPoints.add(new InetSocketAddress(hostAndPort
						.getHostText(), hostAndPort.getPort()));
			} catch (IllegalArgumentException ex) {
				throw new ConfigurationException("Could not parse host: "
						+ host, ex);
			}
		}

		this.username = context.getString(CONF_USERNAME);
		this.password = context.getString(CONF_PASSWORD);
		this.consistency = context.getString(CONF_CONSISTENCY_LEVEL, DEFAULT_CONSISTENCY_LEVEL);
		
		this.keyspace = StringUtils.trimToNull(context
				.getString(CONF_KEY_SPACE));
		this.batchSize = context
				.getInteger(CONF_BATCH_SIZE, DEFAULT_BATCH_SIZE);
		this.sinkCounter = new SinkCounter(this.getName());
	}

	@Override
	public synchronized void start() {

		// Connect to Cassandra cluster
		Cluster.Builder clusterBuilder = Cluster.builder()
				.addContactPointsWithPorts(contactPoints);
		if (!Strings.isNullOrEmpty(username)
				&& !Strings.isNullOrEmpty(password)) {
			clusterBuilder = clusterBuilder.withCredentials(username, password);
		}
		QueryOptions queryOptions = new QueryOptions();
		queryOptions.setConsistencyLevel(ConsistencyLevel.valueOf(consistency));
		
		clusterBuilder = clusterBuilder.withQueryOptions(queryOptions);
		this.cluster = clusterBuilder.build();
		this.session = this.cluster.connect(this.keyspace);
		this.sinkCounter.start();
		super.start();
	}

	@Override
	public synchronized void stop() {
		if (session != null && !session.isClosed()) {
			try {
				session.close();
			} catch (RuntimeException ex) {
				log.error("Error while closing session", ex);
			}
		}
		if (cluster != null && !cluster.isClosed()) {
			try {
				cluster.close();
			} catch (RuntimeException ex) {
				log.error("Error while closing cluster", ex);
			}
		}
		this.sinkCounter.stop();
		super.stop();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Status process() throws EventDeliveryException {
		log.debug("Executing CassandraSink.process()...");
		Status status = Status.READY;
		Channel channel = getChannel();
		Transaction txn = channel.getTransaction();
		try {
			txn.begin();
			int count;
			List<Event> eventList = new ArrayList<Event>();
			for (count = 0; count < batchSize; ++count) {
				Event event = channel.take();
				if (event == null) {
					break;
				}

				JsonObject json = parser.parse(new String(event.getBody())).getAsJsonObject();
				
				json.addProperty("device", json.get("sourceId").getAsString());
				json.remove("sourceId");
				
				json.addProperty("lastupdatedtime", json.get("lastUpdatedTime").getAsString());
				json.remove("lastUpdatedTime");
				
				json.addProperty("devicename", json.get("deviceName").getAsString());
				json.remove("deviceName");
				
				json.addProperty("datasourcename", json.get("datasourceName").getAsString());
				json.remove("datasourceName");
				
				Statement statement = new SimpleStatement("insert into device_activity JSON " 
				+ "'" + json.toString().replaceAll("'", "''") + "'");
				log.info("device activity data....." + statement.toString());
				this.session.execute(statement);
				
				eventList.add(event);
			}

			if (count <= 0) {
				sinkCounter.incrementBatchEmptyCount();
				counterGroup.incrementAndGet("channel.underflow");
				status = Status.BACKOFF;
			} else {
				if (count < batchSize) {
					sinkCounter.incrementBatchUnderflowCount();
					status = Status.BACKOFF;
				} else {
					sinkCounter.incrementBatchCompleteCount();
				}

				sinkCounter.addToEventDrainAttemptCount(count);
				// client.execute();
			}
			txn.commit();
			sinkCounter.addToEventDrainSuccessCount(count);
			counterGroup.incrementAndGet("transaction.success");
		} catch (Throwable ex) {
			try {
				txn.rollback();
				counterGroup.incrementAndGet("transaction.rollback");
			} catch (Exception ex2) {
				log.error(
						"Exception in rollback. Rollback might not have been successful.",
						ex2);
			}

			if (ex instanceof Error || ex instanceof RuntimeException) {
				log.error(
						"Failed to commit transaction. Transaction rolled back.",
						ex);
				Throwables.propagate(ex);
			} else {
				log.error(
						"Failed to commit transaction. Transaction rolled back.",
						ex);
				throw new EventDeliveryException(
						"Failed to commit transaction. Transaction rolled back.",
						ex);
			}
		} finally {
			txn.close();
		}
		return status;

	}
	
	public static void main(String[] args) {
		String event = "{\"sourceId\":\"659ef853-f51c-4a76-bd72-7e17e205203e\",\"lastUpdatedTime\":1462135183192,\"deviceName\":\"82972011566449\",\"datasourceName\":\"82972011566449\"}";
		
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(new String(event)).getAsJsonObject();
		
		json.addProperty("device", json.get("sourceId").getAsString());
		json.remove("sourceId");
		
		json.addProperty("lastupdatedtime", json.get("lastUpdatedTime").getAsString());
		json.remove("lastUpdatedTime");
		
		json.addProperty("devicename", json.get("deviceName").getAsString());
		json.remove("deviceName");
		
		json.addProperty("datasourcename", json.get("datasourceName").getAsString());
		json.remove("datasourceName");
		
		System.out.println("For device activity " + json.toString());
	}

}