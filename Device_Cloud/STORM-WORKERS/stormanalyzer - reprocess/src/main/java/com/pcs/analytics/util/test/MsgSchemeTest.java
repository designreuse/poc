package com.pcs.analytics.util.test;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.data.analyzer.storm.scheme.MessageScheme;
import com.pcs.saffron.cipher.data.message.Message;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class MsgSchemeTest   {

	private static final long serialVersionUID = 8125410491388903629L;
	private final Logger LOGGER = LoggerFactory.getLogger(MessageScheme.class);
	public static final String SOURCE_ID_KEY = "sourceId";

	public static final String DEVICE_MESSAGE_KEY = "deviceMessage";

	private String spoutName;
	
	public MsgSchemeTest(String spoutName){
		this.spoutName = spoutName;
	}
	
	public List<Object> deserialize(byte[] ser) {

		Values values = null;
		String jsonString = null;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = new String(ser);
			jsonString = jsonString.replaceAll("alarmState", "state");//need to be fixed, workaround
			jsonString = jsonString.replaceAll("alarmType", "type");//need to be fixed, workaround
			Message message = mapper.readValue(jsonString, Message.class);
			values = new Values(message.getSourceId(), message);

		} catch (Exception e) {
			LOGGER.error("Error in Deserializing in spout {} for data {}",spoutName,jsonString,e);
			values = new Values(null, null);
		}
		return values;
	}
	
	public Fields getOutputFields() {
		return new Fields(SOURCE_ID_KEY, DEVICE_MESSAGE_KEY);
	}

	
	public static void main(String[] args) {
		String str="{\"sourceId\":\"123456789012345\",\"time\":\"1452700417505\",\"receivedTime\":\"1452700416371\",\"points\":[{\"pointId\":\"Priority\",\"pointName\":\"Priority\",\"displayName\":\"Priority\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"null\",\"data\":\"00\",\"status\":\"null\",\"type\":\"INTEGER\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"Longitude\",\"pointName\":\"Longitude\",\"displayName\":\"Longitude\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"null\",\"data\":\"0003E11C\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"Latitude\",\"pointName\":\"Latitude\",\"displayName\":\"Latitude\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"null\",\"data\":\"00088292\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"Altitude\",\"pointName\":\"Altitude\",\"displayName\":\"Altitude\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"null\",\"data\":\"00B1\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"Angle\",\"pointName\":\"Angle\",\"displayName\":\"Angle\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"null\",\"data\":\"0093\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"Visible Satellites\",\"pointName\":\"Visible Satellites\",\"displayName\":\"Visible Satellites\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"null\",\"data\":\"0E\",\"status\":\"null\",\"type\":\"INTEGER\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"Speed\",\"pointName\":\"Speed\",\"displayName\":\"Speed\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"null\",\"data\":\"0092\",\"status\":\"null\",\"type\":\"INTEGER\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"1\",\"pointName\":\"DIN1\",\"displayName\":\"DIN1\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"01\",\"status\":\"null\",\"type\":\"BOOLEAN\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"2\",\"pointName\":\"DIN2\",\"displayName\":\"DIN2\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"01\",\"status\":\"null\",\"type\":\"BOOLEAN\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"3\",\"pointName\":\"DIN3\",\"displayName\":\"DIN3\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"01\",\"status\":\"null\",\"type\":\"BOOLEAN\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"22\",\"pointName\":\"Current Profile\",\"displayName\":\"Current Profile\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"03\",\"status\":\"null\",\"type\":\"STRING\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"71\",\"pointName\":\"GNSS status\",\"displayName\":\"GNSS status\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"03\",\"status\":\"null\",\"type\":\"INTEGER\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"240\",\"pointName\":\"Movement\",\"displayName\":\"Movement\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"01\",\"status\":\"null\",\"type\":\"BOOLEAN\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"21\",\"pointName\":\"GSM Signal\",\"displayName\":\"GSM Signal\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"05\",\"status\":\"null\",\"type\":\"INTEGER\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"200\",\"pointName\":\"Deep Sleep\",\"displayName\":\"Deep Sleep\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"00\",\"status\":\"null\",\"type\":\"BOOLEAN\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"204\",\"pointName\":\"Fuel temperature 2\",\"displayName\":\"Fuel temperature 2\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"FF\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"203\",\"pointName\":\"Fuel level meter 2\",\"displayName\":\"Fuel level meter 2\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"0352\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"9\",\"pointName\":\"Analog 1\",\"displayName\":\"Analog 1\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"000F\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"10\",\"pointName\":\"Analog 2\",\"displayName\":\"Analog 2\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"34F7\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"11\",\"pointName\":\"Analog 3\",\"displayName\":\"Analog 3\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"0003\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"19\",\"pointName\":\"Analog 4\",\"displayName\":\"Analog 4\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"0450\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"67\",\"pointName\":\"Internal Battery Voltage\",\"displayName\":\"Internal Battery Voltage\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"241B\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"68\",\"pointName\":\"Internal Battery Current\",\"displayName\":\"Internal Battery Current\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"0000\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"181\",\"pointName\":\"GPS PDOP\",\"displayName\":\"GPS PDOP\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"000A\",\"status\":\"null\",\"type\":\"INTEGER\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"182\",\"pointName\":\"GPS HDOP\",\"displayName\":\"GPS HDOP\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"0006\",\"status\":\"null\",\"type\":\"INTEGER\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"66\",\"pointName\":\"External Power Voltage\",\"displayName\":\"External Power Voltage\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"36A1\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"24\",\"pointName\":\"Speedometer\",\"displayName\":\"Speedometer\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"0000\",\"status\":\"null\",\"type\":\"INTEGER\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"241\",\"pointName\":\"Current Operator Code\",\"displayName\":\"Current Operator Code\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"0000A5A2\",\"status\":\"null\",\"type\":\"STRING\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]},{\"pointId\":\"70\",\"pointName\":\"PCB Temperature\",\"displayName\":\"PCB Temperature\",\"className\":\"null\",\"systemTag\":\"null\",\"physicalQuantity\":\"generic_quantity\",\"unit\":\"unitless\",\"data\":\"000001EE\",\"status\":\"null\",\"type\":\"FLOAT\",\"extensions\":[{\"extensionName\":\"READONLY\",\"extensionType\":\"ACCESS TYPE\"}],\"alarmExtensions\":[]}]}";
		
		 new MsgSchemeTest("test").deserialize(str.getBytes());
	}
}