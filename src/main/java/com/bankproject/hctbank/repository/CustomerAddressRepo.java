package com.bankproject.hctbank.repository;

import com.bankproject.hctbank.model.CustAddress;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerAddressRepo extends JpaRepository<CustAddress, Long> {
}
