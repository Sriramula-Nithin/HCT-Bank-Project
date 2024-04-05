package com.bankproject.hctbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class CustAddress {
    @Id
    private Long address_Id;
    private String country;
    private String city;
    private String addressLine;
    private long pin;
    private Timestamp lastUpdate;

    public CustAddress() {

    }

    public CustAddress(long Address_Id, String Country, String City, String AddressLane, long PIN, Timestamp LastUpdate) {
        this.address_Id = Address_Id;
        this.country = Country;
        this.city = City;
        this.addressLine = AddressLane;
        this.pin = PIN;
        this.lastUpdate = LastUpdate;
    }
}