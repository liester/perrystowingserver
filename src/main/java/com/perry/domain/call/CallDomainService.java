package com.perry.domain.call;

import java.util.List;

import com.perry.domain.truck.Truck;

public interface CallDomainService {

	List<Call> getByIds(List<Long> ids);

	Call create(Call call);

	Truck assignTruck(long callId, long truckId);

	List<Call> getAllCalls();

	List<Call> getAvailable();

	void unAssignTruck(long callId);

	void delete(long callId);

	Call edit(Call call);

	Call getTruckActive(long truckId);
	
	List<Call> getAllNonCompleteCalls();
}
