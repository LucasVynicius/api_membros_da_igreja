package br.com.gerenciadordemembros.api.repository;

import br.com.gerenciadordemembros.api.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
