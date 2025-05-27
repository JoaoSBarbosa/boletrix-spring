package com.joaobarbosadev.boletrix.api.installment.services;

import com.joaobarbosadev.boletrix.core.models.domain.Installment;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEmptyFieldException;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEntityNotFoundException;
import com.joaobarbosadev.boletrix.core.repository.InstallmentRepository;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentInsert;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentRequest;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentResponse;
import com.joaobarbosadev.boletrix.api.installment.mappers.InstallmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.joaobarbosadev.boletrix.api.installment.common.InstallmentValidations.validateInsert;

@Service
public class InstallmentServiceImpl implements InstallmentService {

    private final InstallmentRepository installmentRepository;
    private final InstallmentMapper mapper;

    public InstallmentServiceImpl(InstallmentRepository installmentRepository, InstallmentMapper mapper) {
        this.installmentRepository = installmentRepository;
        this.mapper = mapper;
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
        if(id == null) {
            throw new CustomEmptyFieldException("O ID é obrigatorio para edição de registro");
        }
        Installment actual = installmentRepository.findById(id).orElseThrow(()-> new CustomEntityNotFoundException("Não foi encontrado um registro com o ID: " + id));

        buildUpdate(request, actual);
        actual = installmentRepository.save(actual);
        return mapper.toInstallmentResponse(actual);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public String generateInstallment(BigDecimal totalAmount, BigDecimal monthlyAmount, LocalDate initialDate) {
        List<Installment> installments = new ArrayList<>();
        BigDecimal remaining = totalAmount;
        int installmentNumber = 1;
        LocalDate installmentDate = initialDate;

        while (remaining.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal currentAmount;

            if (remaining.compareTo(monthlyAmount) > 0) {
                currentAmount = monthlyAmount;
            } else {
                currentAmount = remaining;
            }

            Installment installment = new Installment();
            installment.setAmount(currentAmount);
            installment.setInstallmentDate(installmentDate);
            installment.setPaymentDate(null); // ainda não pago
            installment.setReceiptPath(null); // se desejar preencher depois
            installment.setReceiptUrl(null);
            installment.setInstallmentNumber(installmentNumber);

            installments.add(installment);

            remaining = remaining.subtract(currentAmount);
            installmentDate = installmentDate.plusMonths(1);
            installmentNumber++;
        }

        // Aqui você persiste as parcelas no banco
        installmentRepository.saveAll(installments);

        return "Parcelamento gerado com sucesso: " + installments.size() + " parcelas";
    }


    @Override
    public Page<InstallmentResponse> list(
            Long id,
            BigDecimal amount,
            LocalDateTime paymentDate,
            Integer installmentNumber,
            int page,
            int size,
            String sortField,
            String sortOrder) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        Specification<Installment> specification = Specification.where(null);

        if(id != null) specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id));
        if(amount != null) specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("amount"), amount));
        if(paymentDate != null) specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("paymentDate"), paymentDate));
        if(installmentNumber != null) specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("installmentNumber"), installmentNumber));

        Page<Installment> installments = installmentRepository.findAll(specification, pageable);

        return installments.map(mapper::toInstallmentResponse);
    }

    private void buildUpdate(InstallmentRequest request, Installment installment) {
        if(request.getAmount() != null) {
            installment.setAmount(request.getAmount());
        }
        if(request.getPaymentDate() != null) {
            installment.setPaymentDate(request.getPaymentDate());
        }
        if(request.getInstallmentNumber() != null) {
            installment.setInstallmentNumber(request.getInstallmentNumber());
        }
        if(request.getReceiptPath() != null) {
            installment.setReceiptPath(request.getReceiptPath());
        }
        if(request.getReceiptUrl() != null) {
            installment.setReceiptUrl(request.getReceiptUrl());
        }
    }
    private void buildEntity(InstallmentInsert source, Installment entity) {

        entity.setAmount(source.getAmount());
        if( source.getInstallmentNumber() != null ){
            entity.setInstallmentNumber(source.getInstallmentNumber());
        }
    }
}
