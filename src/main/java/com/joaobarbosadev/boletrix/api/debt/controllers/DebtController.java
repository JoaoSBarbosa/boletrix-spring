package com.joaobarbosadev.boletrix.api.debt.controllers;

import com.joaobarbosadev.boletrix.api.debt.dtos.DebitInitialRequest;
import com.joaobarbosadev.boletrix.api.debt.dtos.DebtDTO;
import com.joaobarbosadev.boletrix.api.debt.dtos.DebtResponse;
import com.joaobarbosadev.boletrix.api.debt.services.DebtService;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentResponse;
import com.joaobarbosadev.boletrix.core.models.domain.Debt;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/debts")
public class DebtController {

    private final DebtService debtService;

    public DebtController(DebtService debtService) {
        this.debtService = debtService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> generateDebt(@RequestBody DebitInitialRequest request) {
        String result = debtService.generateDebt(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DebtDTO> getDebt(@PathVariable Long id) {

        DebtDTO debtDTO = debtService.findById(id);
        return ResponseEntity.ok(debtDTO);
    }
    @GetMapping
    public ResponseEntity<Page<DebtResponse>> getAllInstallments(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam(required = false) LocalDate paymentDate,
            @RequestParam(required = false) LocalDate invoiceDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer installmentNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Page<DebtResponse> installments = debtService.list(
                id, amount, paymentDate, invoiceDate, status, installmentNumber, page, size, sortField, sortOrder
        );
        return ResponseEntity.ok(installments);

    }
}
