package af_project.example.projeto.entity.test;

import af_project.example.projeto.entity.Aproveitamento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AproveitamentoTest {

    @Test
    void testSettersAndGetters() {
        Aproveitamento a = new Aproveitamento();

        a.setId(10L);
        a.setAlunoId(22L);
        a.setMedia(new BigDecimal("8.75"));
        a.setDataReferencia(LocalDate.of(2024, 1, 1));

        assertEquals(10L, a.getId());
        assertEquals(22L, a.getAlunoId());
        assertEquals(new BigDecimal("8.75"), a.getMedia());
        assertEquals(LocalDate.of(2024, 1, 1), a.getDataReferencia());
    }
}
