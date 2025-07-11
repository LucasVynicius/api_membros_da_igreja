package br.com.gerenciadordemembros.api.repository;

import br.com.gerenciadordemembros.api.model.Ministro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinistroRepository extends JpaRepository<Ministro, Long> {
}
