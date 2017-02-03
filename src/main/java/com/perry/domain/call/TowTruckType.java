package com.perry.domain.call;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.perry.exceptions.EnumerationException;

public enum TowTruckType {
	MEDIUM(1, "Medium"), WRECKER(2, "Wrecker"), FLATBED(3, "Flatbed"), FIRST_AVAILABLE(4, "First Available");

	private long id;

	private String name;

	private TowTruckType(long id, String name) {
		this.id = id;
		this.name = name;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
