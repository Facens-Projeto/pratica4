package af_project.example.projeto.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object que representa o aproveitamento do aluno em um curso.
 */
@Getter
@EqualsAndHashCode
public class Aproveitamento {

    private static final double LIMITE_SUCESSO = 0.75d;

    private final double percentual;

    private Aproveitamento(double percentual) {
        this.percentual = Math.max(0d, Math.min(1d, percentual));
    }

    public static Aproveitamento aPartirDo(CursoConclusaoEvento evento) {
        if (evento.getCargaHorariaTotal() <= 0) {
            return new Aproveitamento(0d);
        }
        double progresso = (double) evento.getCargaHorariaCumprida() / evento.getCargaHorariaTotal();
        double notaNormalizada = evento.getNotaFinal() / 10d;
        double percentual = (progresso * 0.6) + (notaNormalizada * 0.4);
        return new Aproveitamento(percentual);
    }

    public boolean isSuficiente() {
        return percentual >= LIMITE_SUCESSO;
    }
}
