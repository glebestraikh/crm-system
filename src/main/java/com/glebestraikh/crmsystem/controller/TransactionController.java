package com.glebestraikh.crmsystem.controller;

import com.glebestraikh.crmsystem.data.entity.Transaction;
import com.glebestraikh.crmsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Получить список всех транзакций
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // Получить информацию о конкретной транзакции
    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    // Получить все транзакции продавца
    @GetMapping("/seller/{sellerId}")
    public List<Transaction> getTransactionsBySellerId(@PathVariable Long sellerId) {
        return transactionService.getTransactionsBySellerId(sellerId);
    }

    // создать новую транзакцию
    @PostMapping("/seller/{sellerId}")
    public Transaction createTransaction(@PathVariable Long sellerId, @RequestBody Transaction transaction) {
        return transactionService.createTransaction(sellerId, transaction);
    }

    // Обновить существующую транзакцию
    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction updatedTransaction) {
        return transactionService.updateTransaction(id, updatedTransaction);
    }

    // Удалить транзакцию
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}

