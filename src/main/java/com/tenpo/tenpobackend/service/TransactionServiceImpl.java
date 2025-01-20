package com.tenpo.tenpobackend.service;

import com.tenpo.tenpobackend.exception.InvalidTransactionException;
import com.tenpo.tenpobackend.exception.TransactionLimitExceededException;
import com.tenpo.tenpobackend.exception.TransactionNotFoundException;
import com.tenpo.tenpobackend.model.Transaction;
import com.tenpo.tenpobackend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    public static final String TRANSACTION_NOT_FOUND_WITH_ID = "Transacción no encontrada con id: ";
    public static final String USER_CANNOT_HAVE_MORE_THAN_100_TRANSACTIONS = "El usuario no puede tener más de 100 transacciones.";
    public static final String TRANSACTION_AMOUNT_CANNOT_BE_NEGATIVE = "El monto de la transacción no puede ser negativo";
    public static final String TRANSACTION_DATE_CANNOT_BE_IN_THE_FUTURE = "La fecha de la transacción no puede ser en el futuro.";
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        validateTransaction(transaction);
        checkTransactionLimit(transaction.getUsername());
        return transactionRepository.save(transaction);
    }

    private void checkTransactionLimit(String username) {
        List<Transaction> userTransactions = transactionRepository.findByUsername(username);
        if (userTransactions.size() >= 100) {
            throw new TransactionLimitExceededException(USER_CANNOT_HAVE_MORE_THAN_100_TRANSACTIONS);
        }
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction.getAmount() < 0) {
            throw new InvalidTransactionException(TRANSACTION_AMOUNT_CANNOT_BE_NEGATIVE);
        }
        if (transaction.getTransactionDate().isAfter(LocalDateTime.now())) {
            throw new InvalidTransactionException(TRANSACTION_DATE_CANNOT_BE_IN_THE_FUTURE);
        }
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(TRANSACTION_NOT_FOUND_WITH_ID + id));
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction transaction) {
        validateTransaction(transaction);
        Transaction existingTransaction = getTransactionById(id);
        existingTransaction.setAmount(transaction.getAmount());
        existingTransaction.setMerchant(transaction.getMerchant());
        existingTransaction.setUsername(transaction.getUsername());
        existingTransaction.setTransactionDate(transaction.getTransactionDate());
        return transactionRepository.save(existingTransaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        Transaction transaction = getTransactionById(id);
        transactionRepository.delete(transaction);
    }
}
