package com.joaobarbosadev.boletrix.api.debt.mappers;

import com.joaobarbosadev.boletrix.api.debt.dtos.DebtDTO;
import com.joaobarbosadev.boletrix.core.models.domain.Debt;

public interface DebtMapper {

    DebtDTO toDTO(Debt debt);
    Debt toEntity(DebtDTO debtDTO);
}
