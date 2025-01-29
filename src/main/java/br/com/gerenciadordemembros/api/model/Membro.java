package br.com.gerenciadordemembros.api.model;

import br.com.gerenciadordemembros.api.enums.TipoMembroEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "membro")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Membro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "nacionalidade", nullable = false)
    private String nacionalidade;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "data_batismo", nullable = false)
    private LocalDate dataBatismo;

    @Column(name = "data_consagracao", nullable = false)
    private LocalDate dataConsagracao;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "tipo_membro", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoMembroEnum tipoMembro;
}
