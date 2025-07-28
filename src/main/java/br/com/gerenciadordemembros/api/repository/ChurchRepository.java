package br.com.gerenciadordemembros.api.repository;

import br.com.gerenciadordemembros.api.model.Church;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChurchRepository extends JpaRepository<Church, Long> {
}
