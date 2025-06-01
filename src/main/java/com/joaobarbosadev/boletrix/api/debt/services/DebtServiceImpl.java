package com.joaobarbosadev.boletrix.api.debt.services;

import com.joaobarbosadev.boletrix.api.debt.dtos.DebitInitialRequest;
import com.joaobarbosadev.boletrix.api.debt.dtos.DebtDTO;
import com.joaobarbosadev.boletrix.api.debt.dtos.DebtResponse;
import com.joaobarbosadev.boletrix.api.debt.mappers.DebtMapper;
import com.joaobarbosadev.boletrix.api.installment.services.InstallmentService;
import com.joaobarbosadev.boletrix.core.models.domain.Debt;
import com.joaobarbosadev.boletrix.core.repository.DebtRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    public DebtDTO findById(Long id) {
        return debtMapper.toDTO(debtCommonService.getDebt(id));
    }

    @Override
    public Page<DebtResponse> list(Long id, BigDecimal amount, LocalDate paymentDate, LocalDate invoiceDate, String status, Integer installmentNumber, int page, int size, String sortField, String sortOrder) {
        return null;
    }
}
