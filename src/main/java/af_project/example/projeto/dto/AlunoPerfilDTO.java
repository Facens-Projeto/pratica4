package af_project.example.projeto.dto;

import java.util.List;

import af_project.example.projeto.entity.Aluno;
import af_project.example.projeto.entity.Recompensa;

public class AlunoPerfilDTO {

    private final Long id;
    private final String nome;
    private final List<Recompensa> recompensas;
    private final List<String> conquistasRecentes;

    public AlunoPerfilDTO(Long id, String nome, List<Recompensa> recompensas, List<String> conquistasRecentes) {
        this.id = id;
        this.nome = nome;
        this.recompensas = recompensas;
        this.conquistasRecentes = conquistasRecentes;
    }

    public static AlunoPerfilDTO from(Aluno aluno, List<String> conquistasRecentes) {
        return new AlunoPerfilDTO(aluno.getId(), aluno.getNome(), aluno.getRecompensas(), conquistasRecentes);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Recompensa> getRecompensas() {
        return recompensas;
    }

    public List<String> getConquistasRecentes() {
        return conquistasRecentes;
    }
}
