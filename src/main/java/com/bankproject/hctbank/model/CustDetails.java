package com.bankproject.hctbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class CustDetails {
    @Id
    private long cust_Id;
    private String name;
    private long phone;
    private long address_Id;
    private String email;
    private Timestamp created;
    private Timestamp lastUpdated;

    public CustDetails() {

    }

    public CustDetails(long Cust_Id, String Name, long Phone, long Address_Id, String Email, Timestamp Created, Timestamp LastUpdated) {
        this.cust_Id = Cust_Id;
        this.name = Name;
        this.phone = Phone;
        this.address_Id = Address_Id;
        this.email = Email;
        this.created = Created;
        this.lastUpdated = LastUpdated;
    }
}
