package com.perry.infrastructure.call;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.perry.domain.call.Call;
import com.perry.domain.truck.Truck;
import com.perry.infrastructure.truck.TruckDaoService;

import rowmappers.CallRowMapper;

@Named
public class CallDaoServiceImpl implements CallDaoService {

	@Inject
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Inject
	private TruckDaoService truckDaoService;

	@Override
	public List<Call> getByIds(List<Long> ids) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("call_ids", ids);

		String sql = "select * from calls where call_id in (:call_ids)";
		List<Call> callList = namedParameterJdbcTemplate.query(sql, params, new CallRowMapper());

		return callList;
	}

	@Override
	public Call create(Call call) {
		String sql = "INSERT INTO calls(\r\n" + //
				"            customer_first_name, customer_last_name, pick_up_location, \r\n" + //
				"            drop_off_location, customer_vehicle_year, customer_vehicle_make, \r\n" + //
				"            customer_vehicle_model, customer_vehicle_color, customer_vehicle_license_plate_number, \r\n" + //
				"            customer_phone_number, customer_vehicle_key_location, customer_call_type, \r\n" + //
				"            customer_payment_information, insert_by, update_by, truck_id, \r\n" + //
				"            insert_time, update_time)\r\n" + //
				"    VALUES (:customerFirstName, :customerLastName, :pickUpLocation, \r\n" + //
				"            :dropOffLocation, :customerVehicleYear, :customerVehicleMake, \r\n" + //
				"            :customerVehicleModel, :customerVehicleColor, :customerVehicleLiscensePlateNumber, \r\n" + //
				"            :customerPhoneNumber, :customerVehicleKeyLocation, :customerCallType, \r\n" + //
				"            :customerPaymentInformation , :insertBy, :updateBy, :truckId, \r\n" + //
				"            :insertTime, :updateTime)";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("customerFirstName", call.getCustomer().getFirstName());
		params.addValue("customerLastName", call.getCustomer().getLastName());
		params.addValue("pickUpLocation", call.getPickUpLocation());
		params.addValue("dropOffLocation", call.getDropOffLocation());
		params.addValue("customerVehicleYear", call.getCustomer().getVehicle().getYear());
		params.addValue("customerVehicleMake", call.getCustomer().getVehicle().getMake());
		params.addValue("customerVehicleModel", call.getCustomer().getVehicle().getMake());
		params.addValue("customerVehicleColor", call.getCustomer().getVehicle().getColor());
		params.addValue("customerVehicleLiscensePlateNumber", call.getCustomer().getVehicle().getLicensePlateNumber());
		params.addValue("customerPhoneNumber", call.getCustomer().getPhoneNumber());
		params.addValue("customerVehicleKeyLocation", call.getCustomer().getVehicle().getKeyLocationType().getValue());
		params.addValue("customerCallType", call.getCallType().getValue());
		params.addValue("customerPaymentInformation", call.getCustomer().getVehicle().getMake());
		params.addValue("insertBy", 1);
		params.addValue("updateBy", 1);
		params.addValue("truckId", 0);
		params.addValue("insertTime", Instant.now().getEpochSecond());
		params.addValue("updateTime", Instant.now().getEpochSecond());

		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, params, keyHolder);

		// update call with primary key returned from DB
		call.setId((Long) keyHolder.getKeys().get("call_id"));
		return call;
	}

	@Override
	public List<Call> getAllCalls() {
		String sql = "select * from calls order by insert_time asc ";
		List<Call> callList = namedParameterJdbcTemplate.query(sql, new CallRowMapper());
		return callList;
	}

	@Override
	public Truck assignTruck(long callId, long truckId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("truckId", truckId);
		params.addValue("callId", callId);
		String sql = "update calls set truck_id = :truckId where call_id = :callId";
		namedParameterJdbcTemplate.update(sql, params);
		Truck truck = truckDaoService.getByIds(Arrays.asList(truckId)).get(0);
		return truck;

	}

	@Override
	public List<Call> getAvailable() {
		String sql = "select * from calls where truck_id = 0";
		List<Call> callList = namedParameterJdbcTemplate.query(sql, new CallRowMapper());
		return callList;
	}

	@Override
	public void unAssignTruck(long callId) {
		String sql = "update calls set truck_id = 0 where call_id = :callId";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("callId", callId);

		namedParameterJdbcTemplate.update(sql, params);

	}

	@Override
	public void delete(long callId) {
		String sql = "delete from calls where call_id = :callId";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("callId", callId);

		namedParameterJdbcTemplate.update(sql, params);

	}

	@Override
	public Call edit(Call call) {
		String sql = "UPDATE calls set customer_first_name = :customerFirstName, \r\n" + //
				"       customer_last_name = :customerLastName, \r\n" + //
				"       pick_up_location = :pickUpLocation, \r\n" + //
				"       drop_off_location =:dropOffLocation, \r\n" + //
				"       customer_vehicle_year=:customerVehicleYear, \r\n" + //
				"       customer_vehicle_make=:customerVehicleMake, \r\n" + //
				"       customer_vehicle_model= :customerVehicleModel, \r\n" + //
				"       customer_vehicle_color=:customerVehicleColor, \r\n" + //
				"       customer_vehicle_license_plate_number=:customerVehicleLiscensePlateNumber, \r\n" + //
				"       customer_phone_number=:customerPhoneNumber, \r\n" + //
				"       customer_vehicle_key_location=:customerVehicleKeyLocation, \r\n" + //
				"       customer_call_type=:customerCallType, \r\n" + //
				"       customer_payment_information=:customerPaymentInformation, \r\n" + //
				"       insert_by=:insertBy, \r\n" + //
				"       update_by=:updateBy, \r\n" + //
				"       update_time=:updateTime WHERE call_id = :callId";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("customerFirstName", call.getCustomer().getFirstName());
		params.addValue("customerLastName", call.getCustomer().getLastName());
		params.addValue("pickUpLocation", call.getPickUpLocation());
		params.addValue("dropOffLocation", call.getDropOffLocation());
		params.addValue("customerVehicleYear", call.getCustomer().getVehicle().getYear());
		params.addValue("customerVehicleMake", call.getCustomer().getVehicle().getMake());
		params.addValue("customerVehicleModel", call.getCustomer().getVehicle().getMake());
		params.addValue("customerVehicleColor", call.getCustomer().getVehicle().getColor());
		params.addValue("customerVehicleLiscensePlateNumber", call.getCustomer().getVehicle().getLicensePlateNumber());
		params.addValue("customerPhoneNumber", call.getCustomer().getPhoneNumber());
		params.addValue("customerVehicleKeyLocation", call.getCustomer().getVehicle().getKeyLocationType().getValue());
		params.addValue("customerCallType", call.getCallType().getValue());
		params.addValue("customerPaymentInformation", call.getCustomer().getVehicle().getMake());
		params.addValue("insertBy", 1);
		params.addValue("updateBy", 1);
		params.addValue("updateTime", Instant.now().getEpochSecond());
		params.addValue("callId", call.getId());

		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, params, keyHolder);

		// update call with primary key returned from DB
		call.setId((Long) keyHolder.getKeys().get("call_id"));
		return call;
	}

	@Override
	public Call getTruckActive(long truckId) {
		String sql = "select * from calls where truck_id = :truckId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("truckId", truckId);
		List<Call> callList = namedParameterJdbcTemplate.query(sql, params, new CallRowMapper());
		if(!callList.isEmpty()) {
			return callList.get(0);
		}
		return null;
	}
}
