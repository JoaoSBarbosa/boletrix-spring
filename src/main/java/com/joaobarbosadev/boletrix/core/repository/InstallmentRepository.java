package com.joaobarbosadev.boletrix.core.repository;

import com.joaobarbosadev.boletrix.core.models.domain.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Porta de saída (interface de persistência)
 */
@Repository
public interface InstallmentRepository extends JpaRepository<Installment, Long>, JpaSpecificationExecutor<Installment> {

    @Query("SELECT i.receiptPath FROM Installment i WHERE i.id = :id")
    Optional<String> getReceiptById(@Param("id") Long id);

    @Query("SELECT i FROM Installment i WHERE i.debt.id = :debtId")
    List<Installment> findAllByDebtId(@Param("debtId") Long debtId);
}
