package af_project.example.projeto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProjetoApplicationTests {

    @Test
    void mainDeveExecutarSemErros() {
        assertDoesNotThrow(() -> ProjetoApplication.main(new String[]{}));
    }
}
