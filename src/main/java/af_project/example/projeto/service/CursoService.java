package af_project.example.projeto.service;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;

import af_project.example.projeto.entity.Aluno;
import af_project.example.projeto.entity.Curso;
import af_project.example.projeto.entity.Recompensa;
import af_project.example.projeto.service.model.Aproveitamento;
import af_project.example.projeto.service.model.CursoConclusaoEvento;

@Service
public class CursoService {

    public Optional<Recompensa> registrarConclusao(Curso curso, Aluno aluno, CursoConclusaoEvento evento) {
        Aproveitamento aproveitamento = Aproveitamento.aPartirDo(evento);

        if (!evento.indicaConclusao()) {
            return Optional.empty();
        }

        curso.concluir(aproveitamento);

        aluno.registrarConquista("Concluiu " + curso.getNome() + " com aproveitamento de " + percentual(aproveitamento));

        if (aproveitamento.isSuficiente() && curso.isConcluido()) {
            Recompensa recompensa = Recompensa.vagasExtra(curso.getNome(), 3);
            aluno.adicionarRecompensa(recompensa);
            aluno.registrarConquista("Ganhou nova recompensa: " + recompensa.getTitulo());
            return Optional.of(recompensa);
        }

        return Optional.empty();
    }

    private String percentual(Aproveitamento aproveitamento) {
        NumberFormat format = NumberFormat.getPercentInstance(new Locale("pt", "BR"));
        format.setMaximumFractionDigits(0);
        return format.format(aproveitamento.getPercentual());
    }
}
