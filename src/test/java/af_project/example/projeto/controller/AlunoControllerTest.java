package af_project.example.projeto.controller;

import af_project.example.projeto.dto.AlunoPerfilDTO;
import af_project.example.projeto.entity.Recompensa;
import af_project.example.projeto.service.AlunoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlunoController.class)
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlunoService alunoService;

    @Test
    void perfilDeveRetornarPerfilDoAlunoComLimitePadraoDeConquistas() throws Exception {
        Long alunoId = 1L;

        AlunoPerfilDTO dto = new AlunoPerfilDTO(
                alunoId,
                "João",
                List.<Recompensa>of(),
                List.of("Conquista 1", "Conquista 2", "Conquista 3")
        );

        // quando não passar o parâmetro "conquistas", usa defaultValue = 3
        when(alunoService.perfil(alunoId, 3)).thenReturn(dto);

        mockMvc.perform(get("/alunos/{alunoId}/perfil", alunoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.conquistasRecentes.length()").value(3));
    }

    @Test
    void perfilDeveRespeitarLimiteDeConquistasPassadoPorQueryParam() throws Exception {
        Long alunoId = 2L;

        AlunoPerfilDTO dto = new AlunoPerfilDTO(
                alunoId,
                "Maria",
                List.<Recompensa>of(),
                List.of("A", "B")
        );

        // aqui o limite vem do parâmetro ?conquistas=2
        when(alunoService.perfil(alunoId, 2)).thenReturn(dto);

        mockMvc.perform(
                        get("/alunos/{alunoId}/perfil", alunoId)
                                .param("conquistas", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nome").value("Maria"))
                .andExpect(jsonPath("$.conquistasRecentes.length()").value(2));
    }
}
