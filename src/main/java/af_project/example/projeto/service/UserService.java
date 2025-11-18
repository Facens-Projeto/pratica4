package af_project.example.projeto.service;

import af_project.example.projeto.dto.UserDTO;
import af_project.example.projeto.entity.User;
import af_project.example.projeto.repository.User_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor   // ⬅ injeção via construtor
public class UserService {

    private final User_Repository userRepository;  // ⬅ dependência imutável

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::fromEntity)
                .toList();  // ⬅ mais limpo
    }
}
