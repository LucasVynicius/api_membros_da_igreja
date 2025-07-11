package br.com.gerenciadordemembros.api.repository;

import br.com.gerenciadordemembros.api.model.Igreja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IgrejaRepository extends JpaRepository<Igreja, Long> {
}
