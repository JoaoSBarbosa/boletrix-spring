package com.joaobarbosadev.boletrix.api.debt.services;

import com.joaobarbosadev.boletrix.api.debt.dtos.DebitInitialRequest;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEmptyFieldException;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEntityNotFoundException;
import com.joaobarbosadev.boletrix.core.models.domain.Debt;
import com.joaobarbosadev.boletrix.core.repository.DebtRepository;
import org.springframework.stereotype.Component;

@Component
public class DebtCommonServiceImpl implements DebtCommonService {

    private final DebtRepository debtRepository;

    public DebtCommonServiceImpl(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    @Override
    public Debt getDebt(Long id) {
        if (id == null) throw new CustomEmptyFieldException("ID do débito está ausente");
        return debtRepository.findById(id).orElseThrow(() -> new CustomEntityNotFoundException("Não foi localizado registro de débito com o ID: " + id));

    }

    @Override
    public void saveDebt(Debt debt) {
        debtRepository.save(debt);
    }
}
