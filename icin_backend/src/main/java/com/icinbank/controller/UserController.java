package com.icinbank.controller;

import com.icinbank.exceptionHandling.LoginFailedException;
import com.icinbank.exceptionHandling.UserBlockedException;
import com.icinbank.exceptionHandling.UserNotFoundException;
import com.icinbank.model.User;
import com.icinbank.model.UserPostBody;
import com.icinbank.repository.AccountRepository;
import com.icinbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    //Login
    @PostMapping("login")
    public User login(@RequestBody UserPostBody userBody) throws UserNotFoundException, LoginFailedException, UserBlockedException {
        String email = userBody.getEmail();
        String password = userBody.getPassword();

        List<User> users = userRepository.getUserByEmail(email);
        if (users == null || users.size() == 0) {
            throw new UserNotFoundException("User not found.");
        }
        User user = users.get(0);
        if (!user.getPassword().equals(password)) {
            throw new LoginFailedException("Incorrect password.");
        }
        if (user.isBlocked()) {
            throw new UserBlockedException("Your account is blocked!");
        }
        return user;
    }

    @PostMapping("add")
    public User addUser(@RequestBody User newUser) {
        User user = userRepository.save(newUser);
        int userId = user.getId();
        int checkingAccountId = Integer.parseInt(String.valueOf(userId) + "01");
        int savingAccountId = Integer.parseInt(String.valueOf(userId) + "02");
        accountRepository.addNewAccount(checkingAccountId, userId, false, 0.00);
        accountRepository.addNewAccount(savingAccountId, userId, true, 0.00);
        return user;
    }
    
}
