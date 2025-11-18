package af_project.example.projeto.controller;

import af_project.example.projeto.dto.AlunoPerfilDTO;
import af_project.example.projeto.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor     // ⬅ substitui o construtor manual
public class AlunoController {

    private final AlunoService alunoService;   // ⬅ dependência imutável

    @GetMapping("/{alunoId}/perfil")
    public AlunoPerfilDTO perfil(@PathVariable Long alunoId,
                                 @RequestParam(name = "conquistas", defaultValue = "3") int limiteConquistas) {
        return alunoService.perfil(alunoId, limiteConquistas);
    }
}
