package com.joaobarbosadev.boletrix.web.installment.services;

import com.joaobarbosadev.boletrix.core.domain.Installment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface InstallmentService {

    Page<Installment> list(
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
