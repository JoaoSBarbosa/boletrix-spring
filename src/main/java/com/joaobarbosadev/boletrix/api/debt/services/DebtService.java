package com.joaobarbosadev.boletrix.api.debt.services;

import com.joaobarbosadev.boletrix.api.debt.dtos.DebitInitialRequest;
import com.joaobarbosadev.boletrix.api.debt.dtos.DebtDTO;
import com.joaobarbosadev.boletrix.api.debt.dtos.DebtResponse;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface DebtService {

    public String generateDebt(DebitInitialRequest request);

    void deleteDebt(Long id);
    void deleteAllDebts();
    List<DebtDTO> findAll();
    DebtDTO findById(Long id);
    Page<DebtResponse> list(
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
