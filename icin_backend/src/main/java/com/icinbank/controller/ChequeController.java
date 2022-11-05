package com.icinbank.controller;

import com.icinbank.model.*;
import com.icinbank.repository.ChequeBookRepository;
import com.icinbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("cheque")
public class ChequeController {

    @Autowired
    ChequeBookRepository chequeBookRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("allPending")
    public List<pendingChequeBookRequest> getAllPendingRequest() {
        List<ChequeBookRequest> requests = chequeBookRepository.getAllPendingRequest();
        List<pendingChequeBookRequest> result = new ArrayList<>();
        for (ChequeBookRequest request : requests) {
            int requestId = request.getId();
            int accountId = request.getAccountId();
            int quantity = request.getQuantity();
            int userId = request.getUserId();
            String account = accountId % 2 == 0 ? "saving" : "checking";
            String status = request.getStatus();
            User customer = userRepository.getUserById(userId).get(0);
            String name = customer.getFirstname() + " " + customer.getLastname();
            pendingChequeBookRequest pendingRequest = new pendingChequeBookRequest(requestId, name, account, quantity, status);
            result.add(pendingRequest);
        }
        return result;
    }

    @GetMapping("get/{userId}")

    public List<ChequeBookRequest> getRequestsByUserId(@PathVariable("userId") int userId) {
        return chequeBookRepository.getRequestsByUserId(userId);
    }

    @PostMapping("approve")
    public void approveRequest(@RequestBody UpdateRequestBody updateRequestBody) {
        int id = updateRequestBody.getId();
        String status = updateRequestBody.getStatus();
        chequeBookRepository.approveChequeRequest(id, status);
    }

    @PostMapping("request")
    public void requestChequeBook(@RequestBody ChequeRequestBody chequeRequestBody) {
        int userId = chequeRequestBody.getUserId();
        int quantity = chequeRequestBody.getQuantity();
        boolean isSaving = chequeRequestBody.isSaving();
        int accountId;
        if (isSaving) {
            accountId = Integer.parseInt(String.valueOf(userId) + "02");
        } else {
            accountId = Integer.parseInt(String.valueOf(userId) + "01");
        }
        ChequeBookRequest newChequeBookRequest = getNewChequeBookRequest(userId, quantity, accountId);
        chequeBookRepository.save(newChequeBookRequest);
    }

    private ChequeBookRequest getNewChequeBookRequest(int userId, int quantity, int accountId) {
        ChequeBookRequest newChequeBookRequest = new ChequeBookRequest();
        newChequeBookRequest.setUserId(userId);
        newChequeBookRequest.setQuantity(quantity);
        newChequeBookRequest.setAccountId(accountId);
        return newChequeBookRequest;
    }

}
