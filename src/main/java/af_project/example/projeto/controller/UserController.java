package af_project.example.projeto.controller;

import af_project.example.projeto.dto.UserDTO;
import af_project.example.projeto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
@RequiredArgsConstructor          // ⬅ elimina o @Autowired e gera o construtor correto
public class UserController {

    private final UserService userService;   // ⬅ dependência final (boa prática)

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }
}
