package com.pcs.device.gateway.G2021.message.processor;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.G2021.devicemanager.G2021DeviceManager;
import com.pcs.device.gateway.G2021.devicemanager.bean.G2021DeviceConfiguration;
import com.pcs.device.gateway.G2021.exception.MessageProcessException;
import com.pcs.device.gateway.G2021.message.PointDiscoveryMessage;
import com.pcs.device.gateway.G2021.message.extension.access.type.PointAccess;
import com.pcs.device.gateway.G2021.message.extension.alarm.criticality.Criticality;
import com.pcs.device.gateway.G2021.message.header.Header;
import com.pcs.device.gateway.G2021.packet.type.PacketType;
import com.pcs.deviceframework.cache.Cache;
import com.pcs.deviceframework.decoder.data.message.Message;
import com.pcs.deviceframework.decoder.data.point.Point;
import com.pcs.deviceframework.decoder.data.point.extension.AlarmExtension;
import com.pcs.deviceframework.decoder.data.point.extension.OutOfRangeAlarm;
import com.pcs.deviceframework.decoder.data.point.extension.PointExtension;
import com.pcs.deviceframework.decoder.data.point.extension.StateChangeAlarm;
import com.pcs.deviceframework.decoder.data.point.types.PointDataTypes;

public class PointDiscoveryProcessor extends G2021Processor {


	private static final Logger LOGGER = LoggerFactory.getLogger(PointDiscoveryProcessor.class);

	private G2021DeviceConfiguration configuration;
	private final G2021DeviceManager deviceManager = G2021DeviceManager.instance();
	private Object unitId;


	public PointDiscoveryProcessor() throws Exception{

	}

	public PointDiscoveryProcessor(Integer unitId) throws MessageProcessException {
		this.unitId = unitId;
		configuration = (G2021DeviceConfiguration) deviceManager.getConfiguration(unitId);
		if(configuration == null)
			throw new MessageProcessException("Configuration cannot be null !!!");
	}

	@Override
	public Message processG2021Message(Object G2021Data) throws MessageProcessException {
		PointDiscoveryMessage message = new PointDiscoveryMessage();
		try {
			ByteBuf pointData = (ByteBuf) G2021Data;
			Integer totalPoints = (int) pointData.readUnsignedByte();
			for (int i = 0; i < totalPoints; i++) {

				Integer pointId = pointData.readUnsignedShort();
				message.addPid(pointId.shortValue());

				int nameLength = pointData.readUnsignedByte();
				char[] nameArray = new char[nameLength];

				for (int j = 0; j < nameLength; j++) {
					nameArray[j] = (char) pointData.readUnsignedByte();
				}
				String pointName = new String(nameArray);
				int unitLength = pointData.readUnsignedByte();
				char[] unitArray = new char[unitLength];

				for (int j = 0; j < unitLength; j++) {
					unitArray[j] = (char) pointData.readUnsignedByte();
				}
				String unit = new String(unitArray);

				int pointType = pointData.readUnsignedByte();
				int pointDataType = pointData.readUnsignedByte();
				int pointAcqType = pointData.readUnsignedByte();
				Short pointAcqTime = pointData.readShort(); 
				Float pointAcqCovThreshold = pointData.readFloat();
				int pointAlarmType = pointData.readUnsignedByte();
				int pointAlarmCriticality = pointData.readUnsignedByte();
				Float pointAlarmLowerThreshold = pointData.readFloat();
				Float pointAlarmUpperThreshold = pointData.readFloat();

				int normalTextLength = pointData.readUnsignedByte();
				char[] normalTextArray = new char[normalTextLength];

				for (int j = 0; j < normalTextLength; j++) {
					normalTextArray[j] = (char) pointData.readUnsignedByte();
				}
				String normalMessage = new String(normalTextArray);

				int offNormalTextLength = pointData.readUnsignedByte();
				char[] offNormalTextArray = new char[offNormalTextLength];

				for (int j = 0; j < offNormalTextLength; j++) {
					offNormalTextArray[j] = (char) pointData.readUnsignedByte();
				}
				String alarmMessage = new String(offNormalTextArray);

				int offNormalLowerThresholdTextLength = pointData.readUnsignedByte();
				char[] offNormalLowerThresholdTextArray = new char[offNormalLowerThresholdTextLength];

				for (int j = 0; j < offNormalLowerThresholdTextLength; j++) {
					offNormalLowerThresholdTextArray[j] = (char) pointData.readUnsignedByte();
				}
				String lowerThresholdAlarmMessage = new String(offNormalLowerThresholdTextArray);

				int offNormalUpperThresholdTextLength = pointData.readUnsignedByte();
				char[] offNormalUpperThresholdTextArray = new char[offNormalUpperThresholdTextLength];

				for (int j = 0; j < offNormalUpperThresholdTextLength; j++) {
					offNormalUpperThresholdTextArray[j] = (char) pointData.readUnsignedByte();
				}
				String upperThresholdAlarmMessage = new String(offNormalUpperThresholdTextArray);

				Point point = Point.getPoint(resolvePointType(pointDataType).getType());
				point.setPointName(pointName);
				point.setCustomTag(pointName); 
				point.setUnit(unit);
				point.setPhyQtyName(resolvePhysicalQuantity(unit));
				point.setPointId(pointId.toString());

				//Managing extensions
				point.setPointAccessType(resolveAccess(pointType));
				PointExtension acquisitionMode = getAcquisitionMode(pointAcqType,pointAcqTime,pointAcqCovThreshold);
				if(acquisitionMode != null)
					point.addExtension(acquisitionMode);
				AlarmExtension alarmExtension = getAlarmType(pointAlarmType);
				String alarmCriticality = getAlarmCriticality(pointAlarmCriticality);
				if(alarmExtension != null){
					if (alarmExtension instanceof StateChangeAlarm) {
						StateChangeAlarm stateChangeAlarm = (StateChangeAlarm) alarmExtension;
						stateChangeAlarm.setCriticality(alarmCriticality);
						stateChangeAlarm.setAlarmMessage(alarmMessage);
						stateChangeAlarm.setNormalMessage(normalMessage);
						point.addAlarmExtension(stateChangeAlarm);
						stateChangeAlarm = null;
					}else if (alarmExtension instanceof OutOfRangeAlarm){
						OutOfRangeAlarm outOfRangeAlarm = (OutOfRangeAlarm) alarmExtension;
						outOfRangeAlarm.setAlarmMessage(alarmMessage);
						outOfRangeAlarm.setNormalMessage(normalMessage);
						outOfRangeAlarm.setCriticality(alarmCriticality);
						outOfRangeAlarm.setLowerThreshold(pointAlarmLowerThreshold);
						outOfRangeAlarm.setLowerThresholdAlarmMessage(lowerThresholdAlarmMessage);
						outOfRangeAlarm.setLowerThresholdNormalMessage(normalMessage);
						outOfRangeAlarm.setUpperThreshold(pointAlarmUpperThreshold);
						outOfRangeAlarm.setUpperThresholdAlarmMessage(upperThresholdAlarmMessage);
						outOfRangeAlarm.setUpperThresholdNormalMessage(normalMessage);
						point.addAlarmExtension(outOfRangeAlarm);
						outOfRangeAlarm = null;
					}
				}
				LOGGER.info("Point decoded : "+point.getPointId()+" "+point.getPointName());
				if(configuration.isConfigured()){
					configuration.addUpdatedPointConfiguration(point);
				}else{
					configuration.addPointConfiguration(point);
					configuration.addPointMapping(pointId.toString(), point);
					LOGGER.info("New point mapped {}",point.getPointName());
					
				}
			}
			deviceManager.refreshDeviceConfiguration(configuration.getUnitId(), configuration);
		} catch (Exception e) {
			LOGGER.error("Error !!!",e);
		}

		return message;
	}

