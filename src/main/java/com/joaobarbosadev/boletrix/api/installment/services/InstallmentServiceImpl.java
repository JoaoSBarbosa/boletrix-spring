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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public String generateInstallment(BigDecimal amount, LocalDate initialDate) {
        return "";
    }

    @Override
    public Page<InstallmentResponse> list(Long id, BigDecimal amount, LocalDateTime paymentDate, Integer installmentNumber, int page, int size, String sortField, String sortOrder) {
        return null;
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
