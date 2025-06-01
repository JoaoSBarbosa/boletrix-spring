package com.joaobarbosadev.boletrix.api.installment.dtos;
import com.joaobarbosadev.boletrix.core.enums.PaymentStatus;
import com.joaobarbosadev.boletrix.core.models.domain.Installment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class InstallmentResponse {
    private Long id;
    private BigDecimal amount;
    private LocalDate installmentDate;
    private LocalDate paymentDate;
    private String paymentTime;
    private String receiptUrl;
    private String receiptPath;
    private Integer installmentNumber;
    private PaymentStatus status;

    public InstallmentResponse() {}

    public InstallmentResponse(
            Long id,
            BigDecimal amount,
            LocalDate installmentDate,
            LocalDate paymentDate,
            String receiptUrl,
            String receiptPath,
            Integer installmentNumber,
            PaymentStatus status,
            String paymentTime
    ) {
        this.id = id;
        this.amount = amount;
        this.installmentDate = installmentDate;
        this.paymentDate = paymentDate;
        this.receiptUrl = receiptUrl;
        this.receiptPath = receiptPath;
        this.installmentNumber = installmentNumber;
        this.status = status;
        this.paymentTime = paymentTime;

    }

    public InstallmentResponse(Installment installment) {
        id = installment.getId();
        amount = installment.getAmount();
        installmentDate = installment.getInstallmentDate();
        paymentDate = installment.getPaymentDate();
        paymentTime = installment.getPaymentTime();
        receiptUrl = installment.getReceiptUrl();
        receiptPath = installment.getReceiptPath();
        installmentNumber = installment.getInstallmentNumber();
        status = installment.getStatus();

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

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDate getInstallmentDate() {
        return installmentDate;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
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
