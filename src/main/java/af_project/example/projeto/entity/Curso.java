package af_project.example.projeto.entity;

import af_project.example.projeto.service.model.Aproveitamento;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;

/**
 * Representa um curso que pode ser concluído por um aluno.
 */
@Getter
@RequiredArgsConstructor   // Gera construtor para id e nome (atributos final)
public class Curso {

    private final Long id;
    private final String nome;

    private boolean concluido;
    private Aproveitamento aproveitamento;

    public Optional<Aproveitamento> getAproveitamento() {
        return Optional.ofNullable(aproveitamento);
    }

    public void concluir(Aproveitamento aproveitamento) {
        this.concluido = true;
        this.aproveitamento = Objects.requireNonNull(aproveitamento, "aproveitamento");
    }
}
