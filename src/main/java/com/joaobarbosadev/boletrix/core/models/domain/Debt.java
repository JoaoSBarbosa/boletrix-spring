package com.joaobarbosadev.boletrix.core.models.domain;
import com.joaobarbosadev.boletrix.core.models.abstracts.Auditable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_divida")
public class Debt extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "valor_total")
    private BigDecimal totalAmount;

    @Column(name = "total_pago")
    private BigDecimal totalPaid;

    @Column(name = "saldo_restante")
    private BigDecimal remainingAmount;

    @Column(name = "quantidade_parcelas")
    private Integer totalInstallments;

    @OneToMany(mappedBy = "debt", cascade = CascadeType.ALL)
    private List<Installment> installments = new ArrayList<>();

    public Debt() {}
    public Debt(long id, Integer totalInstallments, BigDecimal remainingAmount, BigDecimal totalPaid, BigDecimal totalAmount) {
        this.id = id;
        this.totalInstallments = totalInstallments;
        this.remainingAmount = remainingAmount;
        this.totalPaid = totalPaid;
        this.totalAmount = totalAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Installment> getInstallments() {
        return installments;
    }

    public Integer getTotalInstallments() {
        return totalInstallments;
    }

    public void setTotalInstallments(Integer totalInstallments) {
        this.totalInstallments = totalInstallments;
    }

    public void setInstallments(List<Installment> installments) {
        this.installments = installments;
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
