package af_project.example.projeto.curso.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificacaoAgendadaTest {

    @Test
    void deveCriarNotificacaoAgendadaComConstrutorCompleto() {
        NotificacaoTipo tipo = NotificacaoTipo.LIMITE_CONCLUSAO;
        LocalDate referencia = LocalDate.now();
        LocalDateTime registradaEm = LocalDateTime.now();

        NotificacaoAgendada n = new NotificacaoAgendada(tipo, referencia, registradaEm);

        assertEquals(tipo, n.getTipo());
        assertEquals(referencia, n.getReferencia());
        assertEquals(registradaEm, n.getRegistradaEm());
    }

    @Test
    void devePermitirAlterarOsCamposComSetters() {
        NotificacaoAgendada n = new NotificacaoAgendada();

        NotificacaoTipo tipo = NotificacaoTipo.LIMITE_INICIO;
        LocalDate referencia = LocalDate.now().minusDays(1);
        LocalDateTime registradaEm = LocalDateTime.now().minusHours(2);

        n.setTipo(tipo);
        n.setReferencia(referencia);
        n.setRegistradaEm(registradaEm);

        assertEquals(tipo, n.getTipo());
        assertEquals(referencia, n.getReferencia());
        assertEquals(registradaEm, n.getRegistradaEm());
    }
}
