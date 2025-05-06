package com.example.swapSafe.model;

import jakarta.persistence.*;
//import lombok.Getter;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Getter
@Entity
@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    public enum Role {
        CUSTOMER, ADMIN
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 64)
    private String email;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles;

    @Setter
    @Column(nullable = false, columnDefinition = "NUMERIC(38,2) DEFAULT 0.00")
    private BigDecimal balance = BigDecimal.ZERO;


    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected User() {
    }

    private User(Builder builder) {
        this.name = Objects.requireNonNull(builder.name,  "email");
        this.email =  Objects.requireNonNull(builder.email,  "email");
        this.password = Objects.requireNonNull(builder.password, "password");
        this.balance = builder.balance != null ? builder.balance : BigDecimal.ZERO;
        this.roles = Collections.unmodifiableSet(
                builder.roles.isEmpty() ? EnumSet.of(Role.CUSTOMER)
                        : EnumSet.copyOf(builder.roles));
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }


    @PreUpdate
    private void touch() {
        this.updatedAt = Instant.now();
    }

    public static class Builder {
        private String name;
        private String email;
        private String password;
        private Set<Role> roles = new HashSet<>();
        private BigDecimal balance = BigDecimal.ZERO;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder roles(Set<Role> roles) {
            this.roles.addAll(roles);
            return this;
        }

        public Builder balance(BigDecimal balance) {
            this.balance = balance != null ? balance : BigDecimal.ZERO; // Default to ZERO if balance is null
            return this;
        }

        public User build() {
            return new User(this);
        }
    }


    public static Builder builder() {
        return new Builder();
    }
}