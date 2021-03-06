package com.pcs.device.gateway.G2021.devicemanager.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author pcseg310
 * 
 */
@JsonAutoDetect
public class Device implements Serializable {

	private static final long serialVersionUID = -2049990530877806985L;
	private String deviceId;
	private Status entityStatus;
	private Identifier identifier;
	private String datasourceName;
	private String sourceId;
	private Subscription subscription;
	private DeviceType type;
	private DeviceProtocol networkProtocol;
	private DeviceComnProtocol protocol;
	private List<Tag> tags ;
	private Boolean isPublishing;
	private String ip;
	private Integer connectedPort;
	private Integer writeBackPort;
	private Version version;
	private Configuration configurations;
	private Integer unitId;
	
	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Status getEntityStatus() {
		return entityStatus;
	}

	public void setEntityStatus(Status entityStatus) {
		this.entityStatus = entityStatus;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public DeviceType getType() {
		return type;
	}

	public void setType(DeviceType deviceType) {
		this.type = deviceType;
	}

	public DeviceProtocol getNetworkProtocol() {
		return networkProtocol;
	}

	public void setNetworkProtocol(DeviceProtocol deviceProtocol) {
		this.networkProtocol = deviceProtocol;
	}

	public DeviceComnProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(DeviceComnProtocol deviceComnProtocol) {
		this.protocol = deviceComnProtocol;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Boolean getIsPublishing() {
		return isPublishing;
	}

	public void setIsPublishing(Boolean isPublishing) {
		this.isPublishing = isPublishing;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getConnectedPort() {
		return connectedPort;
	}

	public void setConnectedPort(Integer connectedPort) {
		this.connectedPort = connectedPort;
	}

	public Integer getWriteBackPort() {
		return writeBackPort;
	}

	public void setWriteBackPort(Integer writeBackPort) {
		this.writeBackPort = writeBackPort;
	}

	public Configuration getConfigurations() {
		return configurations;
	}

	public void setConfigurations(Configuration configurations) {
		this.configurations = configurations;
	}

}