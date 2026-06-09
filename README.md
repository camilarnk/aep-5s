# OcupaMais 🌳

Sistema acadêmico para registro e acompanhamento de demandas públicas, permitindo que cidadãos registrem solicitações e gestores acompanhem e resolvam essas demandas com transparência e rastreabilidade.

---

## 📌 Problema

Em muitas cidades, a população enfrenta dificuldades para:
- Solicitar serviços públicos (iluminação, buracos, limpeza, etc.)
- Acompanhar o andamento das solicitações
- Obter respostas claras e dentro de prazos

Isso gera falta de transparência, desigualdade no atendimento e desconfiança nas instituições públicas.

---

## 🎯 Objetivo

Um sistema simples que:
- Reduza barreiras no acesso a serviços públicos
- Aumente a transparência no atendimento
- Permita rastreabilidade das solicitações
- Organize o trabalho dos gestores

---

## 🌍 Relação com ODS

Este projeto está alinhado com a **ODS 16 – Paz, Justiça e Instituições Eficazes**, promovendo:
- Transparência
- Acesso à informação
- Eficiência no serviço público

---

## ⚙️ Tecnologias

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- H2 Database
- Maven
- HTML
- CSS

---

## 🧱 Arquitetura

O sistema segue separação de responsabilidades:

- **model** → entidades e enums do domínio
- **repository** → acesso aos dados com Spring Data JPA
- **service** → regras de negócio
- **controller** → controllers MVC
- **templates** → telas Thymeleaf
- **static** → CSS

---

## 🚀 Funcionalidades

### 👤 Cidadão
- Criar solicitação em formulário web
- Solicitação anônima ou identificada
- Consulta por protocolo
- Visualização de histórico
- Acompanhamento visual do progresso

### 🏢 Gestor
- Painel administrativo
- Listagem de solicitações
- Filtros por categoria, prioridade e localização
- Visualização detalhada de solicitações
- Atualização de status
- Registro de comentários no histórico
- Acompanhamento do progresso da solicitação

---

## 🔄 Fluxo de Status

`ABERTO → TRIAGEM → EM_EXECUCAO → RESOLVIDO → ENCERRADO`


O sistema valida automaticamente a transição entre estados.

---

## 📄 Protocolo

Cada solicitação recebe automaticamente um protocolo único.

O protocolo permite que o cidadão acompanhe o andamento da solicitação sem necessidade de autenticação.

---

## ⏱ SLA (Prazo por Prioridade)

O sistema define prazos automaticamente com base na prioridade:

| Prioridade | Prazo |
|----------|------|
| BAIXA | 14 dias |
| MÉDIA | 7 dias |
| ALTA | 3 dias |

Caso o prazo seja ultrapassado:
- o sistema sinaliza atraso
- exige justificativa do gestor

---

## 💾 Persistência

Os dados são persistidos utilizando o banco de dados H2 através do Spring Data JPA.

Durante o desenvolvimento, o banco pode ser acessado pelo console H2 para consulta e inspeção dos dados.

---

## ▶️ Como executar

Após clonar o repositório, abrir o arquivo:

`src/main/java/br/com/ocupamais/OcupamaisApplication.java`

e executar pela IDE.  

Ou ainda, executar manualmente com:
```
mvn spring-boot:run
```

A aplicação ficará disponível em:  

`http://localhost:8080`

---

## 🧠 Decisões de Projeto
- Uso de SLA baseado em prioridade para organizar atendimento
- Separação em camadas
- Uso de enum para evitar erros de entrada
- Interface web utilizando Spring MVC e Thymeleaf
- Persistência utilizando Spring Data JPA e H2 Database

---

Projeto desenvolvido para fins acadêmicos.