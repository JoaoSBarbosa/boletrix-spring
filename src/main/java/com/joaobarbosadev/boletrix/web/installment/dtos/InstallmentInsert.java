package com.joaobarbosadev.boletrix.web.installment.dtos;
import java.math.BigDecimal;

public class InstallmentInsert {
    private BigDecimal amount;
    private Integer installmentNumber;

    public InstallmentInsert() {}
    public InstallmentInsert(BigDecimal amount, Integer installmentNumber) {
        this.amount = amount;
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
}
