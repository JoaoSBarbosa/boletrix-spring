package com.joaobarbosadev.boletrix.api.debt.dtos;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentResponse;
import com.joaobarbosadev.boletrix.core.models.domain.Debt;
import com.joaobarbosadev.boletrix.core.models.domain.Installment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DebtResponse {
    private long id;
    private BigDecimal totalAmount;
    private BigDecimal totalPaid;
    private BigDecimal remainingAmount;
    private Integer totalInstallments;
    private List<InstallmentResponse> installments = new ArrayList<>();

    public DebtResponse() {}

    public DebtResponse(
            long id,
            BigDecimal totalAmount,
            BigDecimal totalPaid,
            BigDecimal remainingAmount,
            Integer totalInstallments
    ) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.totalPaid = totalPaid;
        this.remainingAmount = remainingAmount;
        this.totalInstallments = totalInstallments;
    }

    public DebtResponse(Debt debt) {
        this.id = debt.getId();
        this.totalAmount = debt.getTotalAmount();
        this.totalPaid = debt.getTotalPaid();
        this.remainingAmount = debt.getRemainingAmount();
        this.totalInstallments = debt.getTotalInstallments();

    }

    public DebtResponse(Debt debt, List<Installment> installments) {
        this(debt);
        installments.forEach(row -> this.installments.add(new InstallmentResponse(row)));
    }
}
