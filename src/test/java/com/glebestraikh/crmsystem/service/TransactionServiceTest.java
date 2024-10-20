package com.glebestraikh.crmsystem.service;

import com.glebestraikh.crmsystem.data.entity.Seller;
import com.glebestraikh.crmsystem.data.entity.Transaction;
import com.glebestraikh.crmsystem.data.repository.SellerRepository;
import com.glebestraikh.crmsystem.data.repository.TransactionRepository;
import com.glebestraikh.crmsystem.util.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.glebestraikh.crmsystem.data.entity.PaymentType.CARD;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Seller seller1;
    private Transaction transaction1;
    private Transaction transaction2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seller1 = new Seller(1L, "John Doe", "john.doe@example.com", LocalDateTime.of(2023, 1, 1, 10, 0));
        transaction1 = new Transaction(1L, seller1, 100, CARD, LocalDateTime.of(2023, 1, 5, 10, 0, 0));
        transaction2 = new Transaction(2L, seller1, 2000, CARD, LocalDateTime.of(2023, 1, 10, 12, 0, 0));
    }

    @Test
    void getAllTransactions() {
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> result = transactionService.getAllTransactions();

        assertEquals(2, result.size());
        assertEquals(transaction1.getId(), result.get(0).getId());
        assertEquals(transaction2.getId(), result.get(1).getId());

        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void getTransactionById() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction1));

        Transaction result = transactionService.getTransactionById(1L);

        assertEquals(transaction1.getId(), result.getId());
        assertEquals(transaction1.getSeller().getId(), result.getSeller().getId());
        assertEquals(transaction1.getAmount(), result.getAmount());
        assertEquals(transaction1.getPaymentType(), result.getPaymentType());
        assertEquals(transaction1.getTransactionDate(), result.getTransactionDate());

        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void getTransactionById_NotFound() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> transactionService.getTransactionById(1L));

        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void createTransaction() {
        when(sellerRepository.existsById(1L)).thenReturn(true);
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller1));
        when(transactionRepository.save(transaction1)).thenReturn(transaction1);

        Transaction result = transactionService.createTransaction(1L, transaction1);

        assertEquals(transaction1.getId(), result.getId());
        assertEquals(transaction1.getSeller().getId(), result.getSeller().getId());
        assertEquals(transaction1.getAmount(), result.getAmount());
        assertEquals(transaction1.getPaymentType(), result.getPaymentType());
        assertEquals(transaction1.getTransactionDate(), result.getTransactionDate());

        verify(sellerRepository, times(1)).existsById(1L);
        verify(sellerRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).save(transaction1);
    }

    @Test
    void createTransaction_SellerNotFound() {
        when(sellerRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> transactionService.createTransaction(1L, transaction1));

        verify(sellerRepository, times(1)).existsById(1L);
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void getTransactionsBySellerId() {
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(sellerRepository.existsById(1L)).thenReturn(true);
        when(transactionRepository.findBySellerId(1L)).thenReturn(transactions);

        List<Transaction> result = transactionService.getTransactionsBySellerId(1L);

        assertEquals(2, result.size());
        assertEquals(transaction1.getId(), result.get(0).getId());
        assertEquals(transaction2.getId(), result.get(1).getId());

        verify(sellerRepository, times(1)).existsById(1L);
        verify(transactionRepository, times(1)).findBySellerId(1L);
    }

    @Test
    void getTransactionsBySellerId_SellerNotFound() {
        when(sellerRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> transactionService.getTransactionsBySellerId(1L));

        verify(sellerRepository, times(1)).existsById(1L);
        verify(transactionRepository, never()).findBySellerId(any());
    }

    @Test
    void deleteTransaction() {
        when(transactionRepository.existsById(1L)).thenReturn(true);

        transactionService.deleteTransaction(1L);

        verify(transactionRepository, times(1)).existsById(1L);
        verify(transactionRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTransaction_NotFound() {
        when(transactionRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> transactionService.deleteTransaction(1L));

        verify(transactionRepository, times(1)).existsById(1L);
        verify(transactionRepository, never()).deleteById(any());
    }

    @Test
    void updateTransaction() {
        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setAmount(3000);
        updatedTransaction.setTransactionDate(LocalDateTime.of(2023, 1, 15, 10, 0, 0));
        updatedTransaction.setPaymentType(CARD);

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction1));
        when(transactionRepository.save(transaction1)).thenReturn(transaction1);

        Transaction result = transactionService.updateTransaction(1L, updatedTransaction);

        assertEquals(3000, result.getAmount());
        assertEquals(LocalDateTime.of(2023, 1, 15, 10, 0, 0), result.getTransactionDate());
        assertEquals(CARD, result.getPaymentType());

        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).save(transaction1);
    }

    @Test
    void updateTransaction_NotFound() {
        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setAmount(3000);

        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> transactionService.updateTransaction(1L, updatedTransaction));

        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, never()).save(any());
    }
}