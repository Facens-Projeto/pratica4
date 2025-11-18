package af_project.example.projeto.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Projeto DevOps API",
                version = "v1",
                description = "API do projeto DevOps seguindo Clean Architecture e DDD"
        )
)
public class SwaggerConfig {
    // Se quiser, depois dá pra adicionar configurações avançadas aqui (security, servers, etc.)
}
