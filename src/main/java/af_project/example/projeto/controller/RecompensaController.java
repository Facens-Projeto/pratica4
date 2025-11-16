package af_project.example.projeto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import af_project.example.projeto.entity.CursoDisponivel;
import af_project.example.projeto.service.RecompensaService;
import af_project.example.projeto.service.exception.MediaInsuficienteException;

@RestController
@RequestMapping("/recompensas")
public class RecompensaController {

    private final RecompensaService recompensaService;

    public RecompensaController(RecompensaService recompensaService) {
        this.recompensaService = recompensaService;
    }

    @GetMapping("/alunos/{alunoId}/cursos")
    public ResponseEntity<?> listarCursos(@PathVariable Long alunoId) {
        try {
            List<CursoDisponivel> cursos = recompensaService.listarCursosDisponiveis(alunoId);
            return ResponseEntity.ok(cursos);
        } catch (MediaInsuficienteException ex) {
            Map<String, String> body = new HashMap<>();
            body.put("mensagem", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }
    }
}
