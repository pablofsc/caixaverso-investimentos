# API Caixaverso Investimentos

Feito por Pablo Felipe Santos Carneiro

Sistema de simulação e gestão de investimentos desenvolvido com Quarkus, oferecendo análise de risco, recomendação de produtos financeiros e telemetria operacional.

## Modelagem SQLite

O sistema utiliza SQLite para armazenar os dados. O banco é criado automaticamente quando a aplicação é iniciada pela primeira vez.

Na inicialização, o sistema popula o banco com:
- Usuário administrador padrão
- Produtos financeiros disponíveis
- Clientes de exemplo para testes
- Investimentos de demonstração para os clientes de teste

<img width="714" height="602" alt="banco" src="https://github.com/user-attachments/assets/09f987dc-ac28-490d-a38b-5709a3327bf8" />

Optei por usar SQLite com FKs ativas (SQLite não tem FKs por padrão) para representar o domínio.
A tabela de clientes contém os clientes de fato e a tabela de usuários é usada pelo JWT, armazenando as senhas em forma de hash cripografada.

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

O projeto conta com 90% de cobertura de instructions no Jacoco.

<img width="1114" height="462" alt="jacoco" src="https://github.com/user-attachments/assets/5d051092-7b72-4281-8646-2e9391c72d44" />

Para executar todos os testes unitários:

```bash
mvn test
```
Para gerar o relatório de cobertura do Jacoco:

```bash
mvn jacoco:report
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

```bash
mvn package
```

```bash
docker build -f src/main/docker/Dockerfile.jvm -t caixaverso-investimentos:latest .
```

```bash
docker run -i --rm -p 8080:8080 caixaverso-investimentos:latest
```

## Endpoints e evidências

### `POST /auth/login` - Realiza login e retorna token JWT

<img width="843" height="410" alt="login" src="https://github.com/user-attachments/assets/bd21438f-4cdb-46ad-8512-16f9ab893903" />

### `POST /auth/registrar` - Registra novo usuário

<img width="842" height="377" alt="registrar" src="https://github.com/user-attachments/assets/91d05de6-9cdb-4217-b815-b718b7375e3a" />

### `POST /simular-investimento` - Simula um investimento, escolhendo um produto apropriado para o perfil do cliente

<img width="849" height="659" alt="simular-investimento-cliente1" src="https://github.com/user-attachments/assets/f4140d07-90b5-45f8-a6cc-0623f578fb46" />

### `GET /simulacoes` - Histórico de simulações (ADMIN)

<img width="841" height="776" alt="simulacoes" src="https://github.com/user-attachments/assets/248306c2-24dd-4bd4-a67e-add7dada0da5" />

### `GET /simulacoes/por-produto-dia` - Histórico de simulações (ADMIN)

<img width="841" height="537" alt="por-produto-dia" src="https://github.com/user-attachments/assets/1fe9d80c-1b3b-47b6-8402-0061e95ff1f2" />

### `GET /telemetria` - Obtém métricas de uso dos endpoints

<img width="844" height="762" alt="telemetria" src="https://github.com/user-attachments/assets/ba864269-f902-4025-94d0-664ac51e6648" />

### `GET /perfil-risco` - Calcula perfil de risco do cliente
- 
<img width="843" height="518" alt="perfil-risco" src="https://github.com/user-attachments/assets/b70cea7a-d4e2-465f-a989-9ab590889246" />

### `GET /produtos-recomendados` - Obtém produtos recomendados baseado no perfil

<img width="842" height="772" alt="produtos-recomendados" src="https://github.com/user-attachments/assets/d9a4fd37-be8b-466f-8797-53464031e6b9" />

### `GET /investimentos/{clienteId}` - Lista investimentos de um cliente

<img width="839" height="770" alt="investimentos" src="https://github.com/user-attachments/assets/a5836378-837c-46ed-ba5f-8c6b0f07a2c4" />
