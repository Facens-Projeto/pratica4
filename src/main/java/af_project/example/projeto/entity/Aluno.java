package af_project.example.projeto.entity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entidade de aluno mantida em memória para expor o perfil.
 */
public class Aluno {

    private final Long id;
    private final String nome;
    private final List<Recompensa> recompensas = new ArrayList<>();
    private final Deque<String> conquistas = new ArrayDeque<>();

    public Aluno(Long id, String nome) {
        this.id = id;
        this.nome = nome;
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

    public void adicionarRecompensa(Recompensa recompensa) {
        this.recompensas.add(recompensa);
    }

    public void registrarConquista(String descricao) {
        conquistas.addFirst(descricao);
        while (conquistas.size() > 10) {
            conquistas.removeLast();
        }
    }

    public List<String> conquistasRecentes(int limite) {
        return conquistas.stream()
                .limit(Math.max(0, limite))
                .collect(Collectors.toList());
    }
}
