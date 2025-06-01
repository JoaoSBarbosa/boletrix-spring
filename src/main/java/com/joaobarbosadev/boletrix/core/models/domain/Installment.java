package com.joaobarbosadev.boletrix.core.models.domain;

import com.joaobarbosadev.boletrix.core.enums.PaymentStatus;
import com.joaobarbosadev.boletrix.core.models.abstracts.Auditable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "tb_parcelas")
public class Installment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "valor")
    private BigDecimal amount;

    @Column(name = "data_pagamento")
    private LocalDate paymentDate;

    @Column(name = "hora_pagamento")
    private String paymentTime;

    @Column(name = "data_parcela")
    private LocalDate installmentDate;
    @Column(name = "recibo_url")
    private String receiptUrl;
    @Column(name = "caminho_recibo")
    private String receiptPath;
    @Column(name = "numero_parcela")
    private Integer installmentNumber;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public Installment() {
    }

    public Installment(
            Long id,
            BigDecimal amount,
            LocalDate paymentDate,
            LocalDate installmentDate,
            String receiptUrl,
            String receiptPath,
            Integer installmentNumber,
            PaymentStatus status,
            String paymentTime
    ) {
        this.id = id;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.installmentDate = installmentDate;
        this.receiptUrl = receiptUrl;
        this.receiptPath = receiptPath;
        this.installmentNumber = installmentNumber;
        this.status = status;
        this.paymentTime = paymentTime;
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

    public LocalDate getInstallmentDate() {
        return installmentDate;
    }

    public void setInstallmentDate(LocalDate installmentDate) {
        this.installmentDate = installmentDate;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Installment that = (Installment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }



    @Override
    public String toString() {
        return "📄 Detalhes da Parcela(Entidade):\n" +
                "  • ID: " + id + "\n" +
                "  • Valor: " + amount + "\n" +
                "  • Data de Pagamento: " + paymentDate + "\n" +
                "  • Hora de Pagamento: '" + paymentTime + "'\n" +
                "  • URL do Comprovante: '" + receiptUrl + "'\n" +
                "  • Caminho do Comprovante: '" + receiptPath + "'\n" +
                "  • Número da Parcela: " + installmentNumber + "\n" +
                "  • Status: " + status + "\n";
    }
}
