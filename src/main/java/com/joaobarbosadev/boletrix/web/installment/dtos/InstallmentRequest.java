package com.joaobarbosadev.boletrix.web.installment.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentRequest {
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String receiptUrl;
    private String receiptPath;
    private Integer installmentNumber;
}
