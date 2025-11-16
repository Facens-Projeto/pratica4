package af_project.example.projeto.curso.event;

import af_project.example.projeto.curso.domain.NotificacaoTipo;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Evento simples utilizado para sinalizar que uma notificação foi registrada
 * e que um job/broker externo pode agendá-la para envio.
 */
public record NotificacaoMatriculaEvent(
        Long alunoId,
        Long cursoId,
        NotificacaoTipo tipo,
        LocalDate referencia,
        LocalDateTime registradaEm) {
}
