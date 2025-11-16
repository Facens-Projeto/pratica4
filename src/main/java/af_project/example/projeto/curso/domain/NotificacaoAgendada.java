package af_project.example.projeto.curso.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa uma notificação registrada para posterior disparo por um job.
 */
@Embeddable
public class NotificacaoAgendada {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_notificacao")
    private NotificacaoTipo tipo;

    @Column(name = "data_referencia")
    private LocalDate referencia;

    @Column(name = "registrada_em")
    private LocalDateTime registradaEm;

    public NotificacaoAgendada() {
    }

    public NotificacaoAgendada(NotificacaoTipo tipo, LocalDate referencia, LocalDateTime registradaEm) {
        this.tipo = tipo;
        this.referencia = referencia;
        this.registradaEm = registradaEm;
    }

    public NotificacaoTipo getTipo() {
        return tipo;
    }

    public void setTipo(NotificacaoTipo tipo) {
        this.tipo = tipo;
    }

    public LocalDate getReferencia() {
        return referencia;
    }

    public void setReferencia(LocalDate referencia) {
        this.referencia = referencia;
    }

    public LocalDateTime getRegistradaEm() {
        return registradaEm;
    }

    public void setRegistradaEm(LocalDateTime registradaEm) {
        this.registradaEm = registradaEm;
    }
}
