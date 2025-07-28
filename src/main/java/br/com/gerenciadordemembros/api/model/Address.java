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
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "logradouro", nullable = false, length = 150)
    private String street;

    @Column(name = "numero", nullable = false, length = 10)
    private String number;

    @Column(name = "complemento", nullable = false, length = 50)
    private String complement;

    @Column(name = "bairro", nullable = false, length = 50)
    private String neighborhood;

    @Column(name = "cidade", nullable = false, length = 50)
    private String city;

    @Column(name = "estado", nullable = false, length = 50)
    private String state;

    @Column(name = "pais", nullable = false, length = 50)
    private String country;

    @Column(name = "nacionalidade", nullable = false)
    private String nationality;

    @Column(name = "cep", nullable = false, length = 20)
    private String zipCode;
}
