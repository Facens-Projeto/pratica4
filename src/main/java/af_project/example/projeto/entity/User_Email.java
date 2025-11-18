package af_project.example.projeto.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor      // Necessário para o JPA
@EqualsAndHashCode      // Compara por valor — ideal para VOs
public class User_Email {

    private String emailAddress;

    public User_Email(String emailAddress) {
        if (emailAddress == null || !emailAddress.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return emailAddress;
    }
}
