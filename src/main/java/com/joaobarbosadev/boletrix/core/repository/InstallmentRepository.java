package com.joaobarbosadev.boletrix.core.repository;

import com.joaobarbosadev.boletrix.core.models.domain.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Porta de saída (interface de persistência)
 */
@Repository
public interface InstallmentRepository extends JpaRepository<Installment, Long>, JpaSpecificationExecutor<Installment> {

}
