package com.tenpo.tenpobackend.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
}