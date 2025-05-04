package com.joaobarbosadev.boletrix.core.repository;

import com.joaobarbosadev.boletrix.core.models.domain.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSystemRepository extends JpaRepository<UserSystem, Long> {

    @Query("SELECT u FROM  UserSystem u WHERE u.email = :email")
    Optional<UserSystem> findByEmail(@Param("email") String email);
}