	private String resolvePhysicalQuantity(String unit) {
		String pq = "generic_quantity";
		if (StringUtils.isNotBlank(unit)) {
			Cache unitPqCache = deviceManager.getCacheProvider().getCache("unit_symbols");
			if (unitPqCache != null) {
				pq = unitPqCache.get(unit, String.class);
			}
		}
		return pq;
	}

	private PointDataTypes resolvePointType(int type){
		switch (type) {
		case 0:
			return PointDataTypes.BOOLEAN;
		case 1:
			return PointDataTypes.SHORT;
		case 2:
			return PointDataTypes.INTEGER;
		case 3:
			return PointDataTypes.LONG;
		case 4:
			return PointDataTypes.FLOAT;
		case 5:
			return PointDataTypes.DATE;
		case 6:
			return PointDataTypes.STRING;
		default:
			return PointDataTypes.BASE;
		}
	}

	private String resolveAccess(int access){
		switch (access) {
		case 0:
			return PointAccess.READONLY.name();
		case 1:
			return PointAccess.WRITEABLE.name();
		default:
			return PointAccess.READABLE.name();
		}
	}

	private PointExtension getAcquisitionMode(int type, Short pointAcqTime, Float pointAcqCovThreshold){
		switch (type) {
		case 0:
			return new PointExtension("NO ACQUISITION MODE SPECIFIED","Acquisition Mode");
		case 1:
			return new PointExtension("TIMEBASED ACQUISITION, EVERY "+pointAcqTime+" MINUTES","Acquisition Mode"); 
		case 2:
			return new PointExtension("STATECHANGE ACQUISITION","Acquisition Mode");
		case 3:
			return new PointExtension("CHANGE OF VALUE BASED ACQUISITION, THRESHOLD AT "+pointAcqCovThreshold,"Acquisition Mode");

		default:
			
			LOGGER.info("No acquisition mode specified!!");
			return null;
		}
	}

	private AlarmExtension getAlarmType(int type){
		switch (type) {
		case 0:
			return null;
		case 1:
			return new com.pcs.deviceframework.decoder.data.point.extension.StateChangeAlarm();
		case 2:
			return new OutOfRangeAlarm();

		default:
			return null;
		}
	}

	private String getAlarmCriticality(int criticality){
		switch (criticality) {
		case 0:
			return Criticality.LOW.name();
		case 1:
			return Criticality.NORMAL.name();
		case 2:
			return Criticality.CRITICAL.name();
		case 3:
			return Criticality.MAINTENANCE.name();

		default:
			return null;
		}
	}


	@Override
	public byte[] getServerMessage(Message message, Header header) throws MessageProcessException {
		ByteBuffer ackResponse = ByteBuffer.allocate(3);
		ackResponse.put(PacketType.ANONYMOUS_ACK.getType().byteValue());
		ackResponse.put(header.getMessageType().getType().byteValue());
		ackResponse.put(header.getSeqNumber().byteValue());
		return ackResponse.array();
	}

	
	public static void main(String[] args) throws Exception {
		new PointDiscoveryProcessor().resolvePhysicalQuantity("m2");
	}

}