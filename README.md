# API Caixaverso Investimentos

Sistema de simulação e gestão de investimentos desenvolvido com Quarkus, oferecendo análise de risco, recomendação de produtos financeiros e telemetria operacional.

## Sobre o Projeto

A aplicação é uma API completa que permite:

- Simulação de investimentos com análise de rentabilidade
- Recomendação personalizada de produtos financeiros
- Análise de perfil de risco de clientes
- Histórico de simulações realizadas
- Telemetria e monitoramento do sistema

## Tecnologias Utilizadas

- **Java 21**
- **Quarkus 3.29.3**
- **Hibernate ORM com Panache**
- **SQLite**
- **SmallRye JWT**
- **OpenAPI/Swagger**
- **JUnit**
- **JaCoCo**
- **Lombok**
- **Docker**

## Autenticação e Segurança

Optei pelo uso de JWT (JSON Web Token) para autenticação e autorização de usuários. O fluxo funciona assim:

### Como Funciona o JWT

1. **Login**: O usuário envia email e senha para o endpoint `/auth/login`
2. **Validação**: O sistema verifica as credenciais no banco de dados (a senha é salva como hash)
3. **Geração do Token**: Se válido, um token JWT é gerado contendo:
   - Email do usuário
   - Nome completo
   - ID do usuário
   - Role (perfil de acesso)
   - Data de expiração (24 horas)
4. **Uso do Token**: O cliente envia o token no header `Authorization: Bearer {token}` em cada requisição
5. **Validação**: O sistema valida a assinatura e expiração do token antes de processar a requisição

O token é assinado digitalmente usando chaves RSA (pública e privada)

### Roles Existentes

O sistema possui dois perfis de acesso:

- **USER**: Usuário comum com acesso a funcionalidades básicas
  - Pode realizar simulações de investimento
  - Pode consultar recomendações de produtos
  - Pode visualizar seu perfil de risco
  - Pode acessar histórico de suas próprias simulações

- **ADMIN**: Administrador com acesso total ao sistema
  - Todas as permissões de USER
  - Pode visualizar investimentos de todos os clientes
  - Pode acessar dados de telemetria do sistema
  - Pode gerenciar produtos financeiros

Este é o perfil admin é padrão:

- Email: `admin@caixaverso.com.br`
- Senha: `admin123`

## Documentação Swagger

A API possui documentação interativa completa através do Swagger UI, que permite:

- Visualizar todos os endpoints disponíveis
- Conhecer os parâmetros e formatos de requisição
- Testar as operações diretamente pelo navegador
- Ver exemplos de respostas de sucesso e erro

Para acessar o Swagger:

1. Inicie a aplicação em modo de desenvolvimento
2. Acesse: http://localhost:8080/q/swagger-ui
3. Utilize o botão "Authorize" para inserir o token JWT obtido no login
4. Explore e teste os endpoints disponíveis

A documentação inclui descrições detalhadas, códigos de resposta HTTP, exemplos de payload e informações sobre autenticação necessária para cada endpoint.

## Executando o Projeto

### Modo Desenvolvimento

Para iniciar a aplicação em modo de desenvolvimento com hot reload:

```bash
mvn quarkus:dev
```

A aplicação estará disponível em http://localhost:8080

Recursos adicionais no modo dev:
- Dev UI: http://localhost:8080/q/dev
- Swagger UI: http://localhost:8080/q/swagger-ui
- Health Check: http://localhost:8080/q/health

## Testes Unitários

O projeto possui uma cobertura completa de testes unitários, incluindo:

- Testes de serviços (lógica de negócio)
- Testes de recursos (endpoints da API)
- Testes de entidades e modelos
- Testes de filtros e validações
- Testes de helpers e utilitários

### Executando os Testes

Para executar todos os testes unitários:

```bash
mvn test
```

### Relatório de Cobertura

Para gerar o relatório de cobertura de código com JaCoCo:

```bash
mvn verify
```

O relatório será gerado em `target/site/jacoco/index.html` e pode ser visualizado em qualquer navegador.

O projeto utiliza:
- **JUnit 5**: Framework moderno de testes
- **Mockito**: Criação de mocks e stubs
- **Quarkus Test Security**: Testes de segurança e autorização (criptografia da senha JWT)

## Docker

O projeto inclui suporte completo para containerização com Docker.

### Construindo a Imagem

```bash
mvn package
```

```bash
docker build -f src/main/docker/Dockerfile.jvm -t caixaverso-investimentos:latest .
```

```bash
docker run -i --rm -p 8080:8080 caixaverso-investimentos:latest
```

## Endpoints Principais

### Autenticação
- `POST /auth/login` - Realiza login e retorna token JWT
- `POST /auth/registrar` - Registra novo usuário

### Simulações
- `POST /simulacoes` - Cria nova simulação de investimento
- `GET /simulacoes/historico` - Lista histórico de simulações

### Produtos
- `GET /produtos/recomendados` - Obtém produtos recomendados baseado no perfil

### Perfil de Risco
- `POST /perfil-risco` - Calcula perfil de risco do cliente

### Investimentos (ADMIN)
- `GET /investimentos/{clienteId}` - Lista investimentos de um cliente

### Telemetria (ADMIN)
- `GET /telemetria` - Obtém métricas de uso dos endpoints

## Banco de Dados

O sistema utiliza SQLite para armazenar os dados. O banco é criado automaticamente quando a aplicação é iniciada pela primeira vez.

Na inicialização, o sistema popula o banco com:
- Usuário administrador padrão
- Produtos financeiros disponíveis
- Clientes de exemplo para testes
- Investimentos de demonstração para os clientes de teste

