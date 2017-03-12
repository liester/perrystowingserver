package com.perry.domain.truck;

import java.util.List;

public interface TruckDomainService {

	List<Truck> getByIds(List<Long> truckIds);

	Truck create(Truck truck);

	List<Truck> getAll();
	
	List<Truck> getAvailable();

	Truck updateStatus(Long truckId, String status);

	void updateLocation(Long truckId, String lat, String lon);
	
	Truck edit(Truck truck);

}
