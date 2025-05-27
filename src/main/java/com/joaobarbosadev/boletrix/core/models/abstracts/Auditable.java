package com.joaobarbosadev.boletrix.core.models.abstracts;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

    @CreatedDate
    @Column(name = "criado_em")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "atualizado_em")
    private LocalDateTime updatedAt;
}
