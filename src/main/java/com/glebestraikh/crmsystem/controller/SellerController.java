package com.glebestraikh.crmsystem.controller;

import com.glebestraikh.crmsystem.data.entity.Seller;
import com.glebestraikh.crmsystem.data.model.ProductivePeriod;
import com.glebestraikh.crmsystem.service.SellerService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/seller")
public class SellerController {
    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    // Получить всех продавцов
    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerService.getAllSellers();
    } // выводит даже транзакции

    // Инфо о конкретном продавце
    @GetMapping("/{id}")
    public Seller getSellerById(@PathVariable Long id) {
        return sellerService.getSellerById(id);
    }

    // Создать нового продавца
    @PostMapping
    public Seller createSeller(@RequestBody Seller seller) {
        return sellerService.createSeller(seller);
    }

    // Обновить информацию о продавце
    @PutMapping("/{id}")
    public Seller updateSeller(@PathVariable Long id, @RequestBody Seller updatedSeller) {
        return sellerService.updateSeller(id, updatedSeller);
    }

    // Удалить продавца
    @DeleteMapping("/{id}")
    public void deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
    }

    // Получить самого продуктивного продавца
    @GetMapping("/transactions/most-productive")
    public Seller getMostProductiveSeller(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return sellerService.getMostProductiveSeller(startDate, endDate);
    }

    // Получить список продавцов с суммой меньше указанной за период
    @GetMapping("/transactions/less-than")
    public List<Seller> getSellersWithLowTransactionSum(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate, @RequestParam Integer threshold) {
        return sellerService.getSellersWithLowTransactionSum(startDate, endDate, threshold);
    }

    @GetMapping("/{sellerId}/productive-period")
    public ProductivePeriod getProductivePeriod(@PathVariable Long sellerId) {
        return sellerService.getProductivePeriod(sellerId);
    }
}
