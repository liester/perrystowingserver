package com.perry.domain.call;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perry.domain.CallType;
import com.perry.domain.Customer;
import com.perry.domain.KeyLocationType;
import com.perry.domain.Vehicle;

public class CallTest {

	@Test
	public void constructor() {
		Customer customer = new Customer();
		String pickUpLocation = "Pick Up Location";
		String dropOffLocation = "Drop Off Location";
		CallType callType = CallType.IMPOUND;
		long truckId = 1;
		TowTruckType requiredTruckType = TowTruckType.FIRST_AVAILABLE;

		Call call = new Call(customer, pickUpLocation, dropOffLocation, callType, truckId, requiredTruckType);

		assertThat(call.getCustomer(), equalTo(customer));
		assertThat(call.getPickUpLocation(), equalTo(pickUpLocation));
		assertThat(call.getDropOffLocation(), equalTo(dropOffLocation));
		assertThat(call.getCallType(), equalTo(callType));
		assertThat(call.getTruckId(), equalTo(truckId));
		assertThat(call.getTowTruckType().getId(), equalTo(requiredTruckType.getId()));
	}

	@Ignore
	@Test
	public void createJsonTest() throws JsonProcessingException {
		Vehicle vehicle = new Vehicle();
		vehicle.setColor("Red");
		vehicle.setKeyLocationType(KeyLocationType.CALL_FOR_KEYS);
		vehicle.setLicensePlateNumber("187 988");
		vehicle.setMake("Dodge");
		vehicle.setModel("Avenger");
		vehicle.setYear("2010");
		String pickUpLocation = "Pick Up Location";
		String dropOffLocation = "Drop Off Location";

		Customer customer = new Customer();
		customer.setFirstName("Levi");
		customer.setLastName("Liester");
		customer.setPhoneNumber("1-238-722-9888");
		customer.setVehicle(vehicle);
		CallType callType = CallType.IMPOUND;
		long truckId = 1;
		TowTruckType towTruckType = TowTruckType.FIRST_AVAILABLE;

		Call call = new Call(customer, pickUpLocation, dropOffLocation, callType, truckId, towTruckType);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(call));
	}

}
