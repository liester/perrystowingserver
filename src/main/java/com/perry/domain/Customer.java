package com.perry.domain;

public class Customer {
	private String firstName;

	private String lastName;

	private String phoneNumber;

	private Vehicle vehicle;

	private String priceQuote;
	
	private PaymentType paymentType;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public String getPriceQuote() {
		return priceQuote;
	}

	public void setPriceQuote(String priceQuote) {
		this.priceQuote = priceQuote;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}
	
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
}
