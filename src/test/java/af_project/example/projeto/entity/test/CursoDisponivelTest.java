package af_project.example.projeto.entity.test;

import af_project.example.projeto.entity.CursoDisponivel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CursoDisponivelTest {

    @Test
    void testConstrutorComParametros() {
        CursoDisponivel curso = new CursoDisponivel(1L, "Java", "Introdução ao Java");

        assertEquals(1L, curso.getId());
        assertEquals("Java", curso.getNome());
        assertEquals("Introdução ao Java", curso.getDescricao());
    }

    @Test
    void testConstrutorVazioEGettersSetters() {
        CursoDisponivel curso = new CursoDisponivel();

        curso.setId(10L);
        curso.setNome("Spring Boot");
        curso.setDescricao("Curso avançado usando Spring");

        assertEquals(10L, curso.getId());
        assertEquals("Spring Boot", curso.getNome());
        assertEquals("Curso avançado usando Spring", curso.getDescricao());
    }
}
