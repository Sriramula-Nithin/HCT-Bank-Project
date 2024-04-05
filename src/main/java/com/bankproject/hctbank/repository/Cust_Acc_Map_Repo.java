package com.bankproject.hctbank.repository;

import com.bankproject.hctbank.model.Cust_Acc_Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Cust_Acc_Map_Repo extends JpaRepository<Cust_Acc_Map,Long> {
    @Query(value = "select * from Cust_Acc_Map where cust_Id=:custId ",nativeQuery = true)
    Cust_Acc_Map findByCust_Id(long custId);
}
