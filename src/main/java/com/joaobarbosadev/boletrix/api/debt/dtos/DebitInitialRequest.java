package com.joaobarbosadev.boletrix.api.debt.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DebitInitialRequest {

    private BigDecimal totalAmount;
    private BigDecimal monthlyAmount;
    private LocalDate initialDate;

    public DebitInitialRequest() {
    }

    public DebitInitialRequest(BigDecimal totalAmount, LocalDate initialDate, BigDecimal monthlyAmount) {
        this.totalAmount = totalAmount;
        this.initialDate = initialDate;
        this.monthlyAmount = monthlyAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public BigDecimal getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(BigDecimal monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }


    @Override
    public String toString() {
        return "📄 Inico da Requisção - Criação de Dividas:\n" +
                "  • Valor Total: " + totalAmount + "\n" +
                "  • Valor ao mês: " + monthlyAmount + "\n" +
                "  • Data de Inicio: '" + initialDate + "'\n";
    }
}
