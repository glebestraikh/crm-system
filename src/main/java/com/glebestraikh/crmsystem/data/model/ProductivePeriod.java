package com.glebestraikh.crmsystem.data.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductivePeriod {
    private Long sellerId;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long numOfTransactions;
}
