# GamesRating 🎮

**GamesRating** é um aplicativo Android de alta performance projetado para entusiastas de games. Ele permite listar, pesquisar e favoritar jogos, consumindo dados de uma API externa e persistindo informações localmente. O projeto serve como um showcase de **modern Android development**, aplicando padrões de arquitetura robustos e as melhores ferramentas do ecossistema Kotlin.

## 🏗 Arquitetura e Fluxo de Dados

O projeto foi estruturado seguindo os princípios da **Clean Architecture**, dividindo as responsabilidades em camadas bem definidas para garantir testabilidade, escalabilidade e independência de frameworks.

### 1. Camada de Apresentação (UI) - MVVM & Compose
A interface é construída inteiramente com **Jetpack Compose**, utilizando o padrão **MVVM (Model-View-ViewModel)**.
- **ViewModels:** Atuam como os "State Holders" da tela. Eles interagem com os Use Cases e expõem o estado da UI através de `StateFlow`.
- **State Management:** Utiliza o fluxo de dados unidirecional (**UDF - Unidirectional Data Flow**). Eventos fluem da UI para o ViewModel, e o estado flui do ViewModel para a UI.
- **Compose Navigation:** Gerenciamento de rotas e navegação entre telas de forma tipada e segura.

### 2. Camada de Domínio (Domain)
É o "coração" do projeto, contendo apenas código Kotlin puro (sem dependências do Android).
- **Use Cases (Interactors):** Cada ação do usuário ou lógica de negócio é encapsulada em um Use Case (ex: `GetGamesUseCase`, `ToggleFavoriteUseCase`), facilitando o reuso e os testes unitários.
- **Domain Models:** Representações simples dos dados que a UI consome.
- **Repository Interfaces:** Define o contrato de como os dados devem ser acessados, permitindo que a camada de domínio não saiba de onde os dados vêm (API ou DB).

### 3. Camada de Dados (Data)
Responsável por implementar os repositórios e gerenciar as fontes de dados (**SSOT - Single Source of Truth**).
- **Remote:** Implementação do Retrofit para chamadas à API.
- **Local (Room):** Cache local e gerenciamento de favoritos.
- **Paging 3:** Implementado para lidar com o carregamento sob demanda (infinite scroll), integrando-se diretamente com o Retrofit e o Flow.
- **Mappers:** Classes responsáveis por converter modelos de API/Entity em modelos de domínio, garantindo o desacoplamento.

---

## 🛠 Tecnologias e Stack Técnica

- **Kotlin + Coroutines/Flow:** Gerenciamento de assincronismo e fluxos de dados reativos. O `Flow` é utilizado desde a camada de banco de dados/API até a UI, permitindo atualizações em tempo real.
- **Hilt (Dependency Injection):** Redução de boilerplate e facilitação de testes através da injeção automatizada de dependências.
- **Retrofit & OkHttp:** Padrão de mercado para comunicação RESTful com interceptors para logs e autenticação.
- **Room Database:** Persistência local robusta com suporte a consultas assíncronas via Flow.
- **Paging 3:** Gerenciamento de paginação eficiente, reduzindo o consumo de memória e dados.
- **Coil:** Carregamento de imagens otimizado para Compose.

---

## 🧪 Estratégia de Testes

O projeto foi construído com foco em **Test-Driven Development (TDD)** e alta cobertura de código:

- **Testes Unitários (Local):**
    - **Use Cases:** Validação rigorosa das regras de negócio.
    - **ViewModels:** Testados com `Turbine` para garantir que os estados da UI (`Loading`, `Success`, `Error`) sejam emitidos na ordem correta.
    - **Mappers & Repositories:** Garantia de que a transformação de dados e a lógica de persistência estão corretas.
    - *Ferramentas:* `MockK`, `JUnit 4`, `Turbine`, `Robolectric`.

- **Testes de UI & Integração (Instrumentados):**
    - **Componentes Compose:** Testes isolados de componentes (ex: estrelas de avaliação, cards).
    - **Fluxos Completos:** Testes que simulam a interação do usuário, como buscar um jogo e favoritá-lo, garantindo que a integração entre UI e ViewModel funcione perfeitamente.

---

## 🚀 Melhores Práticas e Padrões

- **SOLID Principles:** Aplicados para manter o código modular e fácil de estender.
- **Dependency Inversion:** A camada de domínio depende de abstrações, não de implementações.
- **State Hoisting:** Padrão aplicado em todos os Composables para torná-los "stateless" e fáceis de testar.
- **Clean Code:** Nomenclatura clara, funções pequenas e responsabilidade única.
- **Tratamento de Exceções:** Uso de wrappers de resultado para lidar com erros de rede e banco de dados sem quebrar a experiência do usuário.

---
Desenvolvido por William Cabral
