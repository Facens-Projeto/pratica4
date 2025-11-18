package af_project.example.projeto.curso.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "curso_matriculas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoMatricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long alunoId;

    @Column(nullable = false)
    private Long cursoId;

    @Column(nullable = false)
    private LocalDate dataMatricula;

    @Column(nullable = false)
    private LocalDate dataLimiteInicio;

    @Column(nullable = false)
    private LocalDate dataLimiteConclusao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CursoMatriculaStatus status = CursoMatriculaStatus.AGUARDANDO_INICIO;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "curso_matricula_notificacoes",
            joinColumns = @JoinColumn(name = "matricula_id")
    )
    @Builder.Default
    private List<NotificacaoAgendada> notificacoes = new ArrayList<>();

    public void adicionarNotificacao(NotificacaoAgendada notificacao) {
        this.notificacoes.add(notificacao);
    }
}
