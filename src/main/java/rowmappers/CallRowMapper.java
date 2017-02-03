package rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.perry.domain.Customer;
import com.perry.domain.Vehicle;
import com.perry.domain.call.Call;

public class CallRowMapper implements RowMapper<Call> {

	@Override
	public Call mapRow(ResultSet rs, int rowNum) throws SQLException {
		Call call = new Call();

		Vehicle vehicle = new Vehicle();
		vehicle.setMake(rs.getString("customer_vehicle_make"));
		vehicle.setModel(rs.getString("customer_vehicle_model"));
		vehicle.setYear(rs.getString("customer_vehicle_year"));
		vehicle.setColor(rs.getString("customer_vehicle_color"));
		vehicle.setLicensePlateNumber(rs.getString("customer_vehicle_license_plate_number"));

		Customer customer = new Customer();
		customer.setFirstName(rs.getString("customer_first_name"));
		customer.setLastName(rs.getString("customer_last_name"));
		customer.setPhoneNumber(rs.getString("customer_phone_number"));
		customer.setVehicle(vehicle);
		customer.setPriceQuote(rs.getString("customer_price_quote"));

		call.setId(rs.getLong("call_id"));
		call.setCustomer(customer);
		call.setPickUpLocation(rs.getString("pick_up_location"));
		call.setDropOffLocation(rs.getString("drop_off_location"));
		call.setTruckId(rs.getString(("truck_id")));
		call.setInsertTime(rs.getLong("insert_time"));

		return call;
	}
}
