package af_project.example.projeto.curso.dto;

import af_project.example.projeto.curso.domain.NotificacaoAgendada;
import af_project.example.projeto.curso.domain.NotificacaoTipo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NotificacaoAgendadaDTO(
        NotificacaoTipo tipo,
        LocalDate referencia,
        LocalDateTime registradaEm) {

    public static NotificacaoAgendadaDTO from(NotificacaoAgendada notificacao) {
        return new NotificacaoAgendadaDTO(
                notificacao.getTipo(),
                notificacao.getReferencia(),
                notificacao.getRegistradaEm());
    }
}
