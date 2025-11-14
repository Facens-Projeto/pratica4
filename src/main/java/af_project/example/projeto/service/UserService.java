package af_project.example.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import af_project.example.projeto.dto.UserDTO;
import af_project.example.projeto.entity.User;
import af_project.example.projeto.repository.User_Repository;
@Service
public class UserService {

    @Autowired
    private User_Repository userRepository;

    // Método para listar todos os usuários
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                    .map(UserDTO::fromEntity)
                    .collect(Collectors.toList());
    }

    // Outros métodos de lógica de negócio podem ser adicionados aqui
}