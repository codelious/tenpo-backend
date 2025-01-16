package com.tenpo.tenpobackend.controller;

import com.tenpo.tenpobackend.model.Transaction;
import com.tenpo.tenpobackend.service.TransactionService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private Transaction transaction;


    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(1000);
        transaction.setMerchant("Test Merchant");
        transaction.setUsername("testuser");
        transaction.setTransactionDate(LocalDateTime.now());
    }

    @Test
    @DisplayName("Should create a transaction")
    void shouldCreateTransaction() {
        // Mock the service to save and return a transaction
        when(transactionService.saveTransaction(transaction)).thenReturn(transaction);

        // Call the controller
        ResponseEntity<Transaction> response = transactionController.createTransaction(transaction);

        // Check the response code and body, and the call to the service
        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(transaction, response.getBody());
        verify(transactionService, times(1)).saveTransaction(transaction);
    }

    @Test
    @DisplayName("Should get a transaction by Id")
    void shouldGetTransactionById() {
        // Mock the service to find a transaction
        when(transactionService.getTransactionById(1L)).thenReturn(transaction);

        // Call the controller
        ResponseEntity<Transaction> response = transactionController.getTransactionById(1L);

        // Check the response with 200 code and body, Check the call to the service
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(transaction, response.getBody());
        verify(transactionService, times(1)).getTransactionById(1L);
    }

    @Test
    @DisplayName("Should get all transactions")
    void shouldGetAllTransactions() {
        // Mock the transaction service to get all transactions
        List<Transaction> transactions = Collections.singletonList(transaction);
        when(transactionService.getAllTransactions()).thenReturn(transactions);

        // Call the controller to get all transactions
        ResponseEntity<List<Transaction>> response = transactionController.getAllTransactions();

        // Check the response and the calling to the service
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(transactions, response.getBody());
        verify(transactionService, times(1)).getAllTransactions();
    }

    @Test
    @DisplayName("Should update a transaction")
    void shouldUpdateTransaction() {
        // Mock the service to update a transaction
        when(transactionService.updateTransaction(1L, transaction)).thenReturn(transaction);

        // call the controller to update a transaction
        ResponseEntity<Transaction> response = transactionController.updateTransaction(1L, transaction);

        // Check the response and the calling to the service
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(transaction, response.getBody());
        verify(transactionService, times(1)).updateTransaction(1L, transaction);
    }

    @Test
    void shouldDeleteTransaction() {
        // Mock the service to delete a transaction
        doNothing().when(transactionService).deleteTransaction(1L);

        // Call the controller to delete a transaction
        ResponseEntity<Void> response = transactionController.deleteTransaction(1L);

        // Check the response code and the calling to the service
        assertNotNull(response);
        assertEquals(204, response.getStatusCode().value());
        verify(transactionService, times(1)).deleteTransaction(1L);
    }

}