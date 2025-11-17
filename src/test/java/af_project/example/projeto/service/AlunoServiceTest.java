package af_project.example.projeto.service;

import af_project.example.projeto.dto.AlunoPerfilDTO;
import af_project.example.projeto.entity.Aluno;
import af_project.example.projeto.entity.Recompensa;
import af_project.example.projeto.service.model.Aproveitamento;
import af_project.example.projeto.service.model.CursoConclusaoEvento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlunoServiceTest {

    @Test
    void salvarDeveArmazenarAlunoNoMapa() {
        AlunoService service = new AlunoService();
        Aluno aluno = new Aluno(1L, "João");

        Aluno salvo = service.salvar(aluno);

        assertSame(aluno, salvo);
        // garante que realmente foi guardado no "banco em memória"
        Aluno encontrado = service.obterPorId(1L);
        assertSame(aluno, encontrado);
    }

    @Test
    void obterPorIdDeveLancarExcecaoQuandoNaoEncontrado() {
        AlunoService service = new AlunoService();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.obterPorId(99L)
        );

        assertTrue(ex.getMessage().contains("99"));
    }

    @Test
    void perfilDeveMontarDtoComDadosDoAlunoEConquistasLimitadas() {
        AlunoService service = new AlunoService();
        Aluno aluno = new Aluno(1L, "João");

        // registra algumas conquistas (lista limitada a 10 no próprio Aluno)
        aluno.registrarConquista("Conquista 1");
        aluno.registrarConquista("Conquista 2");
        aluno.registrarConquista("Conquista 3");

        service.salvar(aluno);

        // pede perfil com limite 2 conquistas
        AlunoPerfilDTO dto = service.perfil(1L, 2);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("João", dto.getNome());

        // deve respeitar o limite de conquistasRecentes
        assertEquals(2, dto.getConquistasRecentes().size());
        assertEquals("Conquista 3", dto.getConquistasRecentes().get(0));
        assertEquals("Conquista 2", dto.getConquistasRecentes().get(1));
    }
}
