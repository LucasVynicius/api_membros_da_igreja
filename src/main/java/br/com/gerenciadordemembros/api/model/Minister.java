package br.com.gerenciadordemembros.api.model;

import br.com.gerenciadordemembros.api.enums.MinisterialPosition;
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
public class Minister implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "cargo", nullable = false, length = 60)
    private MinisterialPosition position;

    @Column(name = "data_consagracao", nullable = false)
    private LocalDate consecrationDate;

    @ManyToOne
    @JoinColumn(name = "membro_id", referencedColumnName = "id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "igreja_id", referencedColumnName = "id")
    private Church church;
}
