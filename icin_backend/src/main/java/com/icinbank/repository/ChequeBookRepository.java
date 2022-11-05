package com.icinbank.repository;

import com.icinbank.model.ChequeBookRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChequeBookRepository extends CrudRepository<ChequeBookRequest, Integer> {


    @Modifying
    @Transactional
    @Query(value = "UPDATE cheque_book_request SET status = :status WHERE id = :id", nativeQuery = true)
    public int approveChequeRequest(@Param("id") int id, @Param("status") String status);

    @Query(value = "SELECT * FROM cheque_book_request WHERE user_id = :userId", nativeQuery = true)
    public List<ChequeBookRequest> getRequestsByUserId(@Param("userId") int userId);

    //Get all pending request
    @Query(value = "SELECT * FROM cheque_book_request WHERE status = 'REQUESTED'", nativeQuery = true)
    public List<ChequeBookRequest> getAllPendingRequest();
}
