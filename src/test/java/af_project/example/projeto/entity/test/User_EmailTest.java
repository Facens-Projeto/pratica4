package af_project.example.projeto.entity.test;

import af_project.example.projeto.entity.User_Email;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class User_EmailTest {

    @Test
    void deveCriarEmailValido() {
        User_Email email = new User_Email("valid@test.com");

        assertEquals("valid@test.com", email.getEmailAddress());

        // 👉 Esta linha é o que falta pro JaCoCo cobrir o toString()
        assertEquals("valid@test.com", email.toString());
    }


    @Test
    void deveLancarExcecaoParaEmailInvalido_Null() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> new User_Email(null));

        assertTrue(ex.getMessage().contains("Email inválido"));
    }

    @Test
    void deveLancarExcecaoParaEmailInvalido_FormatoErrado() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> new User_Email("invalid-email"));

        assertTrue(ex.getMessage().contains("Email inválido"));
    }

    @Test
    void equalsDeveRetornarVerdadeiroParaEmailsIguais() {
        User_Email e1 = new User_Email("same@test.com");
        User_Email e2 = new User_Email("same@test.com");

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void equalsDeveRetornarFalsoParaEmailsDiferentes() {
        User_Email e1 = new User_Email("a@test.com");
        User_Email e2 = new User_Email("b@test.com");

        assertNotEquals(e1, e2);
    }

    @Test
    void equalsDeveRetornarFalsoParaNull() {
        User_Email e1 = new User_Email("a@test.com");

        assertNotEquals(e1, null);
    }

    @Test
    void equalsDeveRetornarFalsoQuandoTiposDiferem() {
        User_Email e1 = new User_Email("a@test.com");

        assertNotEquals(e1, "string");
    }

    @Test
    void equalsDeveRetornarVerdadeiroParaMesmaInstancia() {
        User_Email e1 = new User_Email("self@test.com");

        assertEquals(e1, e1);
    }
}
