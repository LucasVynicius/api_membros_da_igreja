package br.com.gerenciadordemembros.api.model;

import br.com.gerenciadordemembros.api.enums.TipoMembroEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "membro")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
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

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataBatismo() {
        return dataBatismo;
    }

    public void setDataBatismo(LocalDate dataBatismo) {
        this.dataBatismo = dataBatismo;
    }

    public LocalDate getDataConsagracao() {
        return dataConsagracao;
    }

    public void setDataConsagracao(LocalDate dataConsagracao) {
        this.dataConsagracao = dataConsagracao;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public TipoMembroEnum getTipoMembro() {
        return tipoMembro;
    }

    public void setTipoMembro(TipoMembroEnum tipoMembro) {
        this.tipoMembro = tipoMembro;
    }
}
