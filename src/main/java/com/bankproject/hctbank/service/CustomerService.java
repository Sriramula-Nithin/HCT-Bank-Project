package com.bankproject.hctbank.service;

import com.bankproject.hctbank.ExceptionHandling.CustomerIdNotFound;
import com.bankproject.hctbank.controller.RandomNumbers;
import com.bankproject.hctbank.model.*;
import com.bankproject.hctbank.model.RequestBody.NewCustomerDetails;
import com.bankproject.hctbank.model.RequestBody.NewTransactionDetails;
import com.bankproject.hctbank.model.Responses.BalanceResponse;
import com.bankproject.hctbank.model.Responses.NewCustomerResponse;
import com.bankproject.hctbank.model.Responses.NewTransactionResponse;
import com.bankproject.hctbank.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerService {

    private final CustDetailsRepo custDetailsRepo;
    private final CustomerAddressRepo customerAddressRepo;
    private final AccBalanceRepo accBalanceRepo;
    private final Cust_Acc_Map_Repo custAccMapRepo;
    private final AccTransactionRepo accTransactionRepo;

    public CustomerService(CustDetailsRepo custDetailsRepo, CustomerAddressRepo customerAddressRepo, AccBalanceRepo accBalanceRepo, Cust_Acc_Map_Repo custAccMapRepo, AccTransactionRepo accTransactionRepo) {
        this.custDetailsRepo = custDetailsRepo;
        this.customerAddressRepo = customerAddressRepo;
        this.accBalanceRepo = accBalanceRepo;
        this.custAccMapRepo = custAccMapRepo;
        this.accTransactionRepo = accTransactionRepo;
    }

    public Object newCustomer(NewCustomerDetails newCustomerDetails)
    {
        System.out.println(newCustomerDetails);
        RandomNumbers randomNumbers=new RandomNumbers();
        long cust_Id= randomNumbers.getRandom();
        long address_Id= randomNumbers.getRandom();
        long acc_Id=randomNumbers.getRandom();
        Timestamp currentTimestamp = new Timestamp(new Date().getTime());

        CustDetails cd=new CustDetails(cust_Id,newCustomerDetails.getName(),newCustomerDetails.getPhone(),address_Id,newCustomerDetails.getEmail(),currentTimestamp,currentTimestamp);
        CustAddress ca=new CustAddress(address_Id,newCustomerDetails.getCountry(),newCustomerDetails.getCity(),newCustomerDetails.getAddressLine(),newCustomerDetails.getPin(),currentTimestamp);
        customerAddressRepo.save(ca);
        custDetailsRepo.save(cd);

        AccBalance accBalance=new AccBalance(acc_Id,500.00);
        accBalanceRepo.save(accBalance);

        Cust_Acc_Map cam=new Cust_Acc_Map(acc_Id,cust_Id);
        custAccMapRepo.save(cam);

        return new NewCustomerResponse(newCustomerDetails.getName(),cd.getCust_Id(),accBalance.getAcc_Id(),accBalance.getBalance());
    }

    public ResponseEntity<Object> getCustomerDetails(Long custId) {
            try {
                Optional<CustDetails> customerOpt = custDetailsRepo.findById(custId);
                if(customerOpt.isPresent())
                {
                    return ResponseEntity.ok(customerOpt.get());
                }
                else {
                    throw new CustomerIdNotFound("Customer with the provided ID not found.","HCTB404");
                }
            } catch (CustomerIdNotFound e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("message : "+e.getMessage()+"\nreason code : "+e.getReason());
            }
    }

    public List<CustDetails> getAllCustomerDetails()
    {
        return custDetailsRepo.findAll();
    }

    public ResponseEntity<Object> getBalanceByCustId(String cust_Id)
    {
        try{
            Cust_Acc_Map cap=custAccMapRepo.findByCust_Id(Long.parseLong(cust_Id));
            if(cap == null)
                throw new CustomerIdNotFound("Provided input query params are Invalid!.","HCTB404");
            Optional<AccBalance> ab=accBalanceRepo.findById(cap.getAcc_Id());
            if(ab.isPresent()){
                return ResponseEntity.ok(new BalanceResponse(cap.getAcc_Id(), ab.get().getBalance()));
            }
            else
                throw new CustomerIdNotFound("Provided input query params are Invalid!.","HCTB404");
        }
        catch (CustomerIdNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("message : "+e.getMessage()+"\nreason code : "+e.getReason());
        }
    }

    public ResponseEntity<Object> getBalanceByAccId(String acc_Id)
    {
        try{
            Optional<AccBalance> ab=accBalanceRepo.findById(Long.parseLong(acc_Id));
            if(ab.isPresent()){
                return ResponseEntity.ok(new BalanceResponse(ab.get().getAcc_Id(),ab.get().getBalance()));
            }
            else
                throw new CustomerIdNotFound("Provided input query params are Invalid!.","HCTB404");
        }
        catch (CustomerIdNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("message : "+e.getMessage()+"\nreason code : "+e.getReason());
        }
    }

    public ResponseEntity<Object> getBalanceByAccIdOrCustId(String acc_id, String cust_id)
    {
        try {
            Optional<Cust_Acc_Map> cap=custAccMapRepo.findById(Long.parseLong(acc_id));
            System.out.println(cap);
            if(cap.isEmpty()){
                System.out.println(cap);
                throw new CustomerIdNotFound("Provided input query params are Invalid!.","HCTB404");
            }

            Optional<AccBalance> ab=accBalanceRepo.findById(cap.get().getAcc_Id());
            if (cap.get().getCust_Id()==Long.parseLong(cust_id))
                return ResponseEntity.ok(new BalanceResponse(ab.get().getAcc_Id(), ab.get().getBalance()));
            else {
                throw new CustomerIdNotFound("Provided input query params are Invalid!.","HCTB404");
            }
        }
        catch (CustomerIdNotFound e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("message : "+e.getMessage()+"\nreason code : "+e.getReason());
        }
    }

    public ResponseEntity<Object> newTransaction(NewTransactionDetails newTransactionDetails)
    {
        long accId=newTransactionDetails.getAcc_id();
        long toAccId=newTransactionDetails.getTo_acc_id();
        double amount=newTransactionDetails.getAmount();

        RandomNumbers rn=new RandomNumbers();
        long senderTranId= rn.getRandom();
        long receiverTranId= rn.getRandom();
        long TranRefId= rn.getRandom();
        Timestamp currentTimestamp = new Timestamp(new Date().getTime());

        Optional<AccBalance> accBalance=accBalanceRepo.findById(accId);
        try {
            if (accBalance.isEmpty())
                throw new CustomerIdNotFound("Provided Details are Invalid!..Sender Acc Id not found","HCTB400");
            if (accBalance.get().getBalance() >= amount)
            {
                AccBalance ab=accBalance.get();
                ab.setBalance(ab.getBalance()-amount);
                AccTransaction senderTransaction=new AccTransaction(senderTranId, TranRefId, accId, 0, amount, accBalance.get().getBalance(), currentTimestamp);
                accTransactionRepo.save(senderTransaction);
                accBalanceRepo.save(ab);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insufficient funds!...");
            }
        }
        catch(CustomerIdNotFound e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("message : "+e.getMessage()+"\nreason code : "+e.getReason());
        }
        Optional<AccBalance> receiverOptional = accBalanceRepo.findById(toAccId);
        try {
            if (receiverOptional.isEmpty())
                throw new CustomerIdNotFound("Invalid Reciever Id", "HCTB400");
            AccBalance receiver = receiverOptional.get();
            double receiverBalance = receiver.getBalance();
            receiver.setBalance(receiverBalance + amount);
            AccTransaction at2 = new AccTransaction(receiverTranId, TranRefId, toAccId, amount, 0, receiverBalance, currentTimestamp);
            accTransactionRepo.save(at2);
            accBalanceRepo.save(receiver);
        }
        catch (CustomerIdNotFound e) {
            //return new CustomerIdNotFOund("fds","tre");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("message : "+e.getMessage()+"\reason code : "+e.getReason());
        }
        return ResponseEntity.ok(new NewTransactionResponse("Transaction Success", "HCT200", TranRefId));
    }

    public ResponseEntity<Object> transactionByRefIdAndAccId(long accId, long transactionRefId) {
        List<AccTransaction> accTransactions = accTransactionRepo.findByAccId(accId);
        Optional<AccTransaction> transaction = Optional.ofNullable(accTransactionRepo.findByAccIdAndTranRefId(accId, transactionRefId));

        if (accTransactions.isEmpty()) {
            try{
                throw new CustomerIdNotFound("Invalid AccId: No transactions found for the provided account ID","HCTB404");
            }
            catch (CustomerIdNotFound e) {
                // No transactions found for the given accId
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("message : "+e.getMessage()+"\n"+"reason code : "+e.getReason());
            }
        } else {
            if (transaction.isPresent()) {
                // Transaction found for the provided accId and transactionRefId
                return ResponseEntity.ok(transaction.get());
            } else {
                try{
                    throw new CustomerIdNotFound("Invalid TransactionReferenceID. No transaction found with the provided transaction reference ID","HCTB404");
                }
                catch (CustomerIdNotFound e) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("message : "+e.getMessage()+"\n"+"reason code : "+e.getReason());
                }
            }
        }
    }

    public ResponseEntity<Object> transactionByAccId(long accId)
    {
        List<AccTransaction> transaction = accTransactionRepo.findByAccId(accId);
        if(transaction.isEmpty())
        {
            try
            {
                throw new CustomerIdNotFound("Invalid Account Id", "HCTB404");
            }
            catch (CustomerIdNotFound e)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("message : "+e.getMessage()+"\n"+"reason code : "+e.getReason());
            }
        }
        else
        {
            return ResponseEntity.ok(accTransactionRepo.findByAccId(accId));
        }
    }

    public ResponseEntity<Object> transactionByRefId(long transactionRefId)
    {
        List<AccTransaction> transaction=accTransactionRepo.findByTranRefId(transactionRefId);
        if(transaction.isEmpty())
        {
            try
            {
                throw new CustomerIdNotFound("Invalid Transaction reference Id", "HCTB404");
            }
            catch (CustomerIdNotFound e)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("message : "+e.getMessage()+"\n"+"reason code : "+e.getReason());
            }
        }
        else {
            return ResponseEntity.ok(accTransactionRepo.findByTranRefId(transactionRefId));
        }
    }
}
