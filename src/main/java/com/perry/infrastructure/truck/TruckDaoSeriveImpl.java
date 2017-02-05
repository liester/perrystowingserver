package com.perry.infrastructure.truck;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.perry.domain.truck.Truck;
import com.perry.infrastructure.call.CallDaoService;

import rowmappers.TruckRowMapper;

@Named
public class TruckDaoSeriveImpl implements TruckDaoService {

	@Inject
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Inject
	private CallDaoService callDaoService;

	@Override
	public List<Truck> getByIds(List<Long> truckIds) {
		String sql = "select * from trucks t where t.truck_id in (:truckIds)";
		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("truckIds", truckIds);

		List<Truck> truckList = namedParameterJdbcTemplate.query(sql, params, new TruckRowMapper());
		Set<Long> activeCallIds = new HashSet<>();
		for (Truck truck : truckList) {
			if (truck.getActiveCallId() > 0) {
				activeCallIds.add(truck.getActiveCallId());
			}
		}
		if (!activeCallIds.isEmpty()) {
			Map<Long, String> dropOffLocationMap = callDaoService.getDropOffLocationByIds(new ArrayList<Long>(activeCallIds));
			for (Truck truck : truckList) {
				truck.setDropOffLocation(dropOffLocationMap.get(truck.getId()));
			}
		}

		return truckList;
	}

	@Override
	public Truck getById(long truckId) {
		List<Truck> truckList = getByIds(Arrays.asList(truckId));
		return truckList.get(0);
	}

	@Override
	public Truck create(Truck truck) {
		String sql = "INSERT INTO trucks(\r\n" + //
				"            driver_first_name, driver_last_name, status, insert_time, \r\n" + //
				"            update_time, insert_by, update_by)\r\n" + //
				"    VALUES (:driverFirstName, :driverLastName, :status, :insertTime,  \r\n" + //
				"            :updateTime, :insertBy, :updateBy);";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("driverFirstName", truck.getDriverFirstName());
		params.addValue("driverLastName", truck.getDriverLastName());
		params.addValue("status", truck.getTruckStatusType().getValue());
		params.addValue("insertTime", Instant.now().getEpochSecond());
		params.addValue("updateTime", Instant.now().getEpochSecond());
		params.addValue("insertBy", truck.getInsertBy());
		params.addValue("updateBy", truck.getUpdateBy());
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, params, keyHolder);
		truck.setId((Long) keyHolder.getKeys().get("truck_id"));
		return truck;
	}

	@Override
	public List<Truck> getAll() {
		String sql = "select * from trucks order by insert_time asc";
		List<Truck> truckList = namedParameterJdbcTemplate.query(sql, new TruckRowMapper());

		Set<Long> activeCallIds = new HashSet<>();
		for (Truck truck : truckList) {
			if (truck.getActiveCallId() > 0) {
				activeCallIds.add(truck.getActiveCallId());
			}
		}
		if (!activeCallIds.isEmpty()) {
			Map<Long, String> dropOffLocationMap = callDaoService.getDropOffLocationByIds(new ArrayList<Long>(activeCallIds));
			for (Truck truck : truckList) {
				truck.setDropOffLocation(dropOffLocationMap.get(truck.getId()));
			}
		}

		return truckList;
	}

	@Override
	public Truck updateCall(long truckId, long callId) {
		Truck truck = getById(truckId);

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("callId", callId);
		params.addValue("truckId", truckId);

		// determine if truck has an active call or not
		boolean hasActiveCall = truck.getActiveCallId() > 0;

		// build sql for truck based on active call assignment.
		if (hasActiveCall) {
			// if there is already an active call, updated queued call id
			String truckUpdateSql = "update trucks set queued_call_id = :callId where truck_id = :truckId";
			namedParameterJdbcTemplate.update(truckUpdateSql, params);

		} else {
			// no active call means update active call id
			String truckUpdateSql = "update trucks set active_call_id = :callId where truck_id = :truckId";
			namedParameterJdbcTemplate.update(truckUpdateSql, params);
		}
		// since sql was successful update and return updated truck object
		if (hasActiveCall) {
			truck.setActiveCallId(callId);
		} else {
			truck.setQueuedCallId(callId);
		}
		return truck;
	}

	@Override
	public List<Truck> getAvailable() {
		String sql = "select * from  trucks where trucks.active_call_id = 0 or trucks.queued_call_id = 0";
		List<Truck> truckList = namedParameterJdbcTemplate.query(sql, new TruckRowMapper());
		return truckList;
	}

}
