package com.glebestraikh.crmsystem.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false)
    private Seller seller;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    public Transaction(Long id, Seller seller, Integer amount, PaymentType paymentType, LocalDateTime transactionDate) {
        this.id = id;
        this.seller = seller;
        this.amount = amount;
        this.paymentType = paymentType;
        this.transactionDate = transactionDate;
    }
}
