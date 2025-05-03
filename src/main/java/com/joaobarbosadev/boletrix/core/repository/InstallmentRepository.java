package com.joaobarbosadev.boletrix.core.repository;

import com.joaobarbosadev.boletrix.core.domain.Installment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Porta de saída (interface de persistência)
 */
public interface InstallmentRepository {

    Installment save(Installment installment);
    Optional<Installment> findById(long id);
    Page<Installment> findAll(Pageable pageable);
    void deleteById(long id);
}
