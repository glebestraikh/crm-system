package com.glebestraikh.crmsystem.data.repository;

import com.glebestraikh.crmsystem.data.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("SELECT s FROM Seller s JOIN s.transactions t " +
            "WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
            "GROUP BY s.id " +
            "ORDER BY SUM(t.amount) DESC")
    List<Seller> findMostProductiveSeller(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    @Query("SELECT s FROM Seller s LEFT JOIN s.transactions t " +
            "WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
            "GROUP BY s.id " +
            "HAVING SUM(t.amount) < :threshold")
    List<Seller> findSellersWithTransactionSumLessThan(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("threshold") Integer threshold);

}
