package com.joaobarbosadev.boletrix.api.debt.services;

import com.joaobarbosadev.boletrix.api.debt.dtos.DebitInitialRequest;
import com.joaobarbosadev.boletrix.api.debt.dtos.DebtDTO;
import com.joaobarbosadev.boletrix.api.debt.dtos.DebtResponse;
import com.joaobarbosadev.boletrix.api.debt.mappers.DebtMapper;
import com.joaobarbosadev.boletrix.api.installment.services.InstallmentService;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEmptyFieldException;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEntityNotFoundException;
import com.joaobarbosadev.boletrix.core.models.domain.Debt;
import com.joaobarbosadev.boletrix.core.repository.DebtRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DebtServiceImpl implements DebtService {
    private final InstallmentService installmentService;
    private final DebtRepository debtRepository;
    private final DebtCommonService debtCommonService;
    private final DebtMapper debtMapper;

    public DebtServiceImpl(InstallmentService installmentService, DebtRepository debtRepository, DebtCommonService debtCommonService, DebtMapper debtMapper) {
        this.installmentService = installmentService;
        this.debtRepository = debtRepository;
        this.debtCommonService = debtCommonService;
        this.debtMapper = debtMapper;
    }

    @Override
    @Transactional
    public String generateDebt(DebitInitialRequest request) {

        Debt debt = new Debt();
        debt.setTotalAmount(request.getTotalAmount());
        debt.setTotalPaid(BigDecimal.ZERO);
        debt.setRemainingAmount(request.getTotalAmount());
        debt.setTotalInstallments(0);
        debt = debtRepository.save(debt);

        installmentService.generateInstallment(request.getTotalAmount(), request.getMonthlyAmount(), request.getInitialDate(), debt);
        return "Dívida gerado com sucesso!";
    }

    @Override
    public void deleteDebt(Long id) {
        if (id == null) throw new CustomEmptyFieldException("O ID deve ser informado!");
        try {
            Debt debt = debtRepository.getReferenceById(id);
            debtRepository.delete(debt);
        } catch (EntityNotFoundException e) {
            throw new CustomEntityNotFoundException("Não foi encontrado registro de debito com o ID: " + id);
        }
    }

    @Override
    public void deleteAllDebts() {
        debtRepository.deleteAll();
    }

    @Override
    public DebtDTO findById(Long id) {
        return debtMapper.toDTO(debtCommonService.getDebt(id));
    }


    @Override
    @Transactional(readOnly = true)
    public List<DebtDTO> findAll() {
        return debtRepository.findAll().stream().map(debtMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Page<DebtResponse> list(Long id, BigDecimal amount, LocalDate paymentDate, LocalDate invoiceDate, String status, Integer installmentNumber, int page, int size, String sortField, String sortOrder) {
        return null;
    }
}
