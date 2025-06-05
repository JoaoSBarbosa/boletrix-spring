package com.joaobarbosadev.boletrix.api.installment.helper;

import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentStatus;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentStatusRequest;
import com.joaobarbosadev.boletrix.core.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

public class InstallmentHelpers {


    public static InstallmentStatus convertToDomain(InstallmentStatusRequest dto) {
        InstallmentStatus domain = new InstallmentStatus();

        if (dto.getPaymentDate() != null && !dto.getPaymentDate().isBlank()) {
            domain.setPaymentDate(LocalDate.parse(dto.getPaymentDate())); // ISO: yyyy-MM-dd
        }

        domain.setPaymentTime(dto.getPaymentTime());

        try {
            domain.setStatus(PaymentStatus.valueOf(dto.getStatus().toUpperCase()));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Status inválido: " + dto.getStatus());
        }

        return domain;
    }

}
