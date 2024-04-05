package com.bankproject.hctbank.controller;

import com.bankproject.hctbank.ExceptionHandling.CustomerIdNotFound;
import com.bankproject.hctbank.model.CustDetails;
import com.bankproject.hctbank.model.RequestBody.NewCustomerDetails;
import com.bankproject.hctbank.model.RequestBody.NewTransactionDetails;
import com.bankproject.hctbank.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hctbank")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public Object newCustomer(@RequestBody NewCustomerDetails newCustomerDetails)
    {
        return customerService.newCustomer(newCustomerDetails);
    }

    @GetMapping("/customer")
    public Object getCustomerDetails(@RequestParam(name = "custId") long custId)
    {
        return customerService.getCustomerDetails(custId);
    }

    @GetMapping("/customers")
    public List<CustDetails> getAllCustomerDetails()
    {
        return customerService.getAllCustomerDetails();
    }

    @GetMapping("balances")
    public ResponseEntity<Object> getBalance(@RequestParam(required = false) String cust_id, @RequestParam(required = false) String acc_id ){
        if( cust_id ==null && acc_id ==null){
            try {
                throw new CustomerIdNotFound("Required Query parameter's are not provided!.", "HCTB400");
            }
            catch (CustomerIdNotFound e)
            {
                return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("message : "+e.getMessage()+"\nreason code : "+e.getReason());
            }
        }
        else if(cust_id!=null && acc_id == null){
            return customerService.getBalanceByCustId(cust_id);
        }
        else if(cust_id == null && acc_id != null){
            return customerService.getBalanceByAccId(acc_id);
        }
        else return customerService.getBalanceByAccIdOrCustId(acc_id, cust_id);
    }
    @PostMapping("/transactions")
    public ResponseEntity<Object> newTransaction(@RequestBody NewTransactionDetails newTransactionDetails)
    {
        if(newTransactionDetails.getType().equalsIgnoreCase("credit")) {
            return customerService.newTransaction(newTransactionDetails);
        }
        else
        {
            try {
                throw new CustomerIdNotFound("Transaction not supported","HCTB400");
            }
            catch (CustomerIdNotFound e)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("message : "+e.getMessage()+"\nreason code : "+e.getReason());
            }
        }
    }

    @GetMapping("/transaction")
    public ResponseEntity<Object> getTransactions(@RequestParam(required = false) Long accId, @RequestParam(required = false) Long transactionRefId)
    {
        if (accId != null && transactionRefId != null)
            return customerService.transactionByRefIdAndAccId(accId,transactionRefId);
        else if (accId != null && transactionRefId == null)
            return customerService.transactionByAccId(accId);
        else if (transactionRefId != null && accId == null)
            return customerService.transactionByRefId(transactionRefId);
        else {
            try{
                throw new CustomerIdNotFound("Invalid Request","HCTB400");
            }
            catch (CustomerIdNotFound e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("message : " + e.getMessage()+"\nresponse code : "+e.getReason());
            }
        }
    }
}
