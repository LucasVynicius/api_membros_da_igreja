package br.com.gerenciadordemembros.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "tb_endereco")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "logradouro", nullable = false, length = 150)
    private String logradouro;

    @Column(name = "numero", nullable = false, length = 10)
    private String numero;

    @Column(name = "complemento", nullable = false, length = 50)
    private String complemento;

    @Column(name = "bairro", nullable = false, length = 50)
    private String bairro;

    @Column(name = "cidade", nullable = false, length = 50)
    private String cidade;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "pais", nullable = false, length = 50)
    private String pais;

    @Column(name = "nacionalidade", nullable = false)
    private String nacionalidade;

    @Column(name = "cep", nullable = false, length = 20)
    private String cep;
}
