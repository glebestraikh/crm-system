package com.glebestraikh.crmsystem.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "seller")
@Getter
@Setter
public class Seller {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact_info", nullable = false)
    private String contactInfo;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "seller", orphanRemoval = true)
    private List<Transaction> transactions;

    public Seller(Long id, String name, String contactInfo, LocalDateTime registrationDate) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.registrationDate = registrationDate;
    }
}
