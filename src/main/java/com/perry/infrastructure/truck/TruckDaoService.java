package com.perry.infrastructure.truck;

import java.util.List;

import com.perry.domain.truck.Truck;
import com.perry.domain.truck.TruckStatusType;

public interface TruckDaoService {

	List<Truck> getByIds(List<Long> truckIds);

	Truck create(Truck truck);
	
	Truck edit(Truck truck);

	List<Truck> getAll();

	Truck updateCall(long truckId, long callId);

	Truck getById(long truckId);

	List<Truck> getAvailable();

	void removeCall(long truckId);
	
	List<Truck> getByCallId(long callId);

	int updateStatus(Long truckId, TruckStatusType statusType);

	int updateDriver(Long truckId, String driverName);

	void updateLocation(Long truckId, String lat, String lon);

}
