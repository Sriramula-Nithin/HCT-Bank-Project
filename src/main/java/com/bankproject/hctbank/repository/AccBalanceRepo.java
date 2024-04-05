package com.bankproject.hctbank.repository;

import com.bankproject.hctbank.model.AccBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccBalanceRepo extends JpaRepository<AccBalance,Long> {
}
