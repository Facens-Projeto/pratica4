package af_project.example.projeto.curso.service;

import af_project.example.projeto.curso.domain.CursoMatricula;
import af_project.example.projeto.curso.domain.CursoMatriculaStatus;
import af_project.example.projeto.curso.domain.NotificacaoAgendada;
import af_project.example.projeto.curso.domain.NotificacaoTipo;
import af_project.example.projeto.curso.event.NotificacaoMatriculaEvent;
import af_project.example.projeto.curso.repository.CursoMatriculaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CursoMatriculaServiceTest {

    @Mock
    private CursoMatriculaRepository repository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CursoMatriculaService service;

    @Test
    void deveCriarLimitesENotificacoesAoMatricularAssinanteAtivo() {
        // stubbing só aqui, porque esse teste usa o retorno do save
        when(repository.save(any(CursoMatricula.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LocalDate dataMatricula = LocalDate.of(2024, 1, 10);

        CursoMatricula matricula = service.matricularAssinanteAtivo(5L, 2L, dataMatricula);

        assertThat(matricula.getDataLimiteInicio()).isEqualTo(dataMatricula.plusDays(7));
        assertThat(matricula.getDataLimiteConclusao()).isEqualTo(dataMatricula.plusYears(1));
        assertThat(matricula.getNotificacoes()).hasSize(2);
        assertThat(matricula.getNotificacoes())
                .extracting(NotificacaoAgendada::getTipo)
                .containsExactlyInAnyOrder(NotificacaoTipo.LIMITE_INICIO, NotificacaoTipo.LIMITE_CONCLUSAO);

        ArgumentCaptor<NotificacaoMatriculaEvent> eventCaptor =
                ArgumentCaptor.forClass(NotificacaoMatriculaEvent.class);
        verify(eventPublisher, times(2)).publishEvent(eventCaptor.capture());

        assertThat(eventCaptor.getAllValues())
                .extracting(NotificacaoMatriculaEvent::tipo)
                .containsExactlyInAnyOrder(NotificacaoTipo.LIMITE_INICIO, NotificacaoTipo.LIMITE_CONCLUSAO);
    }

    @Test
    void deveValidarLimitesAoAtualizarStatus() {
        CursoMatricula matricula = new CursoMatricula();
        matricula.setId(9L);
        matricula.setAlunoId(3L);
        matricula.setCursoId(7L);
        matricula.setDataMatricula(LocalDate.of(2024, 1, 1));
        matricula.setDataLimiteInicio(LocalDate.of(2024, 1, 8));
        matricula.setDataLimiteConclusao(LocalDate.of(2025, 1, 1));
        matricula.setStatus(CursoMatriculaStatus.AGUARDANDO_INICIO);

        when(repository.findById(eq(9L))).thenReturn(Optional.of(matricula));

        // EM_ANDAMENTO dentro do prazo -> OK
        LocalDate dentroDoPrazo = LocalDate.of(2024, 1, 7);
        service.atualizarStatus(9L, CursoMatriculaStatus.EM_ANDAMENTO, dentroDoPrazo);

        // CONCLUIDO dentro do prazo -> OK
        LocalDate conclusaoNoPrazo = LocalDate.of(2024, 12, 31);
        service.atualizarStatus(9L, CursoMatriculaStatus.CONCLUIDO, conclusaoNoPrazo);

        // Duas atualizações persistidas
        verify(repository, times(2)).save(matricula);

        // EM_ANDAMENTO após o prazo de início -> erro
        LocalDate depoisDoPrazo = LocalDate.of(2024, 1, 9);
        assertThatThrownBy(() ->
                service.atualizarStatus(9L, CursoMatriculaStatus.EM_ANDAMENTO, depoisDoPrazo))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("prazo limite para início");

        // CONCLUIDO após o prazo de conclusão -> erro
        LocalDate depoisConclusao = LocalDate.of(2025, 1, 2);
        assertThatThrownBy(() ->
                service.atualizarStatus(9L, CursoMatriculaStatus.CONCLUIDO, depoisConclusao))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("prazo limite para conclusão");
    }

    @Test
    void deveListarNotificacoesOrdenadasPorDataDeRegistro() {
        // Matrícula 1 com duas notificações
        CursoMatricula m1 = new CursoMatricula();
        NotificacaoAgendada n1 = new NotificacaoAgendada(
                NotificacaoTipo.LIMITE_INICIO,
                LocalDate.of(2024, 1, 10),
                LocalDateTime.of(2024, 1, 1, 10, 0));
        NotificacaoAgendada n2 = new NotificacaoAgendada(
                NotificacaoTipo.LIMITE_CONCLUSAO,
                LocalDate.of(2024, 2, 10),
                LocalDateTime.of(2024, 1, 1, 8, 0));
        m1.setNotificacoes(List.of(n1, n2));

        // Matrícula 2 com uma notificação
        CursoMatricula m2 = new CursoMatricula();
        NotificacaoAgendada n3 = new NotificacaoAgendada(
                NotificacaoTipo.LIMITE_INICIO,
                LocalDate.of(2024, 3, 10),
                LocalDateTime.of(2024, 1, 1, 12, 0));
        m2.setNotificacoes(List.of(n3));

        when(repository.findByAlunoId(3L)).thenReturn(List.of(m1, m2));

        var resultado = service.listarNotificacoes(3L);

        assertThat(resultado)
                .hasSize(3)
                .extracting(NotificacaoAgendada::getRegistradaEm)
                .containsExactly(
                        n2.getRegistradaEm(),   // 08:00
                        n1.getRegistradaEm(),   // 10:00
                        n3.getRegistradaEm()    // 12:00
                );
    }
}
