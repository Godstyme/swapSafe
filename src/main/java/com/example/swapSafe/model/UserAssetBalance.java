package com.example.swapSafe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAssetBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String network;

    @Column(precision = 19, scale = 4)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference

//    @JsonIgnore
    private User user;

    private LocalDateTime lastUpdated;


}
