package af_project.example.projeto.curso.service;

import af_project.example.projeto.curso.domain.CursoMatricula;
import af_project.example.projeto.curso.domain.CursoMatriculaStatus;
import af_project.example.projeto.curso.domain.NotificacaoAgendada;
import af_project.example.projeto.curso.domain.NotificacaoTipo;
import af_project.example.projeto.curso.event.NotificacaoMatriculaEvent;
import af_project.example.projeto.curso.repository.CursoMatriculaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class CursoMatriculaService {

    private final CursoMatriculaRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public CursoMatriculaService(CursoMatriculaRepository repository,
                                 ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public CursoMatricula matricularAssinanteAtivo(Long alunoId, Long cursoId, LocalDate dataMatricula) {
        LocalDate dataDaMatricula = dataMatricula != null ? dataMatricula : LocalDate.now();

        CursoMatricula matricula = new CursoMatricula();
        matricula.setAlunoId(alunoId);
        matricula.setCursoId(cursoId);
        matricula.setDataMatricula(dataDaMatricula);
        matricula.setStatus(CursoMatriculaStatus.AGUARDANDO_INICIO);
        matricula.setDataLimiteInicio(dataDaMatricula.plusDays(7));
        matricula.setDataLimiteConclusao(dataDaMatricula.plusYears(1));

        registrarNotificacao(matricula, NotificacaoTipo.LIMITE_INICIO, matricula.getDataLimiteInicio());
        registrarNotificacao(matricula, NotificacaoTipo.LIMITE_CONCLUSAO, matricula.getDataLimiteConclusao());

        return repository.save(matricula);
    }

    @Transactional
    public CursoMatricula atualizarStatus(Long matriculaId, CursoMatriculaStatus novoStatus, LocalDate dataReferencia) {
        CursoMatricula matricula = repository.findById(matriculaId)
                .orElseThrow(() -> new EntityNotFoundException("Matrícula não encontrada: " + matriculaId));

        LocalDate referencia = dataReferencia != null ? dataReferencia : LocalDate.now();
        validarPrazos(matricula, novoStatus, referencia);

        matricula.setStatus(novoStatus);
        return repository.save(matricula);
    }

    @Transactional(readOnly = true)
    public List<NotificacaoAgendada> listarNotificacoes(Long alunoId) {
        return repository.findByAlunoId(alunoId)
                .stream()
                .flatMap(m -> m.getNotificacoes().stream())
                .sorted(Comparator.comparing(NotificacaoAgendada::getRegistradaEm))
                .toList();
    }

    private void registrarNotificacao(CursoMatricula matricula, NotificacaoTipo tipo, LocalDate referencia) {
        NotificacaoAgendada notificacao = new NotificacaoAgendada(tipo, referencia, LocalDateTime.now());
        matricula.adicionarNotificacao(notificacao);
        eventPublisher.publishEvent(new NotificacaoMatriculaEvent(
                matricula.getAlunoId(),
                matricula.getCursoId(),
                tipo,
                referencia,
                notificacao.getRegistradaEm()));
    }

    private void validarPrazos(CursoMatricula matricula, CursoMatriculaStatus novoStatus, LocalDate referencia) {
        if (novoStatus == CursoMatriculaStatus.EM_ANDAMENTO && referencia.isAfter(matricula.getDataLimiteInicio())) {
            throw new IllegalStateException("O prazo limite para início do curso foi excedido");
        }

        if (novoStatus == CursoMatriculaStatus.CONCLUIDO && referencia.isAfter(matricula.getDataLimiteConclusao())) {
            throw new IllegalStateException("O prazo limite para conclusão do curso foi excedido");
        }
    }
}
