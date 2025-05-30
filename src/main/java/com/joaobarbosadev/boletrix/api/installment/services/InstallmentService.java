package com.joaobarbosadev.boletrix.api.installment.services;

import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentInsert;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentRequest;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface InstallmentService {

    InstallmentResponse insert(InstallmentInsert installmentInsert);
    InstallmentResponse update(InstallmentRequest request, Long id);
    void deleteById(Long id);
    void deleteAll();
    String generateInstallment(BigDecimal amount, BigDecimal monthlyAmount, LocalDate initialDate);
    Page<InstallmentResponse> list(
            Long id,
            BigDecimal amount,
            LocalDateTime paymentDate,
            Integer installmentNumber,
            int page,
            int size,
            String sortField,
            String sortOrder
            );
}
