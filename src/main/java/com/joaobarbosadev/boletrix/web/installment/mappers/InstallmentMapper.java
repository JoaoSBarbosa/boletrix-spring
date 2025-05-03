package com.joaobarbosadev.boletrix.web.installment.mappers;

import com.joaobarbosadev.boletrix.core.domain.Installment;
import com.joaobarbosadev.boletrix.web.installment.dtos.InstallmentRequest;
import com.joaobarbosadev.boletrix.web.installment.dtos.InstallmentResponse;

public interface InstallmentMapper {

    Installment toInstallmentForRequest(InstallmentRequest request);
    InstallmentRequest toInstallmentRequest(Installment installment);
    InstallmentResponse toInstallmentResponse(Installment installment);
    Installment toInstallmentForResponse(InstallmentResponse response);
}
