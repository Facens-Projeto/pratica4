package af_project.example.projeto.curso.repository;

import af_project.example.projeto.curso.domain.CursoMatricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoMatriculaRepository extends JpaRepository<CursoMatricula, Long> {

    List<CursoMatricula> findByAlunoId(Long alunoId);
}
