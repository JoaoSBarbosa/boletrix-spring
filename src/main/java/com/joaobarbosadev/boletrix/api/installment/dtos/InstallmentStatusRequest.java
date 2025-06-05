package com.joaobarbosadev.boletrix.api.installment.dtos;

public class InstallmentStatusRequest {
    private String paymentDate;
    private String paymentTime;
    private String status;

    public InstallmentStatusRequest() {

    }

    public InstallmentStatusRequest(String paymentDate, String paymentTime, String status) {
        this.paymentDate = paymentDate;
        this.paymentTime = paymentTime;
        this.status = status;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }
}
