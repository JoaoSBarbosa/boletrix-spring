package com.joaobarbosadev.boletrix.api.installment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaobarbosadev.boletrix.api.debt.dtos.DebitInitialRequest;
import com.joaobarbosadev.boletrix.api.installment.dtos.*;
import com.joaobarbosadev.boletrix.api.installment.helper.InstallmentHelpers;
import com.joaobarbosadev.boletrix.api.installment.services.InstallmentService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.joaobarbosadev.boletrix.core.utils.Util.converter;

@RestController
@RequestMapping("/installments")
public class InstallmentController {

    private final InstallmentService service;

    public InstallmentController(InstallmentService service ) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstallmentResponse> createInstallment(@RequestBody InstallmentInsert installmentInsert) {
        InstallmentResponse response = service.insert(installmentInsert);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstallmentResponse> updateInstallment(
            @PathVariable Long id,
            @RequestBody InstallmentRequest request

    ) {

        System.out.println("API REQUEST: " + request);
        InstallmentResponse response = service.update(request, id);
        return ResponseEntity.ok(response);
    }

//    @PatchMapping("/{id}/status")
//    @PreAuthorize("hasRole('ADMIN')")
//    @PatchMapping(value = "/{id}/status", consumes = {"multipart/form-data"})
//    public ResponseEntity<InstallmentResponse> updateInstallmentStatus(
//            @PathVariable Long id,
//            @RequestPart("data") InstallmentStatus request,
//            @RequestPart(value = "file", required = false) MultipartFile file
//    ) {
//        InstallmentResponse response = service.updateStatus(request, id, file);
//        return ResponseEntity.ok(response);
//    }

    @PatchMapping(value = "/{id}/status", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstallmentResponse> updateInstallmentStatus(
            @PathVariable Long id,
            @RequestParam("data") String request,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        // converte DTO -> domínio
        InstallmentResponse response = service.updateStatus(converter(request), id, file);
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
    @DeleteMapping("/all/{debtId}")
    public ResponseEntity<Void> deleteAllInstallments(@PathVariable Long debtId) {
        System.out.println("CHEGOU NA API PARA DELETAR");
        service.deleteAll(debtId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/file")
    public ResponseEntity<InstallmentResponse> uploadFile(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        InstallmentResponse response = service.uploadReceipt(file,id);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/file/{id}/delete")
    public ResponseEntity<InstallmentResponse> deleteFile(@PathVariable Long id) {
        InstallmentResponse response = service.deleteInstallmentReceipt(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<InstallmentResponse>> getAllInstallments(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) BigDecimal amount,
            @RequestParam(required = false) LocalDate paymentDate,
            @RequestParam(required = false) LocalDate invoiceDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer installmentNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "installmentNumber") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Page<InstallmentResponse> installments = service.list(
                id, amount, paymentDate, invoiceDate, status, installmentNumber, page, size, sortField, sortOrder
        );
        return ResponseEntity.ok(installments);

    }
}

