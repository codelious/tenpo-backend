package com.tenpo.tenpobackend.service;

import com.tenpo.tenpobackend.exception.InvalidTransactionException;
import com.tenpo.tenpobackend.exception.TransactionLimitExceededException;
import com.tenpo.tenpobackend.exception.TransactionNotFoundException;
import com.tenpo.tenpobackend.model.Transaction;
import com.tenpo.tenpobackend.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

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
    @DisplayName("Should save a transaction")
    void shouldSaveTransaction() {
        // Mock the repository to return a transaction
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // call the service where the repository is used
        Transaction savedTransaction = transactionService.saveTransaction(transaction);

        // Check the transaction was saved ok
        assertNotNull(savedTransaction);
        assertEquals(transaction.getAmount(), savedTransaction.getAmount());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    @DisplayName("Should find a transaction by id")
    void shouldFindTransactionById() {
        // Mock the repository to return a transaction by id
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        // call the service where the repository is used
        Transaction foundTransaction = transactionService.getTransactionById(1L);

        // Check the transaction was found ok
        assertNotNull(foundTransaction);
        assertEquals(transaction.getId(), foundTransaction.getId());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should find a transaction by id")
    void shouldGetAllTransactions() {
        // Arrange
        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setAmount(2000);
        transaction2.setMerchant("Merchant 2");
        transaction2.setUsername("user2");
        transaction2.setTransactionDate(LocalDateTime.now());

        List<Transaction> transactions = Arrays.asList(transaction, transaction2);
        when(transactionRepository.findAll()).thenReturn(transactions);

        // Act
        List<Transaction> result = transactionService.getAllTransactions();

        // Assert
        assertEquals(2, result.size());
        assertEquals(transaction, result.get(0));
        assertEquals(transaction2, result.get(1));
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should throw an exception when transaction not found")
    void shouldThrowExceptionWhenTransactionNotFound() {
        // Mock the repository to return empty
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        //  Call the service where and exception is returned
        Exception exception = assertThrows(TransactionNotFoundException.class, () -> transactionService.getTransactionById(1L));

        // Check the exception message
        assertEquals("Transacción no encontrada con id: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Should update a transaction")
    void shouldUpdateTransaction() {
        // Mock the repository to return an updated transaction
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // Call the service to update a transaction
        transaction.setAmount(2000);
        Transaction updatedTransaction = transactionService.updateTransaction(1L, transaction);

        // Check the updated transaction
        assertEquals(2000, updatedTransaction.getAmount());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    @DisplayName("Should delete a transaction")
    void shouldDeleteTransaction() {
        // Mock the repository to delete a transaction
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        doNothing().when(transactionRepository).delete(transaction);

        // Call the service to delete a transaction
        transactionService.deleteTransaction(1L);

        // Check the delete method was used
        verify(transactionRepository, times(1)).delete(transaction);
    }

    @Test
    @DisplayName("Should throw exception if transaction amount is negative")
    void shouldThrowExceptionIfTransactionAmountIsNegative() {
        transaction.setAmount(-100);
        assertThrows(InvalidTransactionException.class, () -> transactionService.saveTransaction(transaction));
    }

    @Test
    @DisplayName("Should throw exception if transaction date is in the future")
    void shouldThrowExceptionIfTransactionDateIsInFuture() {
        transaction.setTransactionDate(LocalDateTime.now().plusDays(1));
        assertThrows(InvalidTransactionException.class, () -> transactionService.saveTransaction(transaction));
    }

    @Test
    @DisplayName("Should throw exception if user exceeds transaction limit")
    void shouldThrowExceptionIfUserExceedsTransactionLimit() {
        when(transactionRepository.findByUsername(transaction.getUsername())).thenReturn(Collections.nCopies(100, transaction));
        assertThrows(TransactionLimitExceededException.class, () -> transactionService.saveTransaction(transaction));
    }
}