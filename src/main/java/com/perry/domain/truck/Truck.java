package com.perry.domain.truck;

import com.perry.infrastructure.Persisted;

public class Truck extends Persisted {

	// this is NOT the DB id
	private String identifier;

	private String driverFirstName;

	private String driverLastName;

	private TruckStatusType truckStatusType;

	private String gisLatitude;

	private String gisLongitude;

	private long activeCallId;

	private long queuedCallId;

	private String dropOffLocation;

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDriverFirstName() {
		return driverFirstName;
	}

	public void setDriverFirstName(String firstName) {
		this.driverFirstName = firstName;
	}

	public String getDriverLastName() {
		return driverLastName;
	}

	public void setDriverLastName(String lastName) {
		this.driverLastName = lastName;
	}

	public TruckStatusType getTruckStatusType() {
		return truckStatusType;
	}

	public void setTruckStatusType(TruckStatusType truckStatusType) {
		this.truckStatusType = truckStatusType;
	}

	public String getGis_latitude() {
		return gisLatitude;
	}

	public String getGisLatitude() {
		return gisLatitude;
	}

	public void setGisLatitude(String gisLatitude) {
		this.gisLatitude = gisLatitude;
	}

	public String getGisLongitude() {
		return gisLongitude;
	}

	public void setGisLongitude(String gisLongitude) {
		this.gisLongitude = gisLongitude;
	}

	public long getActiveCallId() {
		return activeCallId;
	}

	public void setActiveCallId(long activeCallId) {
		this.activeCallId = activeCallId;
	}

	public long getQueuedCallId() {
		return queuedCallId;
	}

	public void setQueuedCallId(long queuedCallId) {
		this.queuedCallId = queuedCallId;
	}

	public String getDropOffLocation() {
		return dropOffLocation;
	}

	public void setDropOffLocation(String dropOffLocation) {
		this.dropOffLocation = dropOffLocation;
	}

}
