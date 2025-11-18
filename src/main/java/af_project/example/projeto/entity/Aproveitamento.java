package af_project.example.projeto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_aproveitamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aproveitamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long alunoId;

    @Column(precision = 4, scale = 2)
    private BigDecimal media;

    private LocalDate dataReferencia;
}
