package af_project.example.projeto.entity.test;

import af_project.example.projeto.entity.Aluno;
import af_project.example.projeto.entity.Recompensa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlunoTest {

    @Test
    void testCriarAluno() {
        Aluno aluno = new Aluno(1L, "João");

        assertEquals(1L, aluno.getId());
        assertEquals("João", aluno.getNome());
        assertNotNull(aluno.getRecompensas());
    }

    @Test
    void testAdicionarRecompensa() {
        Aluno aluno = new Aluno(1L, "João");
        Recompensa recompensa = Recompensa.vagasExtra("Java", 5);

        aluno.adicionarRecompensa(recompensa);

        assertEquals(1, aluno.getRecompensas().size());
        assertEquals("Convite especial - Java",
                aluno.getRecompensas().get(0).getTitulo());
    }
}
