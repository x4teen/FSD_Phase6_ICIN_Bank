package com.icinbank.controller;

import com.icinbank.exceptionHandling.AccessDeniedException;
import com.icinbank.exceptionHandling.InsufficientFundException;
import com.icinbank.exceptionHandling.NotRecipientFoundException;
import com.icinbank.exceptionHandling.UserBlockedException;
import com.icinbank.model.*;
import com.icinbank.repository.AccountRepository;
import com.icinbank.repository.TransactionRepository;
import com.icinbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("accounts")
public class AccountController {

    Date date = new Date();

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("balance/{id}")
    public double getBalance(@PathVariable("id") int id) {
        return accountRepository.getBalanceByAccountId(id);
    }

    @PostMapping("deposit")
    public void deposit(@RequestBody DepositRequestBody depositBody) throws AccessDeniedException, UserBlockedException {
        int userId = depositBody.getUserId();
        User user = userRepository.getUserById(userId).get(0);
        if (user.isBlocked()) {
            throw new UserBlockedException("User is blocked.");
        }
        if (!user.isDepositAccess()) {
            throw new AccessDeniedException("Not allow to make deposit.");
        }
        int accountId;
        if (depositBody.isSaving()) {
            accountId = Integer.parseInt(String.valueOf(userId) + "02");
        } else {
            accountId = Integer.parseInt(String.valueOf(userId) + "01");
        }
        double amount = depositBody.getAmount();
        double oldBalance = accountRepository.getBalanceByAccountId(accountId);
        double newBalance = oldBalance + amount;
        accountRepository.changeBalance(newBalance, accountId);
    }

    @PostMapping("withdrawal")
    public void withdrawal(@RequestBody WithdrawalRequestBody withdrawalRequestBody) throws InsufficientFundException, AccessDeniedException, UserBlockedException {
        int userId = withdrawalRequestBody.getUserId();
        User user = userRepository.getUserById(userId).get(0);
        if (user.isBlocked()) {
            throw new UserBlockedException("User is blocked.");
        }
        if (!user.isWithdrawalAccess()) {
            throw new AccessDeniedException("Not allow to make withdrawal.");
        }
        int accountId;
        if (withdrawalRequestBody.isSaving()) {
            accountId = Integer.parseInt(String.valueOf(userId) + "02");
        } else {
            accountId = Integer.parseInt(String.valueOf(userId) + "01");
        }
        double amount = withdrawalRequestBody.getAmount();
        double availableAmount = accountRepository.getBalanceByAccountId(accountId);
        if (availableAmount < amount) {
            throw new InsufficientFundException("Insufficient fund in account.");
        }
        double newAmount = availableAmount - amount;
        accountRepository.changeBalance(newAmount, accountId);
    }

    @PostMapping("transfer")
    public void makeTransfer(@RequestBody TransferRequestBody transferBody) throws NotRecipientFoundException, InsufficientFundException, AccessDeniedException, UserBlockedException {
        int originId = transferBody.getUserIdFrom();
        User originUser = userRepository.getUserById(originId).get(0);
        if (originUser.isBlocked()) {
            throw new UserBlockedException("User is blocked.");
        }
        if (!originUser.isTransferAccess()) {
            throw new AccessDeniedException("Not allow to make transfer.");
        }
        String firstnameTo = transferBody.getFirstnameTo();
        String lastnameTo = transferBody.getLastnameTo();
        List<User> user = userRepository.getUserByFirstnameAndLastname(firstnameTo, lastnameTo);
        if (user == null || user.size() == 0) {
            throw new NotRecipientFoundException("Not recipient found.");
        }
        User recipient = user.get(0);
        double amount = transferBody.getAmount();
        User origin = userRepository.getUserById(transferBody.getUserIdFrom()).get(0);
        int accountIdFrom;
        int accountIdTo;
        if (transferBody.isFromSaving()) {
            accountIdFrom = Integer.parseInt(String.valueOf(origin.getId()) + "02");
        } else {
            accountIdFrom = Integer.parseInt(String.valueOf(origin.getId()) + "01");
        }
        double availableBalance = accountRepository.getBalanceByAccountId(accountIdFrom);
        if (availableBalance < amount) {
            throw new InsufficientFundException("Insufficient fund in account.");
        }

        if (transferBody.isToSaving()) {
            accountIdTo = Integer.parseInt(String.valueOf(recipient.getId()) + "02");
        } else {
            accountIdTo = Integer.parseInt(String.valueOf(recipient.getId()) + "01");
        }
        double origienBalanceAfter = availableBalance - amount;
        double recipientBalanceAfter = accountRepository.getBalanceByAccountId(accountIdTo) + amount;
        accountRepository.changeBalance(origienBalanceAfter, accountIdFrom);
        accountRepository.changeBalance(recipientBalanceAfter, accountIdTo);
        //transactionRepository.insertTransaction(accountIdFrom, origin.getId(), accountIdTo, recipient.getId(), amount, new Timestamp(date.getTime()));
        Transaction newTransaction = getNewTransaction(accountIdFrom, origin.getId(), accountIdTo, recipient.getId(), amount, new Timestamp(date.getTime()));
        transactionRepository.save(newTransaction);
    }

    private Transaction getNewTransaction(int accountIdFrom, int userIdFrom, int accountIdTo, int userIdTo, double amount, Timestamp time) {
        Transaction newTransaction = new Transaction();
        newTransaction.setAccountIdFrom(accountIdFrom);
        newTransaction.setAccountIdTo(accountIdTo);
        newTransaction.setAmount(amount);
        newTransaction.setUserIdFrom(userIdFrom);
        newTransaction.setUserIdTo(userIdTo);
        newTransaction.setTime(time);
        return newTransaction;
    }
}
