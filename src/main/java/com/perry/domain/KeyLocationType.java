package com.perry.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.perry.exceptions.EnumerationException;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum KeyLocationType {
	NONE(0,"None"),NO_KEYS(1, "No Keys"), KEYS_IN_VEHICLE(2, "Keys in Vehicle"), CALL_FOR_KEYS(3, "Call for Keys"), KEYS_AT_DESTINATION(4, "Keys at Destination"),KEYS_WITH_OWNER_SHOP(5, "Keys with owner/shop");

	private long id;

	private String value;

	KeyLocationType(long id, String value) {
		this.id = id;
		this.value = value;
	}

	@JsonCreator
	public static KeyLocationType fromId(long id) {
		for (KeyLocationType keyLocation : KeyLocationType.values()) {
			if (keyLocation.getId() == (id)) {
				return keyLocation;
			}
		}
		throw new EnumerationException("Failed to convert " + id + " to class:", KeyLocationType.class.getName());
	}
	
	public static KeyLocationType fromValue(String value) {
		if(value == null){
			return null;
		}
		value = value.trim();
		for (KeyLocationType keyLocation : KeyLocationType.values()) {
			if (keyLocation.getValue().equalsIgnoreCase(value)) {
				return keyLocation;
			}
		}
		throw new EnumerationException(value, KeyLocationType.class.getName());
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
