package af_project.example.projeto.entity;

import java.time.LocalDateTime;

/**
 * Incentivo gerado para alunos com bom desempenho.
 */
public class Recompensa {

    private final String titulo;
    private final int vagas;
    private final LocalDateTime geradaEm;

    private Recompensa(String titulo, int vagas, LocalDateTime geradaEm) {
        this.titulo = titulo;
        this.vagas = vagas;
        this.geradaEm = geradaEm;
    }

    public static Recompensa vagasExtra(String cursoNome, int vagas) {
        return new Recompensa("Convite especial - " + cursoNome, vagas, LocalDateTime.now());
    }

    public String getTitulo() {
        return titulo;
    }

    public int getVagas() {
        return vagas;
    }

    public LocalDateTime getGeradaEm() {
        return geradaEm;
    }
}
