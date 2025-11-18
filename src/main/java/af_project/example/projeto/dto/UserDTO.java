package af_project.example.projeto.dto;

import af_project.example.projeto.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor      // construtor vazio
@AllArgsConstructor      // construtor completo
public class UserDTO {

    private Long id;
    private String username;
    private String email;

    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail() != null ? user.getEmail().getEmailAddress() : null
        );
    }
}
