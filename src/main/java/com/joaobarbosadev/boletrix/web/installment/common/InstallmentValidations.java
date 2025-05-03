package com.joaobarbosadev.boletrix.web.installment.common;

import com.joaobarbosadev.boletrix.web.installment.dtos.InstallmentInsert;

import java.math.BigDecimal;

public abstract class InstallmentValidations {


    public static void validateInsert(InstallmentInsert installmentInsert) {

    }


    private void validateAmoutn(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {

        }
    }
}
