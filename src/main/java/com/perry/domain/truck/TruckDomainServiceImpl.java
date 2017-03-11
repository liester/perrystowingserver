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
	
	@Override
	public Truck updateStatus(Long truckId, String status) {
		TruckStatusType statusType = TruckStatusType.fromValue(status);
		int changed = truckDaoService.updateStatus(truckId, statusType);
		if(changed == 1) {
			return truckDaoService.getById(truckId);
		}
		return null;
	}
	
	@Override
	public Truck updateDriverStatusAvailable(Long truckId, String driver) {
		int changed = truckDaoService.updateDriver(truckId, driver);
		if(changed == 1) {
			truckDaoService.updateStatus(truckId, TruckStatusType.AVAILABLE);
			return truckDaoService.getById(truckId);
		}
		return null;
	}
	
	@Override
	public Truck updateDriverStatusOffDuty(Long truckId) {
		int changed = truckDaoService.updateDriver(truckId, "");
		if(changed == 1) {
			truckDaoService.updateStatus(truckId, TruckStatusType.OFF_DUTY);
			return truckDaoService.getById(truckId);
		}
		return null;
	}

	@Override
	public List<Truck> getAll() {
		List<Truck> truckList = truckDaoService.getAll();
		return truckList;
	}

	@Override
	public List<Truck> getAvailable() {
		List<Truck> truckList = truckDaoService.getAvailable();
		return truckList;
	}
	
	@Override
	public void updateLocation(Long truckId, String lat, String lon) {
		truckDaoService.updateLocation(truckId, lat, lon);
	}

}
