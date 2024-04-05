package com.bankproject.hctbank.repository;

import com.bankproject.hctbank.model.CustDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustDetailsRepo extends JpaRepository<CustDetails,Long> {
}
