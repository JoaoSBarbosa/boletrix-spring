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
import java.util.List;

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

    @GetMapping("/list")
    public ResponseEntity<List<DebtDTO>> getAllDebt() {
        List<DebtDTO> debtDTO = debtService.findAll();
        return ResponseEntity.ok(debtDTO);
    }
}
