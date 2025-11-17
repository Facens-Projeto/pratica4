package af_project.example.projeto.curso.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CursoMatriculaMethodsTest {

    @Test
    void deveDefinirEObterNotificacoes() {
        NotificacaoAgendada notificacao = new NotificacaoAgendada(
                NotificacaoTipo.LIMITE_INICIO,
                LocalDate.now(),
                LocalDate.now().atStartOfDay()
        );

        CursoMatricula matricula = new CursoMatricula();
        matricula.setNotificacoes(List.of(notificacao));

        assertEquals(1, matricula.getNotificacoes().size());
        assertEquals(notificacao, matricula.getNotificacoes().get(0));
    }

    @Test
    void deveObterIdQuandoDefinido() {
        CursoMatricula matricula = new CursoMatricula();
        matricula.setId(123L);

        assertEquals(123L, matricula.getId());
    }

    @Test
    void deveObterStatusQuandoDefinido() {
        CursoMatricula matricula = new CursoMatricula();
        matricula.setStatus(CursoMatriculaStatus.EM_ANDAMENTO);

        assertEquals(CursoMatriculaStatus.EM_ANDAMENTO, matricula.getStatus());
    }

    @Test
    void deveDefinirEObterDatasLimite() {
        CursoMatricula matricula = new CursoMatricula();
        LocalDate inicio = LocalDate.now();
        LocalDate conclusao = inicio.plusDays(10);

        matricula.setDataLimiteInicio(inicio);
        matricula.setDataLimiteConclusao(conclusao);

        assertEquals(inicio, matricula.getDataLimiteInicio());
        assertEquals(conclusao, matricula.getDataLimiteConclusao());
    }
}
