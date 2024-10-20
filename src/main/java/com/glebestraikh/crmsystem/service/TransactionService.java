package com.glebestraikh.crmsystem.service;

import com.glebestraikh.crmsystem.data.entity.Seller;
import com.glebestraikh.crmsystem.data.entity.Transaction;
import com.glebestraikh.crmsystem.data.repository.SellerRepository;
import com.glebestraikh.crmsystem.data.repository.TransactionRepository;
import com.glebestraikh.crmsystem.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;


    // Получить список всех транзакций
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Получить информацию о конкретной транзакции
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new NotFoundException("Transaction with ID " + id + " not found."));
    }

    // Создать новую транзакцию
    public Transaction createTransaction(Long sellerId, Transaction transaction) {
        // Проверяем наличие продавца
        if (!sellerRepository.existsById(sellerId)) {
            throw new NotFoundException("Seller with ID " + sellerId + " not found.");
        }

        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("Seller with ID " + sellerId + " not found."));

        transaction.setSeller(seller);
        return transactionRepository.save(transaction);
    }

    // Получить все транзакции по ID продавца
    public List<Transaction> getTransactionsBySellerId(Long sellerId) {
        if (!sellerRepository.existsById(sellerId)) {
            throw new NotFoundException("Seller with ID " + sellerId + " not found.");
        }

        return transactionRepository.findBySellerId(sellerId);
    }

    // Удалить транзакцию
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new NotFoundException("Transaction with ID " + id + " not found.");
        }
        transactionRepository.deleteById(id);
    }

    // Обновить существующую транзакцию
    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction existingTransaction = transactionRepository.findById(id).orElseThrow(() -> new NotFoundException("Transaction with ID " + id + " not found."));

        if (updatedTransaction.getAmount() != null) {
            existingTransaction.setAmount(updatedTransaction.getAmount());
        }
        if (updatedTransaction.getTransactionDate() != null) {
            existingTransaction.setTransactionDate(updatedTransaction.getTransactionDate());
        }
        if (updatedTransaction.getPaymentType() != null) {
            existingTransaction.setPaymentType(updatedTransaction.getPaymentType());
        }
        return transactionRepository.save(existingTransaction);
    }
}
