package com.joaobarbosadev.boletrix.api.installment.dtos;

import com.joaobarbosadev.boletrix.core.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InstallmentFile {
    private Integer installmentNumber;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentTime;



    public InstallmentFile() {}


    public InstallmentFile(Integer installmentNumber, BigDecimal amount, LocalDate paymentDate, String paymentTime) {
        this.installmentNumber = installmentNumber;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentTime = paymentTime;
    }


    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "📄 Detalhes Atualização Status:\n" +
                "  • Data de Pagamento: " + paymentDate + "\n" +
                "  • Hora de Pagamento: '" + paymentTime + "'\n" +
                "  • Valor de Pagamento: '" + amount + "'\n" +
                "  • Nº parcela: " + installmentNumber + "\n";
    }

}
