package af_project.example.projeto.controller;

import af_project.example.projeto.dto.CursoDisponivelDTO;
import af_project.example.projeto.service.RecompensaService;
import af_project.example.projeto.service.exception.MediaInsuficienteException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recompensas")
@RequiredArgsConstructor
public class RecompensaController {

    private final RecompensaService recompensaService;

    @GetMapping("/alunos/{alunoId}/cursos")
    public ResponseEntity<?> listarCursos(@PathVariable Long alunoId) {
        try {
            List<CursoDisponivelDTO> cursos = recompensaService.listarCursosDisponiveis(alunoId)
                    .stream()
                    .map(CursoDisponivelDTO::fromEntity)
                    .toList();

            return ResponseEntity.ok(cursos);
        } catch (MediaInsuficienteException ex) {
            Map<String, String> body = new HashMap<>();
            body.put("mensagem", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }
    }
}
