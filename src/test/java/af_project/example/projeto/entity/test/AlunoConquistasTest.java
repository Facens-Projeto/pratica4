package af_project.example.projeto.entity.test;

import af_project.example.projeto.entity.Aluno;
import af_project.example.projeto.entity.Recompensa;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlunoConquistasTest {

    @Test
    void deveManterApenasDezConquistasMaisRecentes() {
        Aluno aluno = new Aluno(1L, "Fulano");

        // adiciona 12 conquistas
        for (int i = 1; i <= 12; i++) {
            aluno.registrarConquista("Conquista " + i);
        }

        List<String> conquistas = aluno.conquistasRecentes(20);

        // tamanho máximo 10
        assertEquals(10, conquistas.size());
        // as mais recentes devem ser da 12 até a 3
        assertEquals("Conquista 12", conquistas.get(0));
        assertEquals("Conquista 3", conquistas.get(9));
        // não deve conter as muito antigas
        assertFalse(conquistas.contains("Conquista 1"));
        assertFalse(conquistas.contains("Conquista 2"));
    }

    @Test
    void conquistasRecentesNaoDeveQuebrarComLimiteNegativo() {
        Aluno aluno = new Aluno(1L, "Fulano");
        aluno.registrarConquista("A");

        List<String> conquistas = aluno.conquistasRecentes(-5);

        // Math.max(0, limite) → 0, então lista vazia
        assertTrue(conquistas.isEmpty());
    }

    @Test
    void conquistasRecentesLimitaAoValorInformado() {
        Aluno aluno = new Aluno(1L, "Fulano");
        aluno.registrarConquista("A");
        aluno.registrarConquista("B");
        aluno.registrarConquista("C");

        List<String> conquistas = aluno.conquistasRecentes(2);

        assertEquals(2, conquistas.size());
        assertEquals("C", conquistas.get(0));
        assertEquals("B", conquistas.get(1));
    }
}
