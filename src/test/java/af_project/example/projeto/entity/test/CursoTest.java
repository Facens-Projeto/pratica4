package af_project.example.projeto.entity.test;

import af_project.example.projeto.entity.Curso;
import af_project.example.projeto.service.model.Aproveitamento;
import af_project.example.projeto.service.model.CursoConclusaoEvento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CursoTest {

    @Test
    void testCriarCurso() {
        Curso curso = new Curso(1L, "Java");

        assertEquals(1L, curso.getId());
        assertEquals("Java", curso.getNome());
        assertFalse(curso.isConcluido());
        assertTrue(curso.getAproveitamento().isEmpty());
    }

    @Test
    void testConcluirCurso() {
        Curso curso = new Curso(1L, "Java");

        // monta um evento de conclusão qualquer
        CursoConclusaoEvento evento =
                new CursoConclusaoEvento(true, 40, 40, 9.0);

        // cria o aproveitamento a partir do evento
        Aproveitamento aproveitamento = Aproveitamento.aPartirDo(evento);

        // conclui o curso com esse aproveitamento
        curso.concluir(aproveitamento);

        assertTrue(curso.isConcluido());
        assertTrue(curso.getAproveitamento().isPresent());
        assertSame(aproveitamento, curso.getAproveitamento().get());
    }
}
