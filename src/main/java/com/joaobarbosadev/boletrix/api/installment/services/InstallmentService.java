package com.joaobarbosadev.boletrix.api.installment.services;

import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentInsert;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentRequest;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentResponse;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentStatus;
import com.joaobarbosadev.boletrix.core.models.domain.Debt;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface InstallmentService {

    InstallmentResponse deleteInstallmentReceipt(Long id);
    InstallmentResponse insert(InstallmentInsert installmentInsert);
    InstallmentResponse update(InstallmentRequest request, Long id);
    InstallmentResponse updateStatus(InstallmentStatus request, Long id, MultipartFile file);
    void deleteById(Long id);
    void deleteAll(Long debtId);
    InstallmentResponse uploadReceipt(MultipartFile file, Long id);
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
