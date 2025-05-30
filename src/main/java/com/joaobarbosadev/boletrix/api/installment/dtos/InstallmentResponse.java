package com.joaobarbosadev.boletrix.api.installment.dtos;
import com.joaobarbosadev.boletrix.core.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class InstallmentResponse {
    private Long id;
    private BigDecimal amount;
    private LocalDate installmentDate;
    private LocalDateTime paymentDate;
    private String receiptUrl;
    private String receiptPath;
    private Integer installmentNumber;
    private PaymentStatus status;

    public InstallmentResponse() {}

    public InstallmentResponse(
            Long id,
            BigDecimal amount,
            LocalDate installmentDate,
            LocalDateTime paymentDate,
            String receiptUrl,
            String receiptPath,
            Integer installmentNumber,
            PaymentStatus status
    ) {
        this.id = id;
        this.amount = amount;
        this.installmentDate = installmentDate;
        this.paymentDate = paymentDate;
        this.receiptUrl = receiptUrl;
        this.receiptPath = receiptPath;
        this.installmentNumber = installmentNumber;
        this.status = status;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public String getReceiptPath() {
        return receiptPath;
    }

    public void setReceiptPath(String receiptPath) {
        this.receiptPath = receiptPath;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDate getInstallmentDate() {
        return installmentDate;
    }

    public void setInstallmentDate(LocalDate installmentDate) {
        this.installmentDate = installmentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
