package com.residentevilsecurity.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "capitals")
public class Capital extends BaseEntity {
    private String name;
    private String latitude;
    private String longitude;

    public Capital() {
    }

    public String getName() {
        return name;
    }

    @Column(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    @Column(name = "latitude")
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Column(name = "longitude")
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
