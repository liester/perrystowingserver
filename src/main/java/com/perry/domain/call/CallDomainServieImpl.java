package com.perry.domain.call;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.perry.domain.truck.Truck;
import com.perry.infrastructure.call.CallDaoService;

@Named
public class CallDomainServieImpl implements CallDomainService {

	@Inject
	private CallDaoService callDaoService;

	@Override
	public List<Call> getByIds(List<Long> ids) {
		List<Call> callList = callDaoService.getByIds(ids);
		return callList;
	}

	@Override
	public Call create(Call call) {
		Call createdCall = callDaoService.create(call);
		return createdCall;
	}

	@Override
	public List<Call> getAllCalls() {
		List<Call> callList = callDaoService.getAllCalls();
		return callList;
	}

	@Override
	public Truck assignTruck(long callId, long truckId) {
		Truck updatedTruck = callDaoService.assignTruck(callId, truckId);
		return updatedTruck;

	}

	@Override
	public List<Call> getAvailable() {
		List<Call> callList = callDaoService.getAvailable();
		return callList;
	}

	@Override
	public void unAssignTruck(long callId) {
		callDaoService.unAssignTruck(callId);

	}

	@Override
	public void delete(long callId) {
		callDaoService.delete(callId);
	}

	@Override
	public Call edit(Call call) {
		Call updatedCall = callDaoService.edit(call);
		return updatedCall;
	}

	@Override
	public Call getTruckActive(long truckId) {
		return callDaoService.getTruckActive(truckId);
	}

	@Override
	public List<Call> getAllNonCompleteCalls() {
		List<Call> nonCompleteCalls = callDaoService.getAllNonCompleteCalls();
		return nonCompleteCalls;
	}

	@Override
	public File createCallBackupFile() throws IOException {
		List<Call> completedCalls = callDaoService.getCompletedCalls();
		File tempBackup = File.createTempFile("backup", ".csv");
		
		FileOutputStream fos = new FileOutputStream(tempBackup);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);
		for(Call call : completedCalls) {
			bw.write(writeCallAsString(call));
			bw.newLine();
		}
		bw.close();
		osw.close();
		fos.close();
		
		//TODO - Delete calls
		
		return tempBackup;
	}
	
	private String writeCallAsString(Call call) {
		StringBuffer sb = new StringBuffer();
		sb.append("\"").append(call.getCustomer().getFirstName()).append("\",");
		sb.append("\"").append(call.getCustomer().getLastName()).append("\",");
		sb.append("\"").append(call.getCustomer().getPhoneNumber()).append("\",");
		sb.append("\"").append(call.getCustomer().getPriceQuote()).append("\",");
		sb.append("\"").append(call.getCustomer().getPaymentType() != null ? call.getCustomer().getPaymentType().getValue() : " ").append("\",");
		if(call.getCustomer().getVehicle() != null) {
			sb.append("\"").append(call.getCustomer().getVehicle().getYear()).append("\",");
			sb.append("\"").append(call.getCustomer().getVehicle().getMake()).append("\",");
			sb.append("\"").append(call.getCustomer().getVehicle().getModel()).append("\",");
			sb.append("\"").append(call.getCustomer().getVehicle().getColor()).append("\",");
			sb.append("\"").append(call.getCustomer().getVehicle().getLicensePlateNumber()).append("\",");
			sb.append("\"").append(call.getCustomer().getVehicle().getKeyLocationType() != null ? call.getCustomer().getVehicle().getKeyLocationType().getValue() : " ").append("\",");
		} else {
			sb.append("\"\" ,");
			sb.append("\"\" ,");
			sb.append("\"\" ,");
			sb.append("\"\" ,");
			sb.append("\"\" ,");
			sb.append("\"\" ,");
		}
		sb.append("\"").append(call.getPickUpLocation()).append("\",");
		sb.append("\"").append(call.getDropOffLocation()).append("\",");
		sb.append("\"").append(call.getCallType() != null ? call.getCallType().getValue() : " ").append("\",");
		sb.append("\"").append(call.getTowTruckType() != null ? call.getTowTruckType().getValue() : " ").append("\",");
		sb.append("\"").append(call.getComment()).append("\",");
		return sb.toString();
	}
}