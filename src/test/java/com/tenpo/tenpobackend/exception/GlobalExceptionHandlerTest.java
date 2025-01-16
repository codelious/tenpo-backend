package com.tenpo.tenpobackend.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void shouldHandleTransactionNotFoundException() {
        // Given
        TransactionNotFoundException exception = new TransactionNotFoundException("Transaction not found");

        // When
        ResponseEntity<String> response = globalExceptionHandler.handleTransactionNotFoundException(exception);

        // Then
        assertEquals(404, response.getStatusCode().value());
        assertEquals("Transaction not found", response.getBody());
    }

    @Test
    void shouldHandleGeneralException() {
        // Given
        Exception exception = new Exception("Internal error");

        // When
        ResponseEntity<String> response = globalExceptionHandler.handleGeneralException(exception);

        // Then
        assertEquals(500, response.getStatusCode().value());
        assertEquals("Internal error", response.getBody());
    }

    @Test
    void shouldHandleInvalidTransactionException() {
        // Given
        InvalidTransactionException exception = new InvalidTransactionException("Invalid transaction details");

        // When
        ResponseEntity<String> response = globalExceptionHandler.handleInvalidTransactionException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid transaction details", response.getBody());
    }

    @Test
    void shouldHandleTransactionLimitExceededException() {
        // Given
        TransactionLimitExceededException exception = new TransactionLimitExceededException("Transaction limit exceeded");

        // When
        ResponseEntity<String> response = globalExceptionHandler.handleTransactionLimitExceededException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Transaction limit exceeded", response.getBody());
    }
}