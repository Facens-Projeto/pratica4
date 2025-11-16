package af_project.example.projeto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import af_project.example.projeto.entity.Aluno;
import af_project.example.projeto.entity.Curso;
import af_project.example.projeto.entity.Recompensa;
import af_project.example.projeto.service.model.CursoConclusaoEvento;

class CursoServiceTest {

    private CursoService cursoService;

    @BeforeEach
    void setUp() {
        cursoService = new CursoService();
    }

    @Test
    void deveGerarRecompensaQuandoAproveitamentoSuficiente() {
        Aluno aluno = new Aluno(1L, "Ana");
        Curso curso = new Curso(1L, "API REST");
        CursoConclusaoEvento evento = new CursoConclusaoEvento(true, 40, 40, 9.5);

        Optional<Recompensa> recompensa = cursoService.registrarConclusao(curso, aluno, evento);

        assertTrue(recompensa.isPresent());
        assertTrue(curso.isConcluido());
        assertEquals(1, aluno.getRecompensas().size());
        assertEquals(3, recompensa.get().getVagas());
    }

    @Test
    void naoDeveGerarRecompensaQuandoAproveitamentoInsuficiente() {
        Aluno aluno = new Aluno(2L, "Bruno");
        Curso curso = new Curso(2L, "Kotlin");
        CursoConclusaoEvento evento = new CursoConclusaoEvento(true, 40, 20, 5.0);

        Optional<Recompensa> recompensa = cursoService.registrarConclusao(curso, aluno, evento);

        assertTrue(recompensa.isEmpty());
        assertTrue(curso.isConcluido());
        assertEquals(0, aluno.getRecompensas().size());
    }

    @Test
    void naoDeveGerarRecompensaQuandoCursoNaoConcluido() {
        Aluno aluno = new Aluno(3L, "Carlos");
        Curso curso = new Curso(3L, "NodeJS");
        CursoConclusaoEvento evento = new CursoConclusaoEvento(false, 40, 40, 10.0);

        Optional<Recompensa> recompensa = cursoService.registrarConclusao(curso, aluno, evento);

        assertTrue(recompensa.isEmpty());
        assertFalse(curso.isConcluido());
        assertEquals(0, aluno.getRecompensas().size());
    }
}
