package br.com.gerenciadordemembros.api.repository;

import br.com.gerenciadordemembros.api.model.Registry;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistryRepository extends JpaRepository<Registry, Long> {
    boolean existsByRegistryNumber(@NotBlank(message = "O número de registro é obrigatório") String s);
}
