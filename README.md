# DevOps
User Story: Como aluno assinante quero desbloquear novos cursos ao atingir média 7,0 para continuar evoluindo na plataforma sem limitações

BDDs:
Cenario 1 Dado que sou um aluno que concluiu todas as atividades de um curso 
 Quando minha nota final for igual ou superior a 7,0
 Então devo desbloquear novos cursos para continuar minha evolução
 E a conquista deve ser registrada no meu perfil na plataforma

Cenario 2 Dado que sou um aluno assinante
 Quando eu escolher e me matricular em um curso
 Então devo ser notificado que preciso iniciar os estudos em até 7 dias
 E devo concluir o curso em no máximo 1 ano

cenario 3 Dado que sou um aluno autenticado na plataforma como assinante ativo
 E já concluí um curso com média abaixo de 7,0
 Quando eu tento acessar novos cursos
 Então o sistema deve bloquear a liberação dos próximos cursos
 E devo receber uma mensagem explicando que preciso melhorar a nota para desbloquear

