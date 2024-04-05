package com.bankproject.hctbank.model.Responses;

import lombok.Data;

@Data
public class NewTransactionResponse {
    private String message;
    private String statusCode;
    private long transactionRefId;

    public NewTransactionResponse(String message,String statusCode, long transactionRefId)
    {
        this.message=message;
        this.statusCode=statusCode;
        this.transactionRefId=transactionRefId;
    }
}
