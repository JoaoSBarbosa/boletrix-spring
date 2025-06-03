package com.joaobarbosadev.boletrix.api.installment.dtos;
import com.joaobarbosadev.boletrix.core.enums.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDate;

public class InstallmentStatus {
    private LocalDate paymentDate;
    private String paymentTime;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public InstallmentStatus() {}

    public InstallmentStatus(
            LocalDate paymentDate,
            String paymentTime,
            PaymentStatus status
    ) {
        this.paymentDate = paymentDate;
        this.status = status;
        this.paymentTime = paymentTime;
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
        return "📄 Detalhes Atualização Status:\n" +
                "  • Data de Pagamento: " + paymentDate + "\n" +
                "  • Hora de Pagamento: '" + paymentTime + "'\n" +
                "  • Status: " + status + "\n";
    }

}
