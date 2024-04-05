package com.bankproject.hctbank.repository;

import com.bankproject.hctbank.model.AccTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccTransactionRepo extends JpaRepository<AccTransaction,Long> {
    @Query(value = "select * from acc_transaction where acc_id=:accid", nativeQuery = true)
    List<AccTransaction> findByAccId(long accid);

    @Query(value = "select * from acc_transaction where transaction_ref_id=:l",nativeQuery = true)
    List<AccTransaction> findByTranRefId(long l);

    @Query(value = "select * from acc_transaction where transaction_ref_id=:transactionRefId and acc_id=:accId",nativeQuery = true)
    AccTransaction findByAccIdAndTranRefId(Long accId, Long transactionRefId);
}