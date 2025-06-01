package com.joaobarbosadev.boletrix.api.debt.services;

import com.joaobarbosadev.boletrix.api.debt.dtos.DebitInitialRequest;
import com.joaobarbosadev.boletrix.core.models.domain.Debt;

public interface DebtCommonService {

    Debt getDebt(Long id);
    void saveDebt(Debt debt);
}
