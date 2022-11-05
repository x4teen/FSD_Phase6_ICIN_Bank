package com.icinbank.controller;

import com.icinbank.exceptionHandling.LoginFailedException;
import com.icinbank.exceptionHandling.UserNotFoundException;
import com.icinbank.model.AccessUpdateBody;
import com.icinbank.model.AdminUser;
import com.icinbank.model.User;
import com.icinbank.model.UserPostBody;
import com.icinbank.repository.AdminUserRepository;
import com.icinbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminUserController {

    @Autowired
    AdminUserRepository adminUserRepository;

    @Autowired
    UserRepository userRepository;

    //Change Deposit access
    @PostMapping("updateDepositAccess")
    public void updateDepositAccess(@RequestBody AccessUpdateBody updateBody) throws UserNotFoundException {
        String firstname = updateBody.getFirstname();
        String lastname = updateBody.getLastname();
        List<User> users = userRepository.getUserByFirstnameAndLastname(firstname, lastname);
        if (users == null || users.size() == 0) {
            throw new UserNotFoundException("Not such user found.");
        }
        User user = users.get(0);
        int userId = user.getId();
        boolean accessible = updateBody.isAccessible();
        userRepository.updateDepositAccess(userId, accessible);
    }

    //Change Withdrawal access
    @PostMapping("updateWithdrawalAccess")
    public void updateWithdrawalAccess(@RequestBody AccessUpdateBody updateBody) throws UserNotFoundException {
        String firstname = updateBody.getFirstname();
        String lastname = updateBody.getLastname();
        List<User> users = userRepository.getUserByFirstnameAndLastname(firstname, lastname);
        if (users == null || users.size() == 0) {
            throw new UserNotFoundException("Not such user found.");
        }
        User user = users.get(0);
        int userId = user.getId();
        boolean accessible = updateBody.isAccessible();
        userRepository.updateWithdrawalAccess(userId, accessible);
    }

    //Change Transfer access
    @PostMapping("updateTransferAccess")
    public void updateTransferAccess(@RequestBody AccessUpdateBody updateBody) throws UserNotFoundException {
        String firstname = updateBody.getFirstname();
        String lastname = updateBody.getLastname();
        List<User> users = userRepository.getUserByFirstnameAndLastname(firstname, lastname);
        if (users == null || users.size() == 0) {
            throw new UserNotFoundException("Not such user found.");
        }
        User user = users.get(0);
        int userId = user.getId();
        boolean accessible = updateBody.isAccessible();
        userRepository.updateTransferAccess(userId, accessible);
    }

    //Change Block Status
    @PostMapping("updateBlockStatus")
    public void updateBlockStatus(@RequestBody AccessUpdateBody updateBody) throws UserNotFoundException {
        String firstname = updateBody.getFirstname();
        String lastname = updateBody.getLastname();
        List<User> users = userRepository.getUserByFirstnameAndLastname(firstname, lastname);
        if (users == null || users.size() == 0) {
            throw new UserNotFoundException("Not such user found.");
        }
        User user = users.get(0);
        int userId = user.getId();
        boolean accessible = updateBody.isAccessible();
        userRepository.updateBlockStatus(userId, accessible);
    }

    //Login
    @PostMapping("login")
    public AdminUser login(@RequestBody UserPostBody userBody) throws UserNotFoundException, LoginFailedException {
        String email = userBody.getEmail();
        String password = userBody.getPassword();

        List<AdminUser> users = adminUserRepository.getUserByEmail(email);
        if (users == null || users.size() == 0) {
            throw new UserNotFoundException("User not found.");
        }
        AdminUser user = users.get(0);
        if (!user.getPassword().equals(password)) {
            throw new LoginFailedException("Incorrect password.");
        }

        return user;
    }
}
