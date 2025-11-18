package af_project.example.projeto.service;

import af_project.example.projeto.entity.Aproveitamento;
import af_project.example.projeto.entity.CursoDisponivel;
import af_project.example.projeto.repository.AproveitamentoRepository;
import af_project.example.projeto.repository.CursoDisponivelRepository;
import af_project.example.projeto.service.exception.MediaInsuficienteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecompensaService {

    public static final BigDecimal MEDIA_MINIMA = BigDecimal.valueOf(7.0);
    public static final String MENSAGEM_MEDIA_INSUFICIENTE =
            "Sua média está abaixo de 7,0. Melhore sua nota para liberar novos cursos.";

    private final AproveitamentoRepository aproveitamentoRepository;
    private final CursoDisponivelRepository cursoDisponivelRepository;

    public List<CursoDisponivel> listarCursosDisponiveis(Long alunoId) {
        boolean alunoBloqueado = aproveitamentoRepository
                .findTopByAlunoIdOrderByDataReferenciaDesc(alunoId)
                .map(Aproveitamento::getMedia)
                .map(media -> media.compareTo(MEDIA_MINIMA) < 0)
                .orElse(true);

        if (alunoBloqueado) {
            throw new MediaInsuficienteException(MENSAGEM_MEDIA_INSUFICIENTE);
        }

        return cursoDisponivelRepository.findAll();
    }
}
