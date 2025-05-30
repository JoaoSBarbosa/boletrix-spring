package com.joaobarbosadev.boletrix.core.enums;

public enum PaymentStatus {

    PENDING("Pendente"),
    PAID("Pago"),
    WAITING("Aguardando");

    private final String description;


    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
