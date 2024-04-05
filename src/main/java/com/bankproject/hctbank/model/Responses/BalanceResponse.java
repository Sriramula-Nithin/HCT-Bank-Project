package com.bankproject.hctbank.model.Responses;

import lombok.Data;
@Data
public class BalanceResponse {
    private long acc_Id;
    private double balance;

    public BalanceResponse(long accId, double balance) {
        this.acc_Id=accId;
        this.balance=balance;
    }
}
