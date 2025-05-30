package com.joaobarbosadev.boletrix.api.installment.controllers;

import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentInsert;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentRequest;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentResponse;
import com.joaobarbosadev.boletrix.api.installment.services.InstallmentService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/installments")
public class InstallmentController {

    private final InstallmentService service;

    public InstallmentController(InstallmentService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstallmentResponse> createInstallment(InstallmentInsert installmentInsert) {
        InstallmentResponse response = service.insert(installmentInsert);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstallmentResponse> updateInstallment(@PathVariable Long id, InstallmentRequest request) {
        InstallmentResponse response = service.update(request, id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstallment(@PathVariable Long id) {
        System.out.println("CHEGOU NA API PARA DELETAR");
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllInstallments() {
        service.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/generated/{totalAmount}/{monthlyAmount}")
    public ResponseEntity<String> generateInstallment(
            @PathVariable BigDecimal totalAmount,
            @PathVariable BigDecimal monthlyAmount,
            @RequestParam LocalDate initialDate
    ) {
        return ResponseEntity.ok(service.generateInstallment(totalAmount, monthlyAmount, initialDate));
    }

    @GetMapping
    public ResponseEntity<Page<InstallmentResponse>> getAllInstallments(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam(required = false) LocalDateTime paymentDate,
            @RequestParam(required = false) Integer installmentNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "installmentNumber") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Page<InstallmentResponse> installments = service.list(
                id,
                amount, paymentDate, installmentNumber, page, size, sortField, sortOrder
        );
        return ResponseEntity.ok(installments);

    }
}

