package af_project.example.projeto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import af_project.example.projeto.entity.Aproveitamento;
import af_project.example.projeto.entity.CursoDisponivel;
import af_project.example.projeto.repository.AproveitamentoRepository;
import af_project.example.projeto.repository.CursoDisponivelRepository;
import af_project.example.projeto.service.exception.MediaInsuficienteException;

class RecompensaServiceTest {

    @Mock
    private AproveitamentoRepository aproveitamentoRepository;

    @Mock
    private CursoDisponivelRepository cursoDisponivelRepository;

    @InjectMocks
    private RecompensaService recompensaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveLiberarCursosQuandoMediaSuficiente() {
        Aproveitamento aproveitamento = new Aproveitamento();
        aproveitamento.setMedia(new BigDecimal("8.3"));

        List<CursoDisponivel> cursos = List.of(new CursoDisponivel(1L, "API Avançada", "Curso de API"));

        when(aproveitamentoRepository.findTopByAlunoIdOrderByDataReferenciaDesc(1L))
                .thenReturn(Optional.of(aproveitamento));
        when(cursoDisponivelRepository.listarCursosDisponiveis(false)).thenReturn(cursos);

        List<CursoDisponivel> resultado = recompensaService.listarCursosDisponiveis(1L);

        assertEquals(1, resultado.size());
        assertEquals("API Avançada", resultado.get(0).getNome());
        verify(cursoDisponivelRepository).listarCursosDisponiveis(false);
    }

    @Test
    void deveBloquearCursosQuandoMediaBaixa() {
        Aproveitamento aproveitamento = new Aproveitamento();
        aproveitamento.setMedia(new BigDecimal("6.9"));

        when(aproveitamentoRepository.findTopByAlunoIdOrderByDataReferenciaDesc(2L))
                .thenReturn(Optional.of(aproveitamento));
        when(cursoDisponivelRepository.listarCursosDisponiveis(true)).thenReturn(Collections.emptyList());

        MediaInsuficienteException exception = assertThrows(MediaInsuficienteException.class,
                () -> recompensaService.listarCursosDisponiveis(2L));

        assertEquals(RecompensaService.MENSAGEM_MEDIA_INSUFICIENTE, exception.getMessage());
        verify(cursoDisponivelRepository).listarCursosDisponiveis(true);
    }
}
