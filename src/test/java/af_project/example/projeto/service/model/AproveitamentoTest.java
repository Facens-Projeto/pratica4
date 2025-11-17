package af_project.example.projeto.service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AproveitamentoTest {

    @Test
    void deveRetornarZeroQuandoCargaTotalForInvalida() {
        CursoConclusaoEvento evento = new CursoConclusaoEvento(
                true,
                0,        // carga total inválida
                0,
                10.0
        );

        Aproveitamento ap = Aproveitamento.aPartirDo(evento);

        assertEquals(0.0, ap.getPercentual(), 0.0001);
        assertFalse(ap.isSuficiente());
    }

    @Test
    void deveCalcularPercentualEIndicarSucesso() {
        CursoConclusaoEvento evento = new CursoConclusaoEvento(
                true,
                100,      // total
                100,      // cumprida (100%)
                8.0       // nota 8/10
        );

        Aproveitamento ap = Aproveitamento.aPartirDo(evento);

        // progresso = 1.0
        // notaNormalizada = 0.8
        // percentual = 1.0 * 0.6 + 0.8 * 0.4 = 0.92
        assertEquals(0.92, ap.getPercentual(), 0.0001);
        assertTrue(ap.isSuficiente());
    }

    @Test
    void deveLimitarPercentualEntreZeroEUmaUnidade() {
        // Caso percentual ficaria > 1 (nota 20, carga cumprida = total)
        CursoConclusaoEvento evento = new CursoConclusaoEvento(
                true,
                10,
                10,
                20.0  // 20/10 = 2.0
        );

        Aproveitamento ap = Aproveitamento.aPartirDo(evento);

        // Mesmo com cálculo >1, construtor faz clamp [0, 1]
        assertTrue(ap.getPercentual() <= 1.0);
        assertTrue(ap.getPercentual() >= 0.0);
    }
}
