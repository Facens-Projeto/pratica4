package af_project.example.projeto.service;

import af_project.example.projeto.entity.Aproveitamento;
import af_project.example.projeto.entity.CursoDisponivel;
import af_project.example.projeto.repository.AproveitamentoRepository;
import af_project.example.projeto.repository.CursoDisponivelRepository;
import af_project.example.projeto.service.exception.MediaInsuficienteException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecompensaServiceTest {

    @Mock
    private AproveitamentoRepository aproveitamentoRepository;

    @Mock
    private CursoDisponivelRepository cursoDisponivelRepository;

    @InjectMocks
    private RecompensaService recompensaService;

    @Test
    void deveRetornarCursosQuandoMediaSuficiente() {
        Long alunoId = 1L;

        // Aproveitamento com média >= 7.0
        Aproveitamento ap = new Aproveitamento();
        ap.setAlunoId(alunoId);
        ap.setMedia(new BigDecimal("8.0"));
        ap.setDataReferencia(LocalDate.now());

        when(aproveitamentoRepository.findTopByAlunoIdOrderByDataReferenciaDesc(alunoId))
                .thenReturn(Optional.of(ap));

        CursoDisponivel c1 = new CursoDisponivel(1L, "Java Básico", "Intro");
        CursoDisponivel c2 = new CursoDisponivel(2L, "Spring Boot", "Avançado");

        when(cursoDisponivelRepository.findAll())
                .thenReturn(List.of(c1, c2));

        List<CursoDisponivel> cursos = recompensaService.listarCursosDisponiveis(alunoId);

        assertNotNull(cursos);
        assertEquals(2, cursos.size());
        verify(cursoDisponivelRepository, times(1)).findAll();
    }

    @Test
    void deveLancarExcecaoQuandoMediaInsuficiente() {
        Long alunoId = 2L;

        // Aproveitamento com média < 7.0
        Aproveitamento ap = new Aproveitamento();
        ap.setAlunoId(alunoId);
        ap.setMedia(new BigDecimal("6.5"));
        ap.setDataReferencia(LocalDate.now());

        when(aproveitamentoRepository.findTopByAlunoIdOrderByDataReferenciaDesc(alunoId))
                .thenReturn(Optional.of(ap));

        MediaInsuficienteException ex = assertThrows(
                MediaInsuficienteException.class,
                () -> recompensaService.listarCursosDisponiveis(alunoId)
        );

        assertEquals(RecompensaService.MENSAGEM_MEDIA_INSUFICIENTE, ex.getMessage());
        verify(cursoDisponivelRepository, never()).findAll();
    }

    @Test
    void deveLancarExcecaoQuandoNaoHaAproveitamento() {
        Long alunoId = 3L;

        // Nenhum aproveitamento encontrado → considerado bloqueado
        when(aproveitamentoRepository.findTopByAlunoIdOrderByDataReferenciaDesc(alunoId))
                .thenReturn(Optional.empty());

        assertThrows(
                MediaInsuficienteException.class,
                () -> recompensaService.listarCursosDisponiveis(alunoId)
        );

        verify(cursoDisponivelRepository, never()).findAll();
    }
}
