package af_project.example.projeto.curso.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "curso_matriculas")
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
    @CollectionTable(name = "curso_matricula_notificacoes", joinColumns = @JoinColumn(name = "matricula_id"))
    private List<NotificacaoAgendada> notificacoes = new ArrayList<>();

    public CursoMatricula() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public LocalDate getDataLimiteInicio() {
        return dataLimiteInicio;
    }

    public void setDataLimiteInicio(LocalDate dataLimiteInicio) {
        this.dataLimiteInicio = dataLimiteInicio;
    }

    public LocalDate getDataLimiteConclusao() {
        return dataLimiteConclusao;
    }

    public void setDataLimiteConclusao(LocalDate dataLimiteConclusao) {
        this.dataLimiteConclusao = dataLimiteConclusao;
    }

    public CursoMatriculaStatus getStatus() {
        return status;
    }

    public void setStatus(CursoMatriculaStatus status) {
        this.status = status;
    }

    public List<NotificacaoAgendada> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<NotificacaoAgendada> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public void adicionarNotificacao(NotificacaoAgendada notificacao) {
        this.notificacoes.add(notificacao);
    }
}
