package af_project.example.projeto.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_curso_disponivel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoDisponivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;
}
