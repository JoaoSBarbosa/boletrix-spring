package com.joaobarbosadev.boletrix.core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_parcelas")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Installment {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "valor")
    private BigDecimal amount;
    @Column(name = "data_pagamento")
    private LocalDateTime paymentDate;
    @Column(name = "reciboUrl")
    private String receiptUrl;
    @Column(name = "caminhoRecibo")
    private String receiptPath;
    @Column(name = "numero_parcela")
    private Integer installmentNumber;
}
