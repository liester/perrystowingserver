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

import com.perry.domain.call.Call;
import com.perry.domain.truck.Truck;
import com.perry.domain.truck.TruckStatusType;
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
			Map<Long, String> dropOffLocationMap = callDaoService
					.getDropOffLocationByIds(new ArrayList<Long>(activeCallIds));
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
		String sql = "INSERT INTO trucks(" + //
				"           identifier, driver_first_name, driver_last_name, status, insert_time, \r\n" + //
				"            update_time, insert_by, update_by)\r\n" + //
				"    VALUES (:identifier, :driverFirstName, :driverLastName, :status, :insertTime,  \r\n" + //
				"            :updateTime, :insertBy, :updateBy);";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("identifier", truck.getIdentifier());
		params.addValue("driverFirstName", truck.getDriverFirstName());
		params.addValue("driverLastName", truck.getDriverLastName());
		params.addValue("status", TruckStatusType.OFF_DUTY.getValue());
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
	public Truck edit(Truck truck) {
		String sql = "update trucks set " + 
				"driver_first_name = :driverFirstName, " + 
				"driver_last_name = :driverLastName, " + 
				"identifier = :identifier," +
				"update_time = :updateTime where " + 
				"truck_id = :truckId ";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("driverFirstName", truck.getDriverFirstName());
		params.addValue("driverLastName", truck.getDriverLastName());
		params.addValue("identifier", truck.getIdentifier());
		params.addValue("updateTime", Instant.now().getEpochSecond());
		params.addValue("truckId", truck.getId());
		namedParameterJdbcTemplate.update(sql, params);
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
			List<Call> callList = callDaoService.getByIds(new ArrayList<Long>(activeCallIds));
			for (Truck truck : truckList) {
				for (Call call : callList) {
					if (call.getId().equals(truck.getActiveCallId())) {
						truck.setDropOffLocation(call.getDropOffLocation());
						break;
					}
				}
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

		// if call is already assigned to the truck, do not update truck.
		if (truck.getActiveCallId() == callId || truck.getQueuedCallId() == callId) {
			return truck;
		}
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

	@Override
	public void removeCall(long callId) {
		List<Truck> trucks = getByCallId(callId);
		for (Truck truck : trucks) {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("truckId", truck.getId());
			params.addValue("callId", callId);
			if (truck.getActiveCallId() == callId) {
				String sql = "update trucks set active_call_id = 0 where truck_id= :truckId";
				namedParameterJdbcTemplate.update(sql, params);
			}
			if (truck.getQueuedCallId() == callId) {
				String sql = "update trucks set queued_call_id = 0 where truck_id= :truckId";
				namedParameterJdbcTemplate.update(sql, params);
			}
		}

	}

	@Override
	public List<Truck> getByCallId(long callId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("callId", callId);
		String sql = "select * from trucks where trucks.active_call_id = :callId or trucks.queued_call_id=:callId";
		List<Truck> truckList = namedParameterJdbcTemplate.query(sql, params, new TruckRowMapper());
		return truckList;
	}

	@Override
	public int updateStatus(Long truckId, TruckStatusType statusType) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("truckId", truckId);
		params.addValue("status", statusType.getValue());
		params.addValue("updateTime", Instant.now().getEpochSecond());
		String sql = "update trucks set status = :status, update_time = :updateTime where truck_id = :truckId";
		return namedParameterJdbcTemplate.update(sql, params);
	}

	@Override
	public void updateLocation(Long truckId, String lat, String lon) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("truckId", truckId);
		params.addValue("lat", lat);
		params.addValue("lon", lon);
		params.addValue("updateTime", Instant.now().getEpochSecond());
		String sql = "update trucks set gis_latitude = :lat, gis_longitude = :lon, update_time = :updateTime where truck_id = :truckId";
		namedParameterJdbcTemplate.update(sql, params);
	}

}
