package com.perry.infrastructure.truck;

import java.util.List;

import com.perry.domain.truck.Truck;

public interface TruckDaoService {

	List<Truck> getByIds(List<Long> truckIds);

	Truck create(Truck truck);

	List<Truck> getAll();

}
