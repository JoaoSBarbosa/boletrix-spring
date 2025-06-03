package com.joaobarbosadev.boletrix.api.installment.services;

import com.joaobarbosadev.boletrix.api.debt.services.DebtCommonService;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentStatus;
import com.joaobarbosadev.boletrix.core.enums.PaymentStatus;
import com.joaobarbosadev.boletrix.core.models.domain.Debt;
import com.joaobarbosadev.boletrix.core.models.domain.Installment;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEmptyFieldException;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEntityNotFoundException;
import com.joaobarbosadev.boletrix.core.repository.DebtRepository;
import com.joaobarbosadev.boletrix.core.repository.InstallmentRepository;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentInsert;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentRequest;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentResponse;
import com.joaobarbosadev.boletrix.api.installment.mappers.InstallmentMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.joaobarbosadev.boletrix.api.installment.common.InstallmentValidations.validateInsert;

@Service
public class InstallmentServiceImpl implements InstallmentService {

    private final InstallmentRepository installmentRepository;
    private final InstallmentMapper mapper;
    private final DebtRepository debtRepository;
    private final DebtCommonService debtCommonService;

    public InstallmentServiceImpl(InstallmentRepository installmentRepository, InstallmentMapper mapper, DebtRepository debtRepository, DebtCommonService debtCommonService) {
        this.installmentRepository = installmentRepository;
        this.mapper = mapper;
        this.debtRepository = debtRepository;
        this.debtCommonService = debtCommonService;
    }

    @Override
    public InstallmentResponse insert(InstallmentInsert installmentInsert) {
        validateInsert(installmentInsert);
        Installment installment = new Installment();
        buildEntity(installmentInsert, installment);
        installment = installmentRepository.save(installment);
        return mapper.toInstallmentResponse(installment);
    }

    @Override
    public InstallmentResponse update(InstallmentRequest request, Long id) {

        checkId(id);
        Installment actual = getInstallmentById(id);
        buildUpdate(request, actual);


        System.out.println("DADO REGISTRADO: " + actual);

        actual = installmentRepository.save(actual);
        return mapper.toInstallmentResponse(actual);
    }

    @Override
    public InstallmentResponse updateStatus(InstallmentStatus request, Long id) {
        checkId(id);
        Installment actual = getInstallmentById(id);
        buildUpdateStatus(request, actual);
        actual = installmentRepository.save(actual);
        return mapper.toInstallmentResponse(actual);
    }

