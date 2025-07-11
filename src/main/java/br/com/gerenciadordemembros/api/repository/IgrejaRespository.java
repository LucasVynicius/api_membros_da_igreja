package br.com.gerenciadordemembros.api.repository;

import br.com.gerenciadordemembros.api.model.Igreja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IgrejaRespository extends JpaRepository<Igreja, Long> {
}
