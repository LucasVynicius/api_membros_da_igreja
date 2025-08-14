package br.com.gerenciadordemembros.api.repository;

import br.com.gerenciadordemembros.api.model.Minister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MinisterRepository extends JpaRepository<Minister, Long> {
    boolean existsByMemberId(Long memberId);

    Optional<Minister> findByMemberId(Long memberId);
}
