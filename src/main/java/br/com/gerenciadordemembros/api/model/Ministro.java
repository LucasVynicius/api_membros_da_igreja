package br.com.gerenciadordemembros.api.model;

import br.com.gerenciadordemembros.api.enums.CargoMinisterial;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_ministro")
public class Ministro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "cargo", nullable = false, length = 60)
    private CargoMinisterial cargo;

    @Column(name = "data_consagracao", nullable = false)
    private LocalDate dataConsagracao;

    @ManyToOne
    @JoinColumn(name = "membro_id", referencedColumnName = "id")
    private Membro membro;

    @ManyToOne
    @JoinColumn(name = "igreja_id", referencedColumnName = "id")
    private Igreja igreja;
}
