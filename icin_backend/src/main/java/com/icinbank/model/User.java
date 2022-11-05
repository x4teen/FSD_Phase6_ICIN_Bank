package com.icinbank.model;

import javax.persistence.*;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstname;

    private String lastname;

    private String email;

    private String username;

    private String password;

    @Column(columnDefinition = "boolean default false")
    private boolean blocked;

    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean transferAccess = true;

    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean depositAccess = true;

    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean withdrawalAccess = true;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isTransferAccess() {
        return transferAccess;
    }

    public void setTransferAccess(boolean transferAccess) {
        this.transferAccess = transferAccess;
    }

    public boolean isDepositAccess() {
        return depositAccess;
    }

    public void setDepositAccess(boolean depositAccess) {
        this.depositAccess = depositAccess;
    }

    public boolean isWithdrawalAccess() {
        return withdrawalAccess;
    }

    public void setWithdrawalAccess(boolean withdrawalAccess) {
        this.withdrawalAccess = withdrawalAccess;
    }
}
