package af_project.example.projeto.curso.service;

import af_project.example.projeto.curso.domain.CursoMatricula;
import af_project.example.projeto.curso.domain.CursoMatriculaStatus;
import af_project.example.projeto.curso.domain.NotificacaoAgendada;
import af_project.example.projeto.curso.domain.NotificacaoTipo;
import af_project.example.projeto.curso.repository.CursoMatriculaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    @BeforeEach
    void setUp() {
        when(repository.save(any(CursoMatricula.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void deveCriarLimitesENotificacoesAoMatricularAssinanteAtivo() {
        LocalDate dataMatricula = LocalDate.of(2024, 1, 10);

        CursoMatricula matricula = service.matricularAssinanteAtivo(5L, 2L, dataMatricula);

        assertThat(matricula.getDataLimiteInicio()).isEqualTo(dataMatricula.plusDays(7));
        assertThat(matricula.getDataLimiteConclusao()).isEqualTo(dataMatricula.plusYears(1));
        assertThat(matricula.getNotificacoes()).hasSize(2);
        assertThat(matricula.getNotificacoes())
                .extracting(NotificacaoAgendada::getTipo)
                .containsExactlyInAnyOrder(NotificacaoTipo.LIMITE_INICIO, NotificacaoTipo.LIMITE_CONCLUSAO);

        ArgumentCaptor<Object> eventCaptor = ArgumentCaptor.forClass(Object.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        verify(eventPublisher).publishEvent(eventCaptor.capture());
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

        LocalDate dentroDoPrazo = LocalDate.of(2024, 1, 7);
        service.atualizarStatus(9L, CursoMatriculaStatus.EM_ANDAMENTO, dentroDoPrazo);
        verify(repository).save(matricula);

        LocalDate depoisDoPrazo = LocalDate.of(2024, 1, 9);
        assertThatThrownBy(() -> service.atualizarStatus(9L, CursoMatriculaStatus.EM_ANDAMENTO, depoisDoPrazo))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("prazo limite para início");

        LocalDate depoisConclusao = LocalDate.of(2025, 1, 2);
        assertThatThrownBy(() -> service.atualizarStatus(9L, CursoMatriculaStatus.CONCLUIDO, depoisConclusao))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("prazo limite para conclusão");
    }
}
