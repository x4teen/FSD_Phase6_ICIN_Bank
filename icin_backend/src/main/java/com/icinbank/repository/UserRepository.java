package com.icinbank.repository;

import com.icinbank.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
    public List<User> getUserByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
    public List<User> getUserByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM user WHERE firstname = :firstname AND lastname = :lastname", nativeQuery = true)
    public List<User> getUserByFirstnameAndLastname(@Param("firstname") String firstname, @Param("lastname") String lastname);

    @Query(value = "SELECT * FROM user WHERE id = :id", nativeQuery = true)
    public List<User> getUserById(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET deposit_access = :accessible WHERE id = :id", nativeQuery = true)
    public int updateDepositAccess(@Param("id") int id, @Param("accessible") boolean accessible);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET withdrawal_access = :accessible WHERE id = :id", nativeQuery = true)
    public int updateWithdrawalAccess(@Param("id") int id, @Param("accessible") boolean accessible);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET transfer_access = :accessible WHERE id = :id", nativeQuery = true)
    public int updateTransferAccess(@Param("id") int id, @Param("accessible") boolean accessible);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET blocked = :accessible WHERE id = :id", nativeQuery = true)
    public int updateBlockStatus(@Param("id") int id, @Param("accessible") boolean accessible);
}
