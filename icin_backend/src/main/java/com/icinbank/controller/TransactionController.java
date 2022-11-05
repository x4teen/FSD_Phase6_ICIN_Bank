package com.icinbank.controller;

import com.icinbank.model.Transaction;
import com.icinbank.model.TransactionHistory;
import com.icinbank.model.User;
import com.icinbank.repository.TransactionRepository;
import com.icinbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    //Get transaction history
    @GetMapping("getHistory/{id}")
    public List<TransactionHistory> getTransactionHistory(@PathVariable("id") int id) {
        List<TransactionHistory> history = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.getTransactions(id);

        for (Transaction transaction : transactions) {
            TransactionHistory th = new TransactionHistory();
            User fromUser = userRepository.getUserById(transaction.getUserIdFrom()).get(0);
            User toUser = userRepository.getUserById(transaction.getUserIdTo()).get(0);
            double amount = transaction.getAmount();
            int accountIdFrom = transaction.getAccountIdFrom();
            int accountIdTo = transaction.getAccountIdTo();
            String fromName = fromUser.getFirstname() + " " + fromUser.getLastname();
            String toName = toUser.getFirstname() + " " + toUser.getLastname();
            String fromAccount = accountIdFrom % 2 == 0 ? "saving" : "checking";
            String toAccount = accountIdTo % 2 == 0 ? "saving" : "checking";
            th.setAmount(amount);
            th.setFrom(fromName);
            th.setTo(toName);
            th.setFromAccount(fromAccount);
            th.setToAccount(toAccount);
            history.add(th);
        }
        return history;
    }
}
