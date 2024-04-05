package com.bankproject.hctbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Cust_Acc_Map {
    @Id
    private long acc_Id;
    private long cust_Id;

    public Cust_Acc_Map() {
    }

    public Cust_Acc_Map(long Acc_Id, long Cust_Id) {
        this.acc_Id = Acc_Id;
        this.cust_Id = Cust_Id;
    }
}