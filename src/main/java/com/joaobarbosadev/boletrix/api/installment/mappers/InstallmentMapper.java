package com.joaobarbosadev.boletrix.api.installment.mappers;

import com.joaobarbosadev.boletrix.core.models.domain.Installment;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentRequest;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentResponse;

public interface InstallmentMapper {

    Installment toInstallmentForRequest(InstallmentRequest request);
    InstallmentRequest toInstallmentRequest(Installment installment);
    InstallmentResponse toInstallmentResponse(Installment installment);
    Installment toInstallmentForResponse(InstallmentResponse response);
}
