package com.joaobarbosadev.boletrix.api.installment.mappers;

import com.joaobarbosadev.boletrix.core.models.domain.Installment;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentRequest;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentResponse;
import org.springframework.stereotype.Component;

@Component
public class InstallmentMapperImpl implements InstallmentMapper {
    @Override
    public Installment toInstallmentForRequest(InstallmentRequest request) {
        Installment installment = new Installment();
        installment.setAmount(request.getAmount());
        installment.setInstallmentNumber(request.getInstallmentNumber());
        installment.setPaymentDate( request.getPaymentDate());
        installment.setReceiptUrl(request.getReceiptUrl());
        installment.setReceiptPath(request.getReceiptPath());
        return installment;
    }

    @Override
    public InstallmentRequest toInstallmentRequest(Installment installment) {
        InstallmentRequest request = new InstallmentRequest();
        request.setAmount(installment.getAmount());
        request.setPaymentDate( installment.getPaymentDate());
        request.setReceiptUrl(installment.getReceiptUrl());
        request.setReceiptPath(installment.getReceiptPath());
        request.setInstallmentNumber(installment.getInstallmentNumber());
        return request;
    }

    @Override
    public InstallmentResponse toInstallmentResponse(Installment installment) {
        InstallmentResponse response = new InstallmentResponse();
        response.setId(installment.getId());
        response.setAmount(installment.getAmount());
        response.setInstallmentDate(installment.getInstallmentDate());
        response.setPaymentDate(installment.getPaymentDate());
        response.setReceiptUrl(installment.getReceiptUrl());
        response.setReceiptPath(installment.getReceiptPath());
        response.setInstallmentNumber(installment.getInstallmentNumber());
        response.setStatus(installment.getStatus());
        return response;
    }

    @Override
    public Installment toInstallmentForResponse(InstallmentResponse response) {
        Installment entity = new Installment();
        entity.setId(response.getId());
        entity.setAmount(response.getAmount());
        entity.setInstallmentDate(response.getInstallmentDate());
        entity.setPaymentDate(response.getPaymentDate());
        entity.setReceiptUrl(response.getReceiptUrl());
        entity.setReceiptPath(response.getReceiptPath());
        entity.setInstallmentNumber(response.getInstallmentNumber());
        entity.setStatus(response.getStatus());
        return entity;
    }
}
