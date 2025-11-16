package af_project.example.projeto.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import af_project.example.projeto.dto.AlunoPerfilDTO;
import af_project.example.projeto.service.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping("/{alunoId}/perfil")
    public AlunoPerfilDTO perfil(@PathVariable Long alunoId,
                                 @RequestParam(name = "conquistas", defaultValue = "3") int limiteConquistas) {
        return alunoService.perfil(alunoId, limiteConquistas);
    }
}
