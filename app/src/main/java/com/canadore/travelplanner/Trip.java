package com.canadore.travelplanner;

public class Trip {
    private String tripId;
    private String cityName;

    // Empty constructor required for Firebase
    public Trip() {}

    public Trip(String tripId, String cityName) {
        this.tripId = tripId;
        this.cityName = cityName;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
