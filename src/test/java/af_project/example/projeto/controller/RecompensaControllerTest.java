package af_project.example.projeto.controller;

import af_project.example.projeto.entity.CursoDisponivel;
import af_project.example.projeto.service.RecompensaService;
import af_project.example.projeto.service.exception.MediaInsuficienteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecompensaController.class)
class RecompensaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecompensaService recompensaService;

    @Test
    void deveRetornarCursosQuandoMediaSuficiente() throws Exception {
        CursoDisponivel curso = new CursoDisponivel(1L, "Java", "Descricao");
        given(recompensaService.listarCursosDisponiveis(eq(10L)))
                .willReturn(List.of(curso));

        mockMvc.perform(get("/recompensas/alunos/10/cursos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void deveRetornarBadRequestQuandoMediaInsuficiente() throws Exception {
        doThrow(new MediaInsuficienteException("Média insuficiente"))
                .when(recompensaService).listarCursosDisponiveis(eq(20L));

        mockMvc.perform(get("/recompensas/alunos/20/cursos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem", is("Média insuficiente")));
    }
}
