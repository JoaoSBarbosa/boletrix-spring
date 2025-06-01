package com.joaobarbosadev.boletrix.core.repository;

import com.joaobarbosadev.boletrix.core.models.domain.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
}
