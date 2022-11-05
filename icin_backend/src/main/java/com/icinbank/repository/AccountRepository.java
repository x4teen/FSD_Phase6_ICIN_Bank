package com.icinbank.repository;

import com.icinbank.model.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO account (id, user_id, saving, balance) VALUES (:id, :user_id, :saving, :balance)", nativeQuery = true)
    public int addNewAccount(@Param("id") int id, @Param("user_id") int user_id, @Param("saving") boolean saving, @Param("balance") double balance);

    @Query(value = "SELECT balance FROM account WHERE id = :id", nativeQuery = true)
    public double getBalanceByAccountId(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE account SET balance = :balance WHERE id = :id", nativeQuery = true)
    public int changeBalance(@Param("balance") double balance, @Param("id") int id);
}
