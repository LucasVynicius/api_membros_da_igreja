package br.com.gerenciadordemembros.api.repository;

import br.com.gerenciadordemembros.api.model.Membro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembroRepository extends JpaRepository<Membro, Long> {
}
