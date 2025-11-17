package af_project.example.projeto.entity.test;

import af_project.example.projeto.entity.CursoDisponivel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CursoDisponivelTest {

    @Test
    void testCriarCursoDisponivel() {
        CursoDisponivel c = new CursoDisponivel();

        assertNotNull(c);
    }
}
