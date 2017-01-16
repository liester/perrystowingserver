package com.perry.domain.truck;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.perry.infrastructure.truck.TruckDaoService;

@Named
public class TruckDomainServiceImpl implements TruckDomainService {

	@Inject
	private TruckDaoService truckDaoService;

	@Override
	public List<Truck> getByIds(List<Long> truckIds) {
		List<Truck> truckList = truckDaoService.getByIds(truckIds);
		return truckList;
	}

	@Override
	public Truck create(Truck truck) {
		Truck createdTruck = truckDaoService.create(truck);
		return createdTruck;
	}

}
