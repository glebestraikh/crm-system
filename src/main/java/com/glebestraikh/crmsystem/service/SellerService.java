package com.glebestraikh.crmsystem.service;


import com.glebestraikh.crmsystem.data.entity.Seller;
import com.glebestraikh.crmsystem.data.entity.Transaction;
import com.glebestraikh.crmsystem.data.model.ProductivePeriod;
import com.glebestraikh.crmsystem.data.repository.SellerRepository;
import com.glebestraikh.crmsystem.data.repository.TransactionRepository;
import com.glebestraikh.crmsystem.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;

    private final TransactionRepository transactionRepository;


    // Получить всех продавцов
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    // Инфо о конкретном продавце
    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id).orElseThrow(() -> new NotFoundException("Seller with ID " + id + " not found."));
    }

    // Создать нового продавца
    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    // Обновить инфо о продавце
    public Seller updateSeller(Long id, Seller updatedSeller) {
        Seller existingSeller = sellerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Seller with ID " + id + " not found.")); // Обработка ошибки

        if (updatedSeller.getName() != null) {
            existingSeller.setName(updatedSeller.getName());
        }

        if (updatedSeller.getContactInfo() != null) {
            existingSeller.setContactInfo(updatedSeller.getContactInfo());
        }

        if (updatedSeller.getRegistrationDate() != null) {
            existingSeller.setRegistrationDate(updatedSeller.getRegistrationDate());
        }

        return sellerRepository.save(existingSeller);
    }

    // Удалить продавца
    public void deleteSeller(Long id) {
        if (!sellerRepository.existsById(id)) {
            throw new NotFoundException("Seller with ID " + id + " not found.");
        }
        sellerRepository.deleteById(id);
    }

    // Получить самого продуктивного продавца
    public Seller getMostProductiveSeller(LocalDateTime startDate, LocalDateTime endDate) {
        List<Seller> sellers = sellerRepository.findMostProductiveSeller(startDate, endDate);
        if (sellers.isEmpty()) {
            throw new NotFoundException("There are no sellers for the specified period.");
        }
        return sellers.getFirst();
    }

    // Получить список продавцов с суммой транзакций меньше указанной
    public List<Seller> getSellersWithLowTransactionSum(LocalDateTime startDate, LocalDateTime endDate, Integer threshold) {
        List<Seller> sellers = sellerRepository.findSellersWithTransactionSumLessThan(startDate, endDate, threshold);

        if (sellers.isEmpty()) {
            throw new NotFoundException("There are no sellers with transaction amounts below the specified threshold.");
        }

        return sellers;
    }

    public ProductivePeriod getProductivePeriod(Long sellerId) {
        ProductivePeriod productivePeriod = new ProductivePeriod();
        productivePeriod.setSellerId(sellerId);

        List<LocalDateTime> dates = transactionRepository.findBySellerIdOrderByTransactionDateAsc(sellerId)
                .stream()
                .map(Transaction::getTransactionDate)
                .toList();

        if (dates.isEmpty()) {
            return productivePeriod;
        }

        LocalDateTime bestStart = dates.getFirst();
        LocalDateTime bestEnd = dates.getLast();

        long maxTransactions = 0;

        for (int i = 0; i < dates.size(); i++) {
            for (int j = i; j < dates.size(); j++) {
                long numDays = ChronoUnit.DAYS.between(dates.get(i), dates.get(j)) + 1;
                long numTransactions = j - i + 1;
                double density = (double) numTransactions / numDays;

                if (density > (double) maxTransactions / numDays) {
                    maxTransactions = numTransactions;
                    bestStart = dates.get(i);
                    bestEnd = dates.get(j);
                }
            }
        }

        return new ProductivePeriod(sellerId, bestStart, bestEnd, maxTransactions);
    }
}
