package com.perry.infrastructure.call;

import java.util.List;
import java.util.Map;

import com.perry.domain.call.Call;
import com.perry.domain.truck.Truck;

public interface CallDaoService {

	List<Call> getByIds(List<Long> ids);

	Call create(Call call);

	List<Call> getAllCalls();

	Truck assignTruck(long callId, long truckId);

	List<Call> getAvailable();

	void unAssignTruck(long callId);

	void delete(long callId);

	Call edit(Call call);

	Call getTruckActive(long truckId);

	Call getById(long id);

	Map<Long, String> getDropOffLocationByIds(List<Long> arrayList);

}
