package com.residentevilsecurity.domain.model.service;

public class CapitalServiceModel extends BaseServiceModel {
    private String name;
    private String latitude;
    private String longitude;

    public CapitalServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
