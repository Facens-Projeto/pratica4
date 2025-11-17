package af_project.example.projeto.entity.test;

import af_project.example.projeto.entity.Recompensa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecompensaTest {

    @Test
    void testCriarRecompensaVagasExtra() {
        Recompensa r = Recompensa.vagasExtra("Java", 3);

        assertNotNull(r);
        assertEquals("Convite especial - Java", r.getTitulo());
        assertEquals(3, r.getVagas());
        assertNotNull(r.getGeradaEm());
    }
}
