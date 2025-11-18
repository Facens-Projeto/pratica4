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
    void deveRetornarListaVaziaQuandoNaoHaCursos() {
        List<CursoDisponivel> lista = repository.findAll();
        assertTrue(lista.isEmpty());
    }

    @Test
    void deveRetornarTodosCursosPersistidos() {
        // usando o builder gerado pelo Lombok em CursoDisponivel
        CursoDisponivel c1 = CursoDisponivel.builder()
                .nome("Java Básico")
                .descricao("Curso introdutório")
                .build();

        CursoDisponivel c2 = CursoDisponivel.builder()
                .nome("Spring Boot")
                .descricao("Curso avançado de Spring")
                .build();

        repository.save(c1);
        repository.save(c2);

        List<CursoDisponivel> lista = repository.findAll();

        assertEquals(2, lista.size());
        assertTrue(lista.stream().anyMatch(c -> "Java Básico".equals(c.getNome())));
        assertTrue(lista.stream().anyMatch(c -> "Spring Boot".equals(c.getNome())));
    }
}