    @Override
    public void deleteById(Long id) {
        checkId(id);
        try {
            Installment installment = installmentRepository.getReferenceById(id);
            installmentRepository.delete(installment);
        } catch (Exception e) {
            throw new CustomEntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteAll() {
        try {
            installmentRepository.deleteAll();
        } catch (Exception e) {
            throw new CustomEntityNotFoundException(e.getMessage());
        }
    }

    @Override
    public void generateInstallment(BigDecimal totalAmount, BigDecimal monthlyAmount, LocalDate initialDate, Debt debt) {

        try {
            List<Installment> installments = new ArrayList<>();
            BigDecimal remaining = totalAmount;
            int installmentNumber = 1;
            LocalDate installmentDate = initialDate;
            System.out.println("GERANDO PARCELAS");
            System.out.println("Total: " + totalAmount);
            System.out.println("Valor Mensal: " + monthlyAmount);

            while (remaining.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal currentAmount = remaining.compareTo(monthlyAmount) > 0 ? monthlyAmount : remaining;
                System.out.println("Parcela " + installmentNumber + ": " + currentAmount);
                Installment installment = new Installment();
                installment.setAmount(currentAmount);
                installment.setInstallmentDate(installmentDate);;
                installment.setStatus(PaymentStatus.PENDING);
                installment.setInstallmentNumber(installmentNumber);
                installment.setDebtId(debt.getId());
                installment.setReceiptUrl(null);
                installment.setReceiptPath(null);
                installments.add(installment);
                remaining = remaining.subtract(currentAmount);
                installmentDate = installmentDate.plusMonths(1);
                installmentNumber++;
            }
            debtRepository.save(debt);
            installmentRepository.saveAll(installments);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

//        return "Parcelamento gerado com sucesso: " + installments.size() + " parcelas";
    }


    @Override
    public Page<InstallmentResponse> list(
            Long id,
            BigDecimal amount,
            LocalDate paymentDate,
            LocalDate invoiceDate,
            String status,
            Integer installmentNumber,
            int page,
            int size,
            String sortField,
            String sortOrder) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        Specification<Installment> specification = Specification.where(null);

        System.out.println("ID______________________________: " + id);
        System.out.println("VALOR___________________________: " + amount);
        System.out.println("DATA DE PAGAMENTO_______________: " + paymentDate);
        System.out.println("DATA DE DOCUMENTO_______________: " + invoiceDate);
        System.out.println("STATUS__________________________: " + status);
        System.out.println("Nº______________________________: " + installmentNumber);

        if (invoiceDate != null) {specification = specification.and(
                (root, query, cb) -> cb.equal(root.get("invoiceDate"), invoiceDate)
        );}

        // Trata o status fora da lambda
        if (status != null && !status.isBlank()) {
            try {
                PaymentStatus statusEnum = PaymentStatus.valueOf(status);
                specification = specification.and((root, query, cb) ->
                        cb.equal(root.get("status"), statusEnum));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Status inválido: " + status);
            }
        }

        if (id != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("id"), id));
        }

        if (amount != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("amount"), amount));
        }

        if (paymentDate != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("paymentDate"), paymentDate));
        }

        if (installmentNumber != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("installmentNumber"), installmentNumber));
        }

        Page<Installment> installments = installmentRepository.findAll(specification, pageable);
        return installments.map(mapper::toInstallmentResponse);
    }


    private void buildUpdate(InstallmentRequest request, Installment installment) {
        if (request.getAmount() != null) {
            installment.setAmount(request.getAmount());
        }
        if (request.getPaymentDate() != null) {
            installment.setPaymentDate(request.getPaymentDate());
        }
        if(request.getPaymentTime() != null) {
            installment.setPaymentTime(request.getPaymentTime());
        }
        if (request.getInstallmentNumber() != null) {
            installment.setInstallmentNumber(request.getInstallmentNumber());
        }
        if (request.getReceiptPath() != null) {
            installment.setReceiptPath(request.getReceiptPath());
        }
        if (request.getReceiptUrl() != null) {
            installment.setReceiptUrl(request.getReceiptUrl());
        }


    }

    private void buildUpdateStatus(InstallmentStatus request, Installment installment) {
        if (request.getPaymentDate() != null) {
            installment.setPaymentDate(request.getPaymentDate());
        }
        if(request.getPaymentTime() != null) {
            installment.setPaymentTime(request.getPaymentTime());
        }
        if (request.getStatus() != null && request.getStatus() != installment.getStatus()) {
            handleStatusChange(installment, request.getStatus());
        }
    }

    private void buildEntity(InstallmentInsert source, Installment entity) {

        entity.setAmount(source.getAmount());
        if (source.getInstallmentNumber() != null) {
            entity.setInstallmentNumber(source.getInstallmentNumber());
        }
    }

    private void handleStatusChange(Installment installment, PaymentStatus newStatus) {
        PaymentStatus oldStatus = installment.getStatus();
        Debt debt = debtCommonService.getDebt(installment.getDebtId());
        BigDecimal amount = installment.getAmount();

        installment.setStatus(newStatus);

        if (newStatus == PaymentStatus.PAID && oldStatus != PaymentStatus.PAID) {
            // Só altera valores da dívida se estiver marcando como pago agora
            if (installment.getPaymentDate() == null) {
                installment.setPaymentDate(LocalDate.now());
            }

            BigDecimal newTotalPaid = debt.getTotalPaid().add(amount);
            debt.setTotalPaid(newTotalPaid);
            debt.setRemainingAmount(debt.getTotalAmount().subtract(newTotalPaid));

        } else if (oldStatus == PaymentStatus.PAID && newStatus != PaymentStatus.PAID) {
            // Se estava como pago e foi revertido para outro status → desfaz
            BigDecimal newTotalPaid = debt.getTotalPaid().subtract(amount);
            debt.setTotalPaid(newTotalPaid);
            debt.setRemainingAmount(debt.getTotalAmount().subtract(newTotalPaid));
        }
        debtCommonService.saveDebt(debt);
    }


    private Installment getInstallmentById(Long id) {
        return installmentRepository.findById(id).orElseThrow(() -> new CustomEntityNotFoundException("Não foi encontrado um registro com o ID: " + id));
    }
    private void checkId(Long id) {
        if (id == null) {
            throw new CustomEmptyFieldException("O ID obrigatorio para ser efetuado a exclusão do registro");
        }
    }
}
