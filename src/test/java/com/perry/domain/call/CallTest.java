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
		String truckId = "1k8";
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
		String truckId = "1k8";
		TowTruckType towTruckType = TowTruckType.FIRST_AVAILABLE;

		Call call = new Call(customer, pickUpLocation, dropOffLocation, callType, truckId, towTruckType);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(call));
	}

	@Test
	public void parseJson() throws JsonParseException, JsonMappingException, IOException {
		String json = "{\"customer\":{\"firstName\":\"1\",\"lastName\":\"2\",\"phoneNumber\":\"3\",\"priceQuote\":\"4\",\"vehicle\":{\"keyLocationType\":\"1\",\"make\":\"7\",\"model\":\"9\",\"year\":\"8\",\"color\":\"10\",\"licensePlateNumber\":\"19\"},\"towTruckType\":\"1\"},\"callType\":\"1\",\"pickUpLocation\":\"5\",\"dropOffLocation\":\"6\",\"id\":\"\"}";
		ObjectMapper mapper = new ObjectMapper();
		Call call = mapper.readValue(json, Call.class);
		System.out.println(mapper.writeValueAsString(call));
	}

}
