# Todo List API

API REST de gestion de tâches développée avec Spring Boot 3 et SQLite.

## Stack Technique

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA** + Hibernate
- **SQLite** (base de données embarquée)
- **Jakarta Validation** (validation des entrées)
- **Swagger OpenAPI** (documentation API)
- **Maven** (gestion des dépendances)

## Architecture

```
src/main/java/com/todolist/
├── TodoListApplication.java    # Point d'entrée
├── config/                     # Configuration
│   └── OpenApiConfig.java      # Configuration Swagger
├── controller/                 # Controllers REST
│   └── TodoController.java
├── dto/                        # Data Transfer Objects
│   ├── TodoRequest.java
│   └── TodoResponse.java
├── exception/                  # Gestion des erreurs
│   ├── ErrorResponse.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── model/                      # Entités JPA
│   └── Todo.java
├── repository/                 # Repositories
│   └── TodoRepository.java
└── service/                    # Services
    ├── TodoService.java
    └── TodoServiceImpl.java
```

## Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur

## Installation et Lancement

### 1. Cloner ou créer le projet

```bash
cd TodoListJava
```

### 2. Compiler le projet

```bash
mvn clean install
```

### 3. Lancer l'application

```bash
mvn spring-boot:run
```

L'application démarre sur `http://localhost:8080`

## Endpoints API

| Méthode | URL                | Description                    |
|---------|-------------------|--------------------------------|
| POST    | `/api/todos`       | Créer une nouvelle tâche      |
| GET     | `/api/todos`       | Lister toutes les tâches      |
| GET     | `/api/todos/{id}`  | Obtenir une tâche par ID      |
| PUT     | `/api/todos/{id}`  | Modifier une tâche            |
| DELETE  | `/api/todos/{id}`  | Supprimer une tâche           |

## Documentation Swagger

Accédez à la documentation interactive :

- **Swagger UI** : http://localhost:8080/swagger-ui.html
- **OpenAPI JSON** : http://localhost:8080/api-docs

## Exemples d'utilisation

### Créer une tâche

```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Ma première tâche",
    "description": "Description de la tâche"
  }'
```

### Lister toutes les tâches

```bash
curl http://localhost:8080/api/todos
```

### Obtenir une tâche

```bash
curl http://localhost:8080/api/todos/1
```

### Modifier une tâche

```bash
curl -X PUT http://localhost:8080/api/todos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Tâche modifiée",
    "description": "Nouvelle description",
    "completed": true
  }'
```

### Supprimer une tâche

```bash
curl -X DELETE http://localhost:8080/api/todos/1
```

## Structure d'une Tâche

```json
{
  "id": 1,
  "title": "Ma tâche",
  "description": "Description de la tâche",
  "completed": false,
  "createdAt": "2026-02-09T10:30:00",
  "updatedAt": "2026-02-09T10:30:00"
}
```

## Validation des données

- **title** : obligatoire, 1-200 caractères
- **description** : optionnel, max 1000 caractères
- **completed** : optionnel, booléen (défaut: false)

## Base de données

- Fichier SQLite : `todos.db` (créé automatiquement à la racine)
- Les tables sont créées automatiquement au démarrage

## Logs

Les logs sont configurés pour afficher :
- Requêtes SQL en mode DEBUG
- Logs applicatifs au niveau INFO/DEBUG

## Licence

MIT License
