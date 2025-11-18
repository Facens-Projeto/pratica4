package af_project.example.projeto.service;

import af_project.example.projeto.dto.AlunoPerfilDTO;
import af_project.example.projeto.entity.Aluno;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AlunoService {

    // "Banco de dados" em memória — imutável como referência
    private final Map<Long, Aluno> alunos = new ConcurrentHashMap<>();

    public Aluno salvar(Aluno aluno) {
        alunos.put(aluno.getId(), aluno);
        return aluno;
    }

    public Aluno obterPorId(Long id) {
        Aluno aluno = alunos.get(id);
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não encontrado: " + id);
        }
        return aluno;
    }

    public AlunoPerfilDTO perfil(Long alunoId, int limiteConquistas) {
        Aluno aluno = obterPorId(alunoId);
        List<String> conquistas = aluno.conquistasRecentes(limiteConquistas);
        return AlunoPerfilDTO.from(aluno, conquistas);
    }
}
