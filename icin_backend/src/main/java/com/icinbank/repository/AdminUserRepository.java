package com.icinbank.repository;

import com.icinbank.model.AdminUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminUserRepository extends CrudRepository<AdminUser, Integer> {

    @Query(value = "SELECT * FROM admin_user WHERE username = :username", nativeQuery = true)
    public List<AdminUser> getUserByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM admin_user WHERE email = :email", nativeQuery = true)
    public List<AdminUser> getUserByEmail(@Param("email") String email);
}
