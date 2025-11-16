package af_project.example.projeto.curso.controller;

import af_project.example.projeto.curso.dto.NotificacaoAgendadaDTO;
import af_project.example.projeto.curso.service.CursoMatriculaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints relacionados a notificações dos alunos inscritos nos cursos.
 */
@RestController
@RequestMapping("/alunos")
public class CursoAlunoController {

    private final CursoMatriculaService cursoMatriculaService;

    public CursoAlunoController(CursoMatriculaService cursoMatriculaService) {
        this.cursoMatriculaService = cursoMatriculaService;
    }

    @GetMapping("/{alunoId}/notificacoes")
    public ResponseEntity<List<NotificacaoAgendadaDTO>> listarNotificacoes(@PathVariable Long alunoId) {
        List<NotificacaoAgendadaDTO> notificacoes = cursoMatriculaService.listarNotificacoes(alunoId)
                .stream()
                .map(NotificacaoAgendadaDTO::from)
                .toList();
        return ResponseEntity.ok(notificacoes);
    }
}
