package af_project.example.projeto.entity;

import java.util.Objects;
import java.util.Optional;

import af_project.example.projeto.service.model.Aproveitamento;

/**
 * Representa um curso que pode ser concluído por um aluno.
 */
public class Curso {

    private final Long id;
    private final String nome;
    private boolean concluido;
    private Aproveitamento aproveitamento;

    public Curso(Long id, String nome) {
        this.id = id;
        this.nome = nome;
        this.concluido = false;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public Optional<Aproveitamento> getAproveitamento() {
        return Optional.ofNullable(aproveitamento);
    }

    public void concluir(Aproveitamento aproveitamento) {
        this.concluido = true;
        this.aproveitamento = Objects.requireNonNull(aproveitamento, "aproveitamento");
    }
}
