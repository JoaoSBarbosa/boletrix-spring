package com.joaobarbosadev.boletrix.api.installment.services;

import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentInsert;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentRequest;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentResponse;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentStatus;
import com.joaobarbosadev.boletrix.core.models.domain.Debt;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface InstallmentService {

    InstallmentResponse insert(InstallmentInsert installmentInsert);
    InstallmentResponse update(InstallmentRequest request, Long id);
    InstallmentResponse updateStatus(InstallmentStatus request, Long id);
    void deleteById(Long id);
    void deleteAll();
    void generateInstallment(BigDecimal amount, BigDecimal monthlyAmount, LocalDate initialDate, Debt debt);
    Page<InstallmentResponse> list(
            Long id,
            BigDecimal amount,
            LocalDate paymentDate,
            LocalDate invoiceDate,
            String status,
            Integer installmentNumber,
            int page,
            int size,
            String sortField,
            String sortOrder
            );
}
