package br.com.gerenciadordemembros.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_igreja")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Igreja implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_igreja", nullable = false, length = 100)
    private String nome;

    @Column(name = "nome_fantasia", length = 100, nullable = false)
    private String nomeFantasia;

    @Column(name = "data_fundacao", nullable = false)
    private LocalDate dataFundacao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(mappedBy = "igreja",cascade = CascadeType.ALL)
    private List<Membro> membros;
}
