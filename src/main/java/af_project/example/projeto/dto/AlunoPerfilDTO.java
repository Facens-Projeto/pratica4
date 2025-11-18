package af_project.example.projeto.dto;

import af_project.example.projeto.entity.Aluno;
import af_project.example.projeto.entity.Recompensa;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AlunoPerfilDTO {

    private final Long id;
    private final String nome;
    private final List<Recompensa> recompensas;
    private final List<String> conquistasRecentes;

    public static AlunoPerfilDTO from(Aluno aluno, List<String> conquistasRecentes) {
        return new AlunoPerfilDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getRecompensas(),
                conquistasRecentes
        );
    }
}
