package com.joaobarbosadev.boletrix.web.installment.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentInsert {
    private BigDecimal amount;
    private Integer installmentNumber;
}
