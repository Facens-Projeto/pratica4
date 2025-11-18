package af_project.example.projeto.repository;

import af_project.example.projeto.entity.CursoDisponivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoDisponivelRepository extends JpaRepository<CursoDisponivel, Long> {
}
