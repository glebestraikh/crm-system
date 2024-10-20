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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.glebestraikh.crmsystem.data.entity.PaymentType.CARD;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SellerServiceTest {

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private SellerService sellerService;

    private Seller seller1;
    private Seller seller2;
    private List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seller1 = new Seller(1L, "John Doe", "john.doe@example.com", LocalDateTime.of(2023, 1, 1, 10, 0));
        seller2 = new Seller(2L, "Jane Smith", "jane.smith@example.com", LocalDateTime.of(2023, 2, 1, 11, 0));

        transactions = new ArrayList<>();
        transactions.add(new Transaction(1L, seller1, 100, CARD, LocalDateTime.of(2023, 1, 5, 10, 0, 0)));
        transactions.add(new Transaction(2L, seller1, 2000, CARD, LocalDateTime.of(2023, 1, 10, 12, 0, 0)));

        transactions.add(new Transaction(3L, seller2, 90, CARD, LocalDateTime.of(2023, 2, 5, 10, 0, 0)));
        transactions.add(new Transaction(4L, seller2, 120, CARD, LocalDateTime.of(2023, 2, 10, 12, 0, 0)));
    }

    @Test
    void getAllSellers() {
        List<Seller> sellers = List.of(seller1, seller2);
        when(sellerRepository.findAll()).thenReturn(sellers);

        List<Seller> result = sellerService.getAllSellers();

        assertEquals(2, result.size());

        assertEquals(sellers.get(0).getId(), result.get(0).getId());
        assertEquals(sellers.get(1).getId(), result.get(1).getId());

        assertEquals(sellers.get(0).getName(), result.get(0).getName());
        assertEquals(sellers.get(1).getName(), result.get(1).getName());

        assertEquals(sellers.get(0).getContactInfo(), result.get(0).getContactInfo());
        assertEquals(sellers.get(1).getContactInfo(), result.get(1).getContactInfo());

        assertEquals(sellers.get(0).getRegistrationDate(), result.get(0).getRegistrationDate());
        assertEquals(sellers.get(1).getRegistrationDate(), result.get(1).getRegistrationDate());

        verify(sellerRepository, times(1)).findAll();
    }

    @Test
    void getSellerById() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller1));

        Seller result = sellerService.getSellerById(1L);

        assertEquals(seller1.getId(), result.getId());
        assertEquals(seller1.getName(), result.getName());
        assertEquals(seller1.getContactInfo(), result.getContactInfo());
        assertEquals(seller1.getRegistrationDate(), result.getRegistrationDate());

        verify(sellerRepository, times(1)).findById(1L);
    }

    @Test
    void getSellerById_NotFound() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sellerService.getSellerById(1L));

        verify(sellerRepository, times(1)).findById(1L);
    }

    @Test
    void createSeller() {
        when(sellerRepository.save(seller1)).thenReturn(seller1);

        Seller result = sellerService.createSeller(seller1);

        assertEquals(seller1.getId(), result.getId());
        assertEquals(seller1.getName(), result.getName());
        assertEquals(seller1.getContactInfo(), result.getContactInfo());
        assertEquals(seller1.getRegistrationDate(), result.getRegistrationDate());

        verify(sellerRepository, times(1)).save(seller1);
    }

    @Test
    void updateSeller() {
        Seller updatedSeller = new Seller();
        updatedSeller.setName("John Smith");

        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller1));
        when(sellerRepository.save(seller1)).thenReturn(seller1);

        Seller result = sellerService.updateSeller(1L, updatedSeller);

        assertEquals("John Smith", result.getName());
        assertEquals(seller1.getId(), result.getId());
        assertEquals(seller1.getContactInfo(), result.getContactInfo());
        assertEquals(seller1.getRegistrationDate(), result.getRegistrationDate());

        verify(sellerRepository, times(1)).findById(1L);
        verify(sellerRepository, times(1)).save(seller1);
    }

    @Test
    void updateSeller_NotFound() {
        Seller updatedSeller = new Seller();
        updatedSeller.setName("John Smith");

        when(sellerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sellerService.updateSeller(1L, updatedSeller));
        verify(sellerRepository, times(1)).findById(1L);
    }

    @Test
    void deleteSeller() {
        when(sellerRepository.existsById(1L)).thenReturn(true);

        sellerService.deleteSeller(1L);

        verify(sellerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteSeller_NotFound() {
        when(sellerRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> sellerService.deleteSeller(1L));
        verify(sellerRepository, times(1)).existsById(1L);
    }


    @Test
    void getMostProductiveSeller() {
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);

        List<Seller> mostProductiveSellers = List.of(seller1);
        when(sellerRepository.findMostProductiveSeller(startDate, endDate)).thenReturn(mostProductiveSellers);

        Seller result = sellerService.getMostProductiveSeller(startDate, endDate);

        assertEquals(seller1.getId(), result.getId());
        assertEquals(seller1.getName(), result.getName());
        assertEquals(seller1.getContactInfo(), result.getContactInfo());
        assertEquals(seller1.getRegistrationDate(), result.getRegistrationDate());

        verify(sellerRepository, times(1)).findMostProductiveSeller(startDate, endDate);
    }

    @Test
    void getMostProductiveSeller_NotFound() {
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);

        when(sellerRepository.findMostProductiveSeller(startDate, endDate)).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> sellerService.getMostProductiveSeller(startDate, endDate));

        verify(sellerRepository, times(1)).findMostProductiveSeller(startDate, endDate);
    }

    @Test
    void getSellersWithLowTransactionSum() {
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);
        Integer threshold = 200;

        List<Seller> sellersWithLowTransactionSum = List.of(seller2);
        when(sellerRepository.findSellersWithTransactionSumLessThan(startDate, endDate, threshold)).thenReturn(sellersWithLowTransactionSum);

        List<Seller> result = sellerService.getSellersWithLowTransactionSum(startDate, endDate, threshold);

        assertEquals(1, result.size());
        assertEquals(seller2.getId(), result.getFirst().getId());
        assertEquals(seller2.getName(), result.getFirst().getName());
        assertEquals(seller2.getContactInfo(), result.getFirst().getContactInfo());
        assertEquals(seller2.getRegistrationDate(), result.getFirst().getRegistrationDate());

        verify(sellerRepository, times(1)).findSellersWithTransactionSumLessThan(startDate, endDate, threshold);
    }

    @Test
    void getSellersWithLowTransactionSum_NotFound() {
        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);
        Integer threshold = 50;

        when(sellerRepository.findSellersWithTransactionSumLessThan(startDate, endDate, threshold)).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> sellerService.getSellersWithLowTransactionSum(startDate, endDate, threshold));

        verify(sellerRepository, times(1)).findSellersWithTransactionSumLessThan(startDate, endDate, threshold);
    }
}