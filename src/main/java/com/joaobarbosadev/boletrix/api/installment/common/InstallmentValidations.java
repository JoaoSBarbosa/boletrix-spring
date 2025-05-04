package com.joaobarbosadev.boletrix.api.installment.common;

import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEmptyFieldException;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentInsert;

import java.math.BigDecimal;

public abstract class InstallmentValidations {


//    public static void validateInsert(InstallmentInsert installmentInsert) {
//
//        validateAmoutn(installmentInsert);
//    }


    public static void validateInsert(InstallmentInsert installmentInsert) {
        if (installmentInsert.getAmount() != null && installmentInsert.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CustomEmptyFieldException("Valor deve ser maior que zero");
        }
    }

}
