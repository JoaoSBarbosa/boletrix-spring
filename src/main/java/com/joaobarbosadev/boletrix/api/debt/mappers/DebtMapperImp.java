package com.joaobarbosadev.boletrix.api.debt.mappers;

import com.joaobarbosadev.boletrix.api.debt.dtos.DebtDTO;
import com.joaobarbosadev.boletrix.core.models.domain.Debt;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public class DebtMapperImp implements DebtMapper {
    @Override
    public DebtDTO toDTO(Debt debt) {
        DebtDTO debtDTO = new DebtDTO();
        debtDTO.setId(debt.getId());
        debtDTO.setTotalAmount(debt.getTotalAmount());
        debtDTO.setRemainingAmount(debt.getRemainingAmount());
        debtDTO.setTotalPaid(debt.getTotalPaid());
        debtDTO.setTotalAmount(debt.getTotalAmount());
        return debtDTO;
    }

    @Override
    public Debt toEntity(DebtDTO debtDTO) {
        Debt debt = new Debt();
        debt.setId(debtDTO.getId());
        debt.setTotalAmount(debtDTO.getTotalAmount());
        debt.setRemainingAmount(debtDTO.getRemainingAmount());
        debt.setTotalPaid(debtDTO.getTotalPaid());
        debt.setTotalAmount(debtDTO.getTotalAmount());

        return debt;
    }
}
