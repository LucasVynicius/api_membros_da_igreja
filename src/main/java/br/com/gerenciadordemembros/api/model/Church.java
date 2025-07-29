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
public class Church implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_igreja", nullable = false, length = 100)
    private String name;

    @Column(name = "nome_fantasia", length = 100, nullable = false)
    private String tradeName;

    @OneToOne
    @JoinColumn(name = "pastor_local_id", referencedColumnName = "id")
    private Minister pastorLocal;

    @Column(name = "data_fundacao", nullable = false)
    private LocalDate foundationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "church",cascade = CascadeType.ALL)
    private List<Member> members;
}
