package com.bankproject.hctbank.model.Responses;


import lombok.Data;

@Data
public class NewCustomerResponse {

    private String name;
    private long cust_Id;
    private long acc_Id;
    private double balance;
    public NewCustomerResponse(String Name, long cus_id, long acc_id, double balance) {
        this.name = Name;
        this.cust_Id = cus_id;
        this.acc_Id = acc_id;
        this.balance = balance;
    }
}
