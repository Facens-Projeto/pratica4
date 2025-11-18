package af_project.example.projeto.dto;

import af_project.example.projeto.entity.CursoDisponivel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoDisponivelDTO {

    private Long id;
    private String nome;
    private String descricao;

    public static CursoDisponivelDTO fromEntity(CursoDisponivel entity) {
        return CursoDisponivelDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .build();
    }
}
