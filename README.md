# OcupaMais 🌳

Sistema acadêmico para registro e acompanhamento de demandas públicas, permitindo que cidadãos registrem solicitações e gestores acompanhem e resolvam essas demandas com transparência.

## 📌 Objetivo

Reduzir barreiras no acesso a serviços públicos, aumentando a transparência, rastreabilidade e eficiência no atendimento das solicitações da população.

## ⚙️ Tecnologias

* Java
* Maven
* CLI (Interface via terminal)
* Persistência em arquivo (Serializable)

## 🧱 Arquitetura

* **model** → entidades do sistema
* **service** → regras de negócio
* **repository** → persistência de dados
* **controller** → intermediação entre entrada e regras
* **view** → interface via terminal

## 🚀 Funcionalidades previstas

* Cadastro de solicitações
* Listagem de demandas
* Busca por protocolo
* Atualização de status
* Histórico de movimentações

## 📁 Estrutura do projeto

```
src/main/java/br/com/ocupamais/
├── model/
├── service/
├── repository/
├── controller/
└── view/
```