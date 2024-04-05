package com.bankproject.hctbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class AccTransaction {
    @Id
    private long transaction_id;

    private long transactionRefId;
    private long acc_Id;
    private  double credit;
    private  double debit;
    private  double avlBalance;
    private Timestamp lastUpdated;



    public AccTransaction(long TransactionId, long TransactionRefId, long Acc_Id, double Credit, double Debit, double AvlBalance, Timestamp LastUpdated) {
        this.transaction_id = TransactionId;
        this.transactionRefId = TransactionRefId;
        this.acc_Id = Acc_Id;
        this.credit = Credit;
        this.debit = Debit;
        this.avlBalance = AvlBalance;
        this.lastUpdated = LastUpdated;
    }

    public AccTransaction() {

    }
}
