package com.perry.domain.truck;

import com.perry.exceptions.EnumerationException;

public enum TruckStatusType {
	AVAILABLE(1, "Available"), EN_ROUTE(2, "En-Route"), LOADED(3, "Loaded");

	private long id;

	private String value;

	private TruckStatusType(long id, String value) {
		this.id = id;
		this.value = value;
	}

	public static TruckStatusType fromId(long id) {
		for (TruckStatusType truckStatusType : TruckStatusType.values()) {
			if (truckStatusType.getId() == id) {
				return truckStatusType;
			}
		}
		throw new EnumerationException(id, TruckStatusType.class.getName());
	}
	
	public static TruckStatusType fromValue(String value) {
		for (TruckStatusType truckStatusType : TruckStatusType.values()) {
			if (truckStatusType.getValue().equals(value)) {
				return truckStatusType;
			}
		}
		throw new EnumerationException(value, TruckStatusType.class.getName());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
