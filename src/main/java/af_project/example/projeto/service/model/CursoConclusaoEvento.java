package af_project.example.projeto.service.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Evento emitido ao final de um curso.
 */
@Getter
@EqualsAndHashCode
public class CursoConclusaoEvento {

    private final boolean concluido;
    private final int cargaHorariaTotal;
    private final int cargaHorariaCumprida;
    private final double notaFinal;

    public CursoConclusaoEvento(boolean concluido,
                                int cargaHorariaTotal,
                                int cargaHorariaCumprida,
                                double notaFinal) {
        this.concluido = concluido;
        this.cargaHorariaTotal = cargaHorariaTotal;
        this.cargaHorariaCumprida = cargaHorariaCumprida;
        this.notaFinal = notaFinal;
    }

    public boolean indicaConclusao() {
        return concluido;
    }
}
