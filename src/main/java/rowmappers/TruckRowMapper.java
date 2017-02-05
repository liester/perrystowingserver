package rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.perry.domain.truck.Truck;
import com.perry.domain.truck.TruckStatusType;

public class TruckRowMapper implements RowMapper<Truck> {

	@Override
	public Truck mapRow(ResultSet rs, int rowNum) throws SQLException {
		Truck truck = new Truck();
		truck.setId(rs.getLong("truck_id"));
		truck.setInsertBy(rs.getLong("insert_by"));
		truck.setUpdateBy(rs.getLong("update_by"));
		truck.setInsertTime(rs.getLong("insert_time"));
		truck.setUpdateTime(rs.getLong("insert_time"));
		truck.setIdentifier(rs.getString("identifier"));
		truck.setDriverFirstName(rs.getString("driver_first_name"));
		truck.setDriverLastName(rs.getString("driver_last_name"));
		truck.setGisLatitude(rs.getString("gis_latitude"));
		truck.setGisLongitude(rs.getString("gis_longitude"));
		truck.setTruckStatusType(TruckStatusType.fromValue(rs.getString("status")));
		truck.setActiveCallId(rs.getLong("active_call_id"));
		truck.setQueuedCallId(rs.getLong("queued_call_id"));

		return truck;
	}
}
