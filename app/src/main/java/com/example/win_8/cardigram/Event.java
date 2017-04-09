package com.example.win_8.cardigram;



public class Event {

	private String eventName;
	private String eventDate;
	private String eventTime;
	private String placeName;
	private double placeLat;
	private double placeLng;
	public Event(String eventName, String eventDate, String eventTime, String placeName, double placeLat, double placeLng) {
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.placeName = placeName;
		this.placeLat = placeLat;
		this.placeLng = placeLng;
	}

	public Event() {
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public double getPlaceLat() {
		return placeLat;
	}

	public void setPlaceLat(double placeLat) {
		this.placeLat = placeLat;
	}

	public double getPlaceLng() {
		return placeLng;
	}

	public void setPlaceLng(double placeLng) {
		this.placeLng = placeLng;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
}
