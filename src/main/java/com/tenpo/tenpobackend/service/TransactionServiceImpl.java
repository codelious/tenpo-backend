package com.tenpo.tenpobackend.service;

import com.tenpo.tenpobackend.exception.TransactionNotFoundException;
import com.tenpo.tenpobackend.model.Transaction;
import com.tenpo.tenpobackend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    public static final String TRANSACTION_NOT_FOUND_WITH_ID = "Transaction not found with id: ";
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
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
