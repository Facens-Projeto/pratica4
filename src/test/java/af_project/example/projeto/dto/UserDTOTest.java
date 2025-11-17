package af_project.example.projeto.dto;

import af_project.example.projeto.entity.User;
import af_project.example.projeto.entity.User_Email;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void construtorCompletoDevePreencherCampos() {
        UserDTO dto = new UserDTO(1L, "john", "john@test.com");

        assertEquals(1L, dto.getId());
        assertEquals("john", dto.getUsername());
        assertEquals("john@test.com", dto.getEmail());
    }

    @Test
    void settersEGettersDevemFuncionarCorretamente() {
        UserDTO dto = new UserDTO();
        dto.setId(2L);
        dto.setUsername("mary");
        dto.setEmail("mary@test.com");

        assertEquals(2L, dto.getId());
        assertEquals("mary", dto.getUsername());
        assertEquals("mary@test.com", dto.getEmail());
    }

    @Test
    void fromEntityDeveCopiarCamposQuandoEmailPresente() {
        User user = new User();
        user.setId(3L);
        user.setUsername("withEmail");
        user.setEmail(new User_Email("with@test.com"));

        UserDTO dto = UserDTO.fromEntity(user);

        assertEquals(3L, dto.getId());
        assertEquals("withEmail", dto.getUsername());
        assertEquals("with@test.com", dto.getEmail());
    }

    @Test
    void fromEntityDevePermitirEmailNulo() {
        User user = new User();
        user.setId(4L);
        user.setUsername("noEmail");
        user.setEmail(null);

        UserDTO dto = UserDTO.fromEntity(user);

        assertEquals(4L, dto.getId());
        assertEquals("noEmail", dto.getUsername());
        assertNull(dto.getEmail());
    }
}
