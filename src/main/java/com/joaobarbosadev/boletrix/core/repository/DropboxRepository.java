package com.joaobarbosadev.boletrix.core.repository;

import com.joaobarbosadev.boletrix.core.models.domain.Dropbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DropboxRepository extends JpaRepository<Dropbox, Long> {

    @Query("SELECT d FROM Dropbox d ORDER BY d.id ASC")
    Optional<Dropbox> getDropbox();
}
