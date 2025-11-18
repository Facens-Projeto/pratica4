package af_project.example.projeto.curso.controller;

import af_project.example.projeto.curso.dto.NotificacaoAgendadaDTO;
import af_project.example.projeto.curso.service.CursoMatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints relacionados a notificações dos alunos inscritos nos cursos.
 */
@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor   // <- substitui o construtor manual
public class CursoAlunoController {

    private final CursoMatriculaService cursoMatriculaService;

    @GetMapping("/{alunoId}/notificacoes")
    public ResponseEntity<List<NotificacaoAgendadaDTO>> listarNotificacoes(@PathVariable Long alunoId) {

        List<NotificacaoAgendadaDTO> notificacoes = cursoMatriculaService.listarNotificacoes(alunoId)
                .stream()
                .map(NotificacaoAgendadaDTO::from)
                .toList();

        return ResponseEntity.ok(notificacoes);
    }
}
