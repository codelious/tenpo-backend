package com.tenpo.tenpobackend.service;

import com.tenpo.tenpobackend.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction saveTransaction(Transaction transaction);
    Transaction getTransactionById(Long id);
    List<Transaction> getAllTransactions();
    Transaction updateTransaction(Long id, Transaction transaction);
    void deleteTransaction(Long id);
}
