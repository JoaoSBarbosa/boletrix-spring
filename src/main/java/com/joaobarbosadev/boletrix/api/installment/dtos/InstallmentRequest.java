package com.joaobarbosadev.boletrix.api.installment.dtos;


import com.joaobarbosadev.boletrix.core.enums.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InstallmentRequest {
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentTime;
    private String receiptUrl;
    private String receiptPath;
    private Integer installmentNumber;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public InstallmentRequest() {}

    public InstallmentRequest(BigDecimal amount, LocalDate paymentDate,String paymentTime, String receiptUrl, String receiptPath, Integer installmentNumber, PaymentStatus status) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.receiptUrl = receiptUrl;
        this.receiptPath = receiptPath;
        this.installmentNumber = installmentNumber;
        this.status = status;
        this.paymentTime = paymentTime;
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

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "📄 Detalhes da Parcela:\n" +
                "  • Valor: " + amount + "\n" +
                "  • Data de Pagamento: " + paymentDate + "\n" +
                "  • Hora de Pagamento: '" + paymentTime + "'\n" +
                "  • URL do Comprovante: '" + receiptUrl + "'\n" +
                "  • Caminho do Comprovante: '" + receiptPath + "'\n" +
                "  • Número da Parcela: " + installmentNumber + "\n" +
                "  • Status: " + status + "\n";
    }

}
