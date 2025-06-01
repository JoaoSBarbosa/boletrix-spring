package com.joaobarbosadev.boletrix.api.debt.dtos;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentResponse;
import com.joaobarbosadev.boletrix.core.models.domain.Debt;
import com.joaobarbosadev.boletrix.core.models.domain.Installment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DebtDTO {
    private long id;
    private BigDecimal totalAmount;
    private BigDecimal totalPaid;
    private BigDecimal remainingAmount;
    private Integer totalInstallments;

    public DebtDTO() {}

    public DebtDTO(
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

    public DebtDTO(Debt debt) {
        this.id = debt.getId();
        this.totalAmount = debt.getTotalAmount();
        this.totalPaid = debt.getTotalPaid();
        this.remainingAmount = debt.getRemainingAmount();
        this.totalInstallments = debt.getTotalInstallments();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getTotalInstallments() {
        return totalInstallments;
    }

    public void setTotalInstallments(Integer totalInstallments) {
        this.totalInstallments = totalInstallments;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(BigDecimal totalPaid) {
        this.totalPaid = totalPaid;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
