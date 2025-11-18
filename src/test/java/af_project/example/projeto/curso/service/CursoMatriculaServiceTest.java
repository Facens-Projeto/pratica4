package af_project.example.projeto.curso.service;

import af_project.example.projeto.curso.domain.CursoMatricula;
import af_project.example.projeto.curso.domain.CursoMatriculaStatus;
import af_project.example.projeto.curso.domain.NotificacaoAgendada;
import af_project.example.projeto.curso.domain.NotificacaoTipo;
import af_project.example.projeto.curso.event.NotificacaoMatriculaEvent;
import af_project.example.projeto.curso.repository.CursoMatriculaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static af_project.example.projeto.curso.domain.CursoMatriculaStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CursoMatriculaServiceTest {

    @Mock
    private CursoMatriculaRepository repository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CursoMatriculaService service;

    // ----------------------------------------------------------------------
    // 1) matricularAssinanteAtivo - data informada (ramo dataMatricula != null)
    // ----------------------------------------------------------------------
    @Test
    void matricularAssinanteAtivo_deveUsarDataInformada_quandoNaoNula() {
        Long alunoId = 1L;
        Long cursoId = 10L;
        LocalDate dataInformada = LocalDate.of(2024, 1, 10);

        // repository.save apenas devolve o objeto que recebeu
        when(repository.save(any(CursoMatricula.class)))
                .thenAnswer(invocation -> invocation.getArgument(0, CursoMatricula.class));

        CursoMatricula matricula = service.matricularAssinanteAtivo(alunoId, cursoId, dataInformada);

        assertEquals(alunoId, matricula.getAlunoId());
        assertEquals(cursoId, matricula.getCursoId());
        assertEquals(dataInformada, matricula.getDataMatricula());
        assertEquals(AGUARDANDO_INICIO, matricula.getStatus());

        // deve registrar 2 notificações
        assertEquals(2, matricula.getNotificacoes().size());
        verify(eventPublisher, times(2)).publishEvent(any(NotificacaoMatriculaEvent.class));
        verify(repository).save(any(CursoMatricula.class));
    }

    // ----------------------------------------------------------------------
    // 2) matricularAssinanteAtivo - data nula (ramo dataMatricula == null)
    // ----------------------------------------------------------------------
    @Test
    void matricularAssinanteAtivo_deveUsarDataAtual_quandoDataForNula() {
        Long alunoId = 2L;
        Long cursoId = 20L;

        when(repository.save(any(CursoMatricula.class)))
                .thenAnswer(invocation -> invocation.getArgument(0, CursoMatricula.class));

        CursoMatricula matricula = service.matricularAssinanteAtivo(alunoId, cursoId, null);

        assertEquals(alunoId, matricula.getAlunoId());
        assertEquals(cursoId, matricula.getCursoId());
        assertNotNull(matricula.getDataMatricula()); // só pra garantir que foi setada
        assertEquals(AGUARDANDO_INICIO, matricula.getStatus());
        assertEquals(2, matricula.getNotificacoes().size());
    }

    // ----------------------------------------------------------------------
    // 3) atualizarStatus - caminho feliz, dentro do prazo
    //    (cobre: dataReferencia != null E validarPrazos sem exceção)
    // ----------------------------------------------------------------------
    @Test
    void atualizarStatus_deveAtualizarQuandoDentroDoPrazo() {
        Long matriculaId = 100L;
        LocalDate hoje = LocalDate.of(2024, 1, 10);

        CursoMatricula matricula = new CursoMatricula();
        matricula.setId(matriculaId);
        matricula.setAlunoId(1L);
        matricula.setCursoId(2L);
        matricula.setDataMatricula(hoje);
        matricula.setDataLimiteInicio(hoje.plusDays(7));
        matricula.setDataLimiteConclusao(hoje.plusYears(1));
        matricula.setStatus(AGUARDANDO_INICIO);

        when(repository.findById(matriculaId)).thenReturn(Optional.of(matricula));
        when(repository.save(any(CursoMatricula.class)))
                .thenAnswer(invocation -> invocation.getArgument(0, CursoMatricula.class));

        // referencia antes do limite de início => não deve lançar
        LocalDate referencia = hoje.plusDays(5);

        CursoMatricula atualizado = service.atualizarStatus(matriculaId, EM_ANDAMENTO, referencia);

        assertEquals(EM_ANDAMENTO, atualizado.getStatus());
        verify(repository).findById(matriculaId);
        verify(repository).save(matricula);
    }

    // ----------------------------------------------------------------------
    // 4) atualizarStatus - dataReferencia nula
    //    (cobre o ramo dataReferencia == null do ternário)
    // ----------------------------------------------------------------------
    @Test
    void atualizarStatus_deveUsarDataAtual_quandoDataReferenciaForNula() {
        Long matriculaId = 200L;

        LocalDate hoje = LocalDate.now();
        CursoMatricula matricula = new CursoMatricula();
        matricula.setId(matriculaId);
        matricula.setAlunoId(1L);
        matricula.setCursoId(2L);
        matricula.setDataMatricula(hoje);
        matricula.setDataLimiteInicio(hoje.plusDays(30));
        matricula.setDataLimiteConclusao(hoje.plusYears(1));
        matricula.setStatus(AGUARDANDO_INICIO);

        when(repository.findById(matriculaId)).thenReturn(Optional.of(matricula));
        when(repository.save(any(CursoMatricula.class)))
                .thenAnswer(invocation -> invocation.getArgument(0, CursoMatricula.class));

        // dataReferencia = null -> serviço vai usar LocalDate.now()
        CursoMatricula atualizado = service.atualizarStatus(matriculaId, EM_ANDAMENTO, null);

        assertEquals(EM_ANDAMENTO, atualizado.getStatus());
        verify(repository).findById(matriculaId);
        verify(repository).save(matricula);
    }

    // ----------------------------------------------------------------------
    // 5) atualizarStatus - id inexistente (cobre o lambda do orElseThrow)
    // ----------------------------------------------------------------------
    @Test
    void atualizarStatus_deveLancarEntityNotFound_quandoMatriculaNaoExiste() {
        Long matriculaId = 999L;
        when(repository.findById(matriculaId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> service.atualizarStatus(matriculaId, EM_ANDAMENTO, LocalDate.now()));

        verify(repository).findById(matriculaId);
        verify(repository, never()).save(any());
    }

    // ----------------------------------------------------------------------
    // 6) validarPrazos - estourou prazo de início (ramo if 1 verdadeiro)
    // ----------------------------------------------------------------------
    @Test
    void atualizarStatus_deveLancarExcecao_quandoPrazoDeInicioForExcedido() {
        Long matriculaId = 300L;
        LocalDate hoje = LocalDate.of(2024, 1, 10);

        CursoMatricula matricula = new CursoMatricula();
        matricula.setId(matriculaId);
        matricula.setAlunoId(1L);
        matricula.setCursoId(2L);
        matricula.setDataMatricula(hoje);
        matricula.setDataLimiteInicio(hoje.plusDays(7));
        matricula.setDataLimiteConclusao(hoje.plusYears(1));

        when(repository.findById(matriculaId)).thenReturn(Optional.of(matricula));

        LocalDate referenciaAposLimiteInicio = matricula.getDataLimiteInicio().plusDays(1);

        assertThrows(IllegalStateException.class,
                () -> service.atualizarStatus(matriculaId, EM_ANDAMENTO, referenciaAposLimiteInicio));
    }

    // ----------------------------------------------------------------------
    // 7) validarPrazos - estourou prazo de conclusão (ramo if 2 verdadeiro)
    // ----------------------------------------------------------------------
    @Test
    void atualizarStatus_deveLancarExcecao_quandoPrazoDeConclusaoForExcedido() {
        Long matriculaId = 400L;
        LocalDate hoje = LocalDate.of(2024, 1, 10);

        CursoMatricula matricula = new CursoMatricula();
        matricula.setId(matriculaId);
        matricula.setAlunoId(1L);
        matricula.setCursoId(2L);
        matricula.setDataMatricula(hoje);
        matricula.setDataLimiteInicio(hoje.plusDays(7));
        matricula.setDataLimiteConclusao(hoje.plusYears(1));

        when(repository.findById(matriculaId)).thenReturn(Optional.of(matricula));

        LocalDate referenciaAposLimiteConclusao = matricula.getDataLimiteConclusao().plusDays(1);

        assertThrows(IllegalStateException.class,
                () -> service.atualizarStatus(matriculaId, CONCLUIDO, referenciaAposLimiteConclusao));
    }

    // ----------------------------------------------------------------------
    // 8) listarNotificacoes - garante ordenação e cobertura total do método
    // ----------------------------------------------------------------------
    @Test
    void listarNotificacoes_deveRetornarNotificacoesOrdenadasPorData() {
        Long alunoId = 10L;

        CursoMatricula m1 = new CursoMatricula();
        m1.setAlunoId(alunoId);
        m1.adicionarNotificacao(new NotificacaoAgendada(
                NotificacaoTipo.LIMITE_INICIO,
                LocalDate.of(2024, 1, 10),
                LocalDateTime.of(2024, 1, 1, 10, 0)
        ));

        CursoMatricula m2 = new CursoMatricula();
        m2.setAlunoId(alunoId);
        m2.adicionarNotificacao(new NotificacaoAgendada(
                NotificacaoTipo.LIMITE_CONCLUSAO,
                LocalDate.of(2024, 2, 10),
                LocalDateTime.of(2024, 1, 1, 8, 0)
        ));

        when(repository.findByAlunoId(alunoId)).thenReturn(List.of(m1, m2));

        List<NotificacaoAgendada> notificacoes = service.listarNotificacoes(alunoId);

        assertEquals(2, notificacoes.size());
        // garante ordenação por registradaEm
        assertTrue(notificacoes.get(0).getRegistradaEm()
                .isBefore(notificacoes.get(1).getRegistradaEm()));
    }
}
