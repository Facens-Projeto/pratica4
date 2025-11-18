package af_project.example.projeto.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * Incentivo gerado para alunos com bom desempenho.
 */
@Getter
@RequiredArgsConstructor   // Gera construtor para todos os campos final
public class Recompensa {

    private final String titulo;
    private final int vagas;
    private final LocalDateTime geradaEm;

    public static Recompensa vagasExtra(String cursoNome, int vagas) {
        return new Recompensa(
                "Convite especial - " + cursoNome,
                vagas,
                LocalDateTime.now()
        );
    }
}
