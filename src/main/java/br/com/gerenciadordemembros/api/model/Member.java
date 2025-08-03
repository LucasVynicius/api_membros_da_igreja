package br.com.gerenciadordemembros.api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_membro")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo", nullable = false, length = 150)
    private String fullName;

    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "rg", nullable = false)
    private String rg;

    @Column(name = "telefone", nullable = false)
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "data_batismo")
    private LocalDate baptismDate;

    @Column(name = "data_entrada")
    private LocalDate entryDate;

    @Column(name = "ativo", nullable = false)
    private Boolean active = true;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "igreja_id", referencedColumnName = "id")
    private Church church;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Minister> ministers;

    @Column(name = "criado_em", nullable = false)
    @CreationTimestamp
    private LocalDateTime creatingIn;

    @Column(name = "atualizado_em", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedOn;


}
