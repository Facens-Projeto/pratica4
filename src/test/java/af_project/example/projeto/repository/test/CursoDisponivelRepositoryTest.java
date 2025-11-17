package af_project.example.projeto.repository.test;

import af_project.example.projeto.entity.CursoDisponivel;
import af_project.example.projeto.repository.CursoDisponivelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CursoDisponivelRepositoryTest {

    @Autowired
    private CursoDisponivelRepository repository;

    @Test
    void deveRetornarListaVaziaSeAlunoBloqueado() {
        List<CursoDisponivel> lista = repository.listarCursosDisponiveis(true);
        assertTrue(lista.isEmpty());
    }

    @Test
    void deveRetornarTodosCursosSeAlunoNaoBloqueado() {
        CursoDisponivel c1 = new CursoDisponivel(null, "Java Básico", "Curso introdutório");
        CursoDisponivel c2 = new CursoDisponivel(null, "Spring Boot", "Framework moderno");

        repository.save(c1);
        repository.save(c2);

        List<CursoDisponivel> lista = repository.listarCursosDisponiveis(false);

        assertEquals(2, lista.size());
        assertTrue(lista.stream().anyMatch(c -> c.getNome().equals("Java Básico")));
        assertTrue(lista.stream().anyMatch(c -> c.getNome().equals("Spring Boot")));
    }
}
