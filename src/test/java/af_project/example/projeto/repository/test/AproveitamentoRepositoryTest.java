package af_project.example.projeto.repository.test;

import af_project.example.projeto.entity.Aproveitamento;
import af_project.example.projeto.repository.AproveitamentoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AproveitamentoRepositoryTest {

    @Autowired
    private AproveitamentoRepository repository;

    @Test
    void deveSalvarEConsultarPorId() {
        Aproveitamento ap = new Aproveitamento();
        ap.setAlunoId(1L);
        ap.setMedia(new BigDecimal("8.5"));
        ap.setDataReferencia(LocalDate.of(2024, 1, 1));

        Aproveitamento salvo = repository.save(ap);

        Optional<Aproveitamento> encontrado = repository.findById(salvo.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(1L, encontrado.get().getAlunoId());
    }

    @Test
    void deveBuscarUltimoAproveitamentoDoAluno() {
        Aproveitamento a1 = new Aproveitamento();
        a1.setAlunoId(5L);
        a1.setMedia(new BigDecimal("7.0"));
        a1.setDataReferencia(LocalDate.of(2023, 1, 10));

        Aproveitamento a2 = new Aproveitamento();
        a2.setAlunoId(5L);
        a2.setMedia(new BigDecimal("9.0"));
        a2.setDataReferencia(LocalDate.of(2024, 1, 10));

        repository.save(a1);
        repository.save(a2);

        Optional<Aproveitamento> ult = repository.findTopByAlunoIdOrderByDataReferenciaDesc(5L);

        assertTrue(ult.isPresent());
        assertEquals(new BigDecimal("9.0"), ult.get().getMedia());
    }
}
