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
- Maven
- CLI (Interface via terminal)
- Persistência em arquivo (Serializable)

---

## 🧱 Arquitetura

O sistema segue separação de responsabilidades:

- **model** → entidades do domínio (Solicitacao, Status, Categoria, etc.)
- **service** → regras de negócio (validações, fluxo de status, SLA)
- **repository** → persistência em arquivo
- **controller** → intermediação entre interface e regras
- **view** → interface via terminal (CLI)

---

## 🚀 Funcionalidades

### 👤 Cidadão
- Criar solicitação (anônima ou identificada)
- Buscar por protocolo
- Visualizar status e histórico

### 🏢 Gestor
- Listar solicitações
- Filtrar por:
    - categoria
    - prioridade
    - localização
- Atualizar status com comentário obrigatório
- Registrar justificativa em caso de atraso

---

## 🔄 Fluxo de Status

`ABERTO → TRIAGEM → EM_EXECUCAO → RESOLVIDO → ENCERRADO`


O sistema valida automaticamente a transição entre estados.

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

Os dados são armazenados em arquivo:

`data/solicitacoes.dat`

---

## ▶️ Como executar

Após clonar o repositório, abrir o arquivo:

`src/main/java/br/com/ocupamais/Main.java`

e executar pela IDE.  

Ou ainda, executar manualmente com:
```
mvn compile
mvn exec:java
```

---

## 🧠 Decisões de Projeto
- Uso de SLA baseado em prioridade para organizar atendimento
- Separação em camadas
- Uso de enum para evitar erros de entrada
- CLI focado em simplicidade e acessibilidade
- Persistência em arquivo para facilitar testes

---

Projeto desenvolvido para fins acadêmicos.