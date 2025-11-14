package af_project.example.projeto.controller;

import af_project.example.projeto.dto.UserDTO;
import af_project.example.projeto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();  // Chama o serviço para obter os dados
    }
}