package br.com.gerenciadordemembros.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "tb_registro")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Registry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_registro", nullable = false, length = 10)
    private String registryType;

    @Column(name = "numero_registro", nullable = false, unique = true, length = 20)
    private String registryNumber;

    @OneToOne(mappedBy = "registry")
    private Church church;
}
