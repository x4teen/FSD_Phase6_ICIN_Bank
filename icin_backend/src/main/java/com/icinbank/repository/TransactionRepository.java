package com.icinbank.repository;

import com.icinbank.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {


    @Query(value = "INSERT INTO transaction (accountIdFrom, userIdFrom, accountIdTo, userIdTo, amount, time) " +
            "VALUES (:accountIdFrom, :userIdFrom, :accountIdTo, :userIdTo, :amount, :time)", nativeQuery = true)
    public List<Transaction> insertTransaction(@Param("accountIdFrom") int accountIdFrom, @Param("userIdFrom") int userIdFrom,
                                               @Param("accountIdTo") int accountIdTo, @Param("userIdTo") int userIdTo,
                                               @Param("amount") double amount, @Param("time") Timestamp time);

    @Query(value = "SELECT * FROM transaction WHERE account_id_from = :accountId OR account_id_to = :accountId", nativeQuery = true)
    public List<Transaction> getTransactions(@Param("accountId") int accountId);
}
