package af_project.example.projeto.repository;

import java.util.Collections;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import af_project.example.projeto.entity.CursoDisponivel;

@Repository
public interface CursoDisponivelRepository extends JpaRepository<CursoDisponivel, Long> {

    default List<CursoDisponivel> listarCursosDisponiveis(boolean alunoBloqueado) {
        if (alunoBloqueado) {
            return Collections.emptyList();
        }
        return findAll();
    }
}
