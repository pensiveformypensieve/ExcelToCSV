package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.domain.ParkingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ParkingTransactionRepository extends JpaRepository<ParkingTransaction, Long> {

    List<ParkingTransaction> findByEntrySessionIdAndExitSessionId(String entrySessionId, String exitSessionId);

}
