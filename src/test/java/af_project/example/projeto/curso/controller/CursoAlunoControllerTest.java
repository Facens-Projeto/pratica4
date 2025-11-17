package af_project.example.projeto.curso.controller;

import af_project.example.projeto.curso.domain.NotificacaoAgendada;
import af_project.example.projeto.curso.domain.NotificacaoTipo;
import af_project.example.projeto.curso.service.CursoMatriculaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CursoAlunoController.class)
class CursoAlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoMatriculaService cursoMatriculaService;

    @Test
    void listarNotificacoesDeveRetornarListaDeNotificacoesDoAluno() throws Exception {
        Long alunoId = 10L;

        NotificacaoAgendada n1 = new NotificacaoAgendada(
                NotificacaoTipo.LIMITE_INICIO,
                LocalDate.of(2024, 1, 1),
                LocalDateTime.of(2024, 1, 1, 10, 0));

        NotificacaoAgendada n2 = new NotificacaoAgendada(
                NotificacaoTipo.LIMITE_CONCLUSAO,
                LocalDate.of(2024, 1, 20),
                LocalDateTime.of(2024, 1, 20, 12, 0));

        when(cursoMatriculaService.listarNotificacoes(alunoId))
                .thenReturn(List.of(n1, n2));

        mockMvc.perform(get("/alunos/{alunoId}/notificacoes", alunoId))
                .andExpect(status().isOk())
                // tamanho da lista
                .andExpect(jsonPath("$.length()").value(2))
                // valida tipo da primeira e segunda notificação
                .andExpect(jsonPath("$[0].tipo").value("LIMITE_INICIO"))
                .andExpect(jsonPath("$[1].tipo").value("LIMITE_CONCLUSAO"));
    }

    @Test
    void listarNotificacoesDeveRetornarListaVaziaQuandoNaoHouverNotificacoes() throws Exception {
        Long alunoId = 99L;

        when(cursoMatriculaService.listarNotificacoes(alunoId))
                .thenReturn(List.of());

        mockMvc.perform(get("/alunos/{alunoId}/notificacoes", alunoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
