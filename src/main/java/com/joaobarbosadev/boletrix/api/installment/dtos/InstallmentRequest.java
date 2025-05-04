package com.joaobarbosadev.boletrix.api.installment.dtos;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InstallmentRequest {
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String receiptUrl;
    private String receiptPath;
    private Integer installmentNumber;

    public InstallmentRequest() {}

    public InstallmentRequest(BigDecimal amount, LocalDateTime paymentDate, String receiptUrl, String receiptPath, Integer installmentNumber) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.receiptUrl = receiptUrl;
        this.receiptPath = receiptPath;
        this.installmentNumber = installmentNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
}
