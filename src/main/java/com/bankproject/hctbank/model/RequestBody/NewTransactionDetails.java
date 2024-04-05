package com.bankproject.hctbank.model.RequestBody;

import lombok.Data;

@Data
public class NewTransactionDetails {
    private long acc_id;
    private long to_acc_id;
    private String type;
    private double amount;
    public NewTransactionDetails(long acc_id, long to_acc_id, String type, double amount)
    {
        this.acc_id=acc_id;
        this.to_acc_id=to_acc_id;
        this.type=type;
        this.amount=amount;
    }
}
