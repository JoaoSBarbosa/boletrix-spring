package com.joaobarbosadev.boletrix.web.installment.services;

import com.joaobarbosadev.boletrix.core.repository.InstallmentRepository;
import com.joaobarbosadev.boletrix.web.installment.dtos.InstallmentInsert;
import com.joaobarbosadev.boletrix.web.installment.dtos.InstallmentRequest;
import com.joaobarbosadev.boletrix.web.installment.dtos.InstallmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class InstallmentServiceImpl implements InstallmentService {

    private final InstallmentRepository installmentRepository;

    public InstallmentServiceImpl(InstallmentRepository installmentRepository) {
        this.installmentRepository = installmentRepository;
    }

    @Override
    public InstallmentResponse insert(InstallmentInsert installmentInsert) {
        return null;
    }

    @Override
    public InstallmentResponse update(InstallmentRequest request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public String generateInstallment(BigDecimal amount, LocalDate initialDate) {
        return "";
    }

    @Override
    public Page<InstallmentResponse> list(Long id, BigDecimal amount, LocalDateTime paymentDate, Integer installmentNumber, int page, int size, String sortField, String sortOrder) {
        return null;
    }
}
