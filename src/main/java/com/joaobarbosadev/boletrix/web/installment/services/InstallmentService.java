package com.joaobarbosadev.boletrix.web.installment.services;

import com.joaobarbosadev.boletrix.core.domain.Installment;
import com.joaobarbosadev.boletrix.web.installment.dtos.InstallmentInsert;
import com.joaobarbosadev.boletrix.web.installment.dtos.InstallmentRequest;
import com.joaobarbosadev.boletrix.web.installment.dtos.InstallmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface InstallmentService {

    InstallmentResponse insert(InstallmentInsert installmentInsert);
    InstallmentResponse update(InstallmentRequest request, Long id);
    void deleteById(Long id);
    String generateInstallment(BigDecimal amount, LocalDate initialDate);
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
