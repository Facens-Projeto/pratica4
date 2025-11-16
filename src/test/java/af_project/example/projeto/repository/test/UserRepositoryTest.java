package af_project.example.projeto.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import af_project.example.projeto.entity.User;
import af_project.example.projeto.entity.User_Email;
import af_project.example.projeto.repository.User_Repository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserRepositoryTest {
	@Autowired
    private User_Repository userRepository;

    @Test
    void testSaveAndFindUser() {
        //Cria um objeto User com um email válido
        User user = new User();
        user.setUsername("testUser");
        user.setEmail(new User_Email("test@example.com"));

         //Salva no banco de dados
         User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());  // Verifica se o ID foi gerado

        // Busca o usuário pelo ID
        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());
        assertTrue(retrievedUser.isPresent());
        assertEquals("testUser", retrievedUser.get().getUsername());
   }

}
