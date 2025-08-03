package br.com.gerenciadordemembros.api.repository;

import br.com.gerenciadordemembros.api.model.Minister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinisterRepository extends JpaRepository<Minister, Long> {
    boolean existsByMemberId(Long memberId);
}
