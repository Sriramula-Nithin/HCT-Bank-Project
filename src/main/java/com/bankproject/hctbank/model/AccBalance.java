package com.bankproject.hctbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AccBalance {
    @Id
    private long acc_Id;
    private double balance;

    public AccBalance()
    {

    }
    public AccBalance(long Acc_Id, double Balance)
    {
        this.acc_Id = Acc_Id;
        this.balance = Balance;
    }
}

