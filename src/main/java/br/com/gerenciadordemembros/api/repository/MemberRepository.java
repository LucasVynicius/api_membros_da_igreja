package br.com.gerenciadordemembros.api.repository;

import br.com.gerenciadordemembros.api.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
