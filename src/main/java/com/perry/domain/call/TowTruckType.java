package com.perry.domain.call;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.perry.exceptions.EnumerationException;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TowTruckType {
	NONE(0, "None"),MEDIUM(1, "Medium"), WRECKER(2, "Wrecker"), FLATBED(3, "Flatbed"), FIRST_AVAILABLE(4, "First Available");

	private long id;

	private String value;

	private TowTruckType(long id, String value) {
		this.id = id;
		this.value = value;
	}

	@JsonCreator
	public static TowTruckType fromId(long id) {
		for (TowTruckType requiredTruckType : TowTruckType.values()) {
			if (requiredTruckType.getId() == id) {
				return requiredTruckType;
			}
		}
		throw new EnumerationException(id, TowTruckType.class.getName());
	}

	
	public static TowTruckType fromValue(String value) {
		if(value==null){
			return null;
		}
		value = value.trim();
		for (TowTruckType requiredTruckType : TowTruckType.values()) {
			if (requiredTruckType.getValue().equalsIgnoreCase((value))) {
				return requiredTruckType;
			}
		}
		throw new EnumerationException(value, TowTruckType.class.getName());
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
