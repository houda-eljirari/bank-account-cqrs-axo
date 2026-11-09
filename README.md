# Bank Account Management – CQRS & Event Sourcing

## 1. Présentation du projet

**Bank Account Management** est une application de gestion de comptes bancaires développée avec **Spring Boot** et **Axon Framework**.

L'objectif du projet est de mettre en pratique les architectures **CQRS (Command Query Responsibility Segregation)** et **Event Sourcing**, en séparant les opérations de modification des données des opérations de consultation.

L'application permet progressivement de gérer les comptes bancaires, les opérations de crédit et de débit, ainsi que le statut des comptes.

## 2. Technologies utilisées

| Technologie | Utilisation |
|---|---|
| Java 17 | Langage de programmation |
| Spring Boot 3.4.12 | Framework backend |
| Axon Framework 4.10.3 | Implémentation de CQRS et Event Sourcing |
| Spring Data JPA | Persistance des données |
| PostgreSQL 16 | Base de données relationnelle |
| Docker Compose | Gestion des conteneurs |
| pgAdmin | Administration de PostgreSQL |
| Maven | Gestion du projet et des dépendances |
| Springdoc OpenAPI | Documentation des API REST (à vérifier) |

## 3. Architecture du projet

Le projet adopte une architecture organisée autour des responsabilités CQRS.

```text
src/main/java/ma/tp/bank/
│
├── aggregates/
│   └── AccountAggregate.java
│
├── commands/
│   ├── AddAccountCommand.java
│   ├── CreditAccountCommand.java
│   ├── DebitAccountCommand.java
│   └── UpdateAccountStatusCommand.java
│
├── events/
│   ├── AccountCreatedEvent.java
│   ├── AccountCreditedEvent.java
│   ├── AccountDebitedEvent.java
│   ├── AccountStatusUpdatedEvent.java
│   └── AccountActivatedEvent.java
│
├── queries/
│
├── controllers/
│   ├── command/
│   │   └── AccountCommandController.java
│   └── query/
│
├── dto/
│   ├── CreateAccountRequest.java
│   ├── AmountRequest.java
│   └── UpdateAccountStatusRequest.java
│
├── entities/
│   ├── Account.java
│   └── AccountOperation.java
│
├── repositories/
│   ├── AccountRepository.java
│   └── AccountOperationRepository.java
│
└── handlers/
    ├── event/
    │   └── AccountEventHandler.java
    └── query/
```

## 4. Fonctionnalités développées

### 4.1. Gestion des commandes (Command Side)

Les commandes représentent les actions qui modifient l'état d'un compte bancaire.

Les commandes actuellement implémentées sont :

- `AddAccountCommand` : création d'un compte.
- `CreditAccountCommand` : crédit d'un compte.
- `DebitAccountCommand` : débit d'un compte.
- `UpdateAccountStatusCommand` : modification du statut d'un compte.

L'`AccountAggregate` traite ces commandes et produit les événements correspondants.

Des règles métier sont également appliquées :
- Le solde initial doit être positif ou nul.
- Le montant d'un crédit ou d'un débit doit être strictement positif.
- Un compte doit être actif pour effectuer une opération.
- Un débit ne peut pas dépasser le solde disponible.

### 4.2. Gestion des événements (Event Side)

Les événements représentent les changements métier produits par les commandes.

Événements créés :

- `AccountCreatedEvent`
- `AccountCreditedEvent`
- `AccountDebitedEvent`
- `AccountStatusUpdatedEvent`

Un `AccountEventHandler` écoute ces événements et met à jour les données de consultation stockées dans PostgreSQL.

### 4.3. Persistance des données

Deux entités JPA ont été créées :

- `Account` : stocke les informations du compte, notamment son identifiant, son solde et son statut.
- `AccountOperation` : conserve les informations des opérations de crédit et de débit.

Les repositories associés permettent d'accéder aux données PostgreSQL.

## 5. API REST – Commandes

Un premier contrôleur REST a été développé : `AccountCommandController`.

Base URL :

```text
http://localhost:8081/api/accounts
```

| Méthode | Endpoint | Description |
|---|---|---|
| POST | `/api/accounts` | Créer un compte |
| POST | `/api/accounts/{id}/credit` | Créditer un compte |
| POST | `/api/accounts/{id}/debit` | Débiter un compte |
| PUT | `/api/accounts/{id}/status` | Modifier le statut d'un compte |

Les données sont reçues au format JSON et validées à l'aide des annotations Jakarta Validation.

## 6. Configuration de l'environnement

### Prérequis

- Java 17
- Docker Desktop
- Git
- IntelliJ IDEA ou un autre IDE Java

### Lancement de PostgreSQL avec Docker Compose

À la racine du projet :

```powershell
docker compose up -d
```

Vérifier l'état des conteneurs :

```powershell
docker compose ps
```

Configuration actuelle de PostgreSQL :

| Paramètre | Valeur |
|---|---|
| Base de données | `bankdb` |
| Utilisateur | `bankuser` |
| Mot de passe | `bankpass` |
| Port | `5432` |

pgAdmin est accessible à l'adresse :

```text
http://localhost:5050
```

### Lancement de l'application Spring Boot

```powershell
.\mvnw.cmd spring-boot:run
```

L'application utilise actuellement le port `8081`.

## 7. État d'avancement

<badge color="success">Compilation réussie</badge>

La compilation du projet avec Maven a été vérifiée avec succès après la création des DTO et du contrôleur de commandes.

| Composant | État |
|---|---|
| Configuration Spring Boot et Maven | Terminé |
| Structure des packages | Terminé |
| Commands et Events | Terminé |
| Account Aggregate | Terminé |
| Entités JPA et repositories | Terminé |
| PostgreSQL avec Docker Compose | Opérationnel |
| Event Handler et modèle de lecture | Implémentés |
| DTO et Command Controller | Implémentés et compilés |
| Query Handlers | À développer |
| Query Controller | À développer |
| Documentation Swagger et tests API | À vérifier |
| Event Store persistant | À configurer |
| Notifications temps réel avec SSE | À développer |
| Intégration Axon Server | À développer |
| Tests finaux et documentation | À réaliser |

**Remarque importante :** PostgreSQL est actuellement utilisé pour stocker les données du modèle de lecture. La configuration d'un Event Store Axon persistant reste à réaliser. L'architecture Event Sourcing n'est donc pas encore entièrement finalisée.

## 8. Prochaines étapes

Les prochaines étapes du développement sont :

1. Implémenter les requêtes CQRS pour consulter les comptes et leur historique.
2. Développer le contrôleur REST dédié aux consultations.
3. Documenter et tester les API avec Swagger.
4. Configurer la persistance des événements avec Axon Event Store.
5. Ajouter les notifications en temps réel avec Server-Sent Events (SSE).
6. Intégrer Axon Server avec Docker Compose.
7. Réaliser les tests fonctionnels et finaliser la documentation.

## 9. Objectif final

L'objectif est de disposer d'une application bancaire démontrant concrètement :

- La séparation entre commandes et requêtes avec CQRS.
- La représentation des changements métier sous forme d'événements.
- La reconstruction de l'état des agrégats à partir des événements.
- La persistance des événements.
- La synchronisation des modèles de lecture.
- La communication en temps réel avec SSE.
- L'utilisation d'Axon Server pour la gestion des événements et des messages.

