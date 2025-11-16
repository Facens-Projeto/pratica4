package af_project.example.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import af_project.example.projeto.entity.Aproveitamento;

@Repository
public interface AproveitamentoRepository extends JpaRepository<Aproveitamento, Long> {

    Optional<Aproveitamento> findTopByAlunoIdOrderByDataReferenciaDesc(Long alunoId);
}
