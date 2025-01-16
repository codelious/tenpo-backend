package com.tenpo.tenpobackend.repository;

import com.tenpo.tenpobackend.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionRepositoryTest {

    @Mock
    private TransactionRepository transactionRepository;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        // prepare data before each test
        transaction = new Transaction();
        transaction.setAmount(1000);
        transaction.setMerchant("Test Merchant");
        transaction.setUsername("testuser");
        transaction.setTransactionDate(LocalDateTime.now());
    }

    @Test
    @DisplayName("Should save a transaction")
    void shouldSaveTransaction() {
        // Mock the repository to return a transaction when save
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // Call the method that uses the repository
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Check if the repository was called ok
        verify(transactionRepository, times(1)).save(transaction);

        // Check if transaction was saved ok
        assertNotNull(savedTransaction);
        assertEquals(transaction.getAmount(), savedTransaction.getAmount());
    }

    @Test
    @DisplayName("Should find a transaction by Id")
    void shouldFindTransactionById() {
        // Mock the repository to return a transaction
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        // Call the repository
        Optional<Transaction> foundTransaction = transactionRepository.findById(1L);

        // Check if the repository was called ok
        verify(transactionRepository, times(1)).findById(1L);

        // Check the transaction was found
        assertTrue(foundTransaction.isPresent());
        assertEquals(transaction.getId(), foundTransaction.get().getId());
    }

    @Test
    @DisplayName("Should return null when transaction not found")
    void shouldReturnNullWhenTransactionNotFound() {
        // mock the repository to return empty
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the repository
        Optional<Transaction> foundTransaction = transactionRepository.findById(1L);

        // Check if the repository was called ok
        verify(transactionRepository, times(1)).findById(1L);

        // Check the transaction was not found
        assertFalse(foundTransaction.isPresent());
    }

    @Test
    @DisplayName("Should delete a transaction")
    void shouldDeleteTransaction() {
        // Mock the repository to do nothing when delete
        doNothing().when(transactionRepository).delete(transaction);

        // Call the repository
        transactionRepository.delete(transaction);

        // Check the repository was called ok
        verify(transactionRepository, times(1)).delete(transaction);
    }

    @Test
    @DisplayName("Should update a transaction")
    void shouldUpdateTransaction() {
        // Mock the repository to return an updated transaction
        transaction.setAmount(2000);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // call the repository
        Transaction updatedTransaction = transactionRepository.save(transaction);

        // Check the repository was called ok
        verify(transactionRepository, times(1)).save(transaction);

        // Check the transaction was updated
        assertEquals(2000, updatedTransaction.getAmount());

    }
}