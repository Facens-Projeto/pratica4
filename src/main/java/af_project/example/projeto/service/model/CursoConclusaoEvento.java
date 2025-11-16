package af_project.example.projeto.service.model;

/**
 * Evento emitido ao final de um curso.
 */
public class CursoConclusaoEvento {

    private final boolean concluido;
    private final int cargaHorariaTotal;
    private final int cargaHorariaCumprida;
    private final double notaFinal;

    public CursoConclusaoEvento(boolean concluido, int cargaHorariaTotal, int cargaHorariaCumprida, double notaFinal) {
        this.concluido = concluido;
        this.cargaHorariaTotal = cargaHorariaTotal;
        this.cargaHorariaCumprida = cargaHorariaCumprida;
        this.notaFinal = notaFinal;
    }

    public boolean indicaConclusao() {
        return concluido;
    }

    public int getCargaHorariaTotal() {
        return cargaHorariaTotal;
    }

    public int getCargaHorariaCumprida() {
        return cargaHorariaCumprida;
    }

    public double getNotaFinal() {
        return notaFinal;
    }
}
