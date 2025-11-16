package af_project.example.projeto.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import af_project.example.projeto.service.RecompensaService;
import af_project.example.projeto.service.exception.MediaInsuficienteException;

class RecompensaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RecompensaService recompensaService;

    @InjectMocks
    private RecompensaController recompensaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recompensaController).build();
    }

    @Test
    void deveRetornarMensagemQuandoAlunoBloqueado() throws Exception {
        when(recompensaService.listarCursosDisponiveis(5L))
                .thenThrow(new MediaInsuficienteException(RecompensaService.MENSAGEM_MEDIA_INSUFICIENTE));

        mockMvc.perform(get("/recompensas/alunos/5/cursos"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value(RecompensaService.MENSAGEM_MEDIA_INSUFICIENTE));
    }
}
