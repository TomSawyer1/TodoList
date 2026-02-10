# TodoList React + Tailwind CSS

Interface utilisateur moderne pour gérer une liste de tâches, développée en React avec Tailwind CSS.

## Aperçu

Cette application frontend consomme l'API REST **TodoListJava** et permet de :
- Afficher toutes les tâches
- Ajouter une nouvelle tâche (avec titre et description)
- Marquer une tâche comme terminée
- Supprimer une tâche

## Technologies utilisées

| Technologie | Version | Description |
|-------------|---------|-------------|
| React | 18.x | Bibliothèque JavaScript pour construire l'interface |
| Tailwind CSS | 3.4.x | Framework CSS utilitaire pour le design |
| Axios | 1.x | Client HTTP pour les appels API |

## Structure du projet

```
src/
├── api/
│   └── todoApi.js          # Service pour les appels API REST
├── components/
│   ├── TodoApp.js          # Composant principal (logique + état)
│   ├── TodoForm.js         # Formulaire d'ajout de tâche
│   └── TodoItem.js         # Affichage d'une tâche individuelle
├── App.js                  # Point d'entrée de l'application
├── index.js                # Bootstrap React
└── index.css               # Styles Tailwind CSS
```

## Prérequis

- **Node.js** (version 16 ou supérieure)
- **npm** (inclus avec Node.js)
- **API TodoListJava** en cours d'exécution sur le port 8080

## Installation

1. **Cloner le projet** (si ce n'est pas déjà fait)

2. **Installer les dépendances**
```bash
npm install
```

## Démarrage

### 1. Démarrer l'API backend (TodoListJava)

```bash
cd ../TodoListJava
./mvnw spring-boot:run
```

L'API sera accessible sur `http://localhost:8080`

### 2. Démarrer l'application React

```bash
npm start
```

L'application sera accessible sur `http://localhost:3000`

## Scripts disponibles

| Commande | Description |
|----------|-------------|
| `npm start` | Lance le serveur de développement |
| `npm run build` | Compile l'application pour la production |
| `npm test` | Lance les tests |

## Fonctionnalités détaillées

### Affichage des tâches
- Chargement automatique au démarrage
- Spinner de chargement pendant la récupération
- Message d'erreur si l'API n'est pas accessible
- Compteur de tâches terminées

### Ajout de tâche
- Champ titre (obligatoire)
- Champ description (optionnel, extensible)
- Mise à jour optimiste (affichage immédiat avant confirmation serveur)

### Modification de tâche
- Checkbox pour marquer comme terminée
- Style barré pour les tâches terminées
- Mise à jour optimiste avec rollback en cas d'erreur

### Suppression de tâche
- Bouton de suppression sur chaque tâche
- Mise à jour optimiste avec rollback en cas d'erreur

## Communication avec l'API

### Endpoints utilisés

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/api/todos` | Récupère toutes les tâches |
| `POST` | `/api/todos` | Crée une nouvelle tâche |
| `PUT` | `/api/todos/:id` | Met à jour une tâche |
| `DELETE` | `/api/todos/:id` | Supprime une tâche |

### Format des données

**Requête de création/mise à jour :**
```json
{
  "title": "Ma tâche",
  "description": "Description optionnelle",
  "completed": false
}
```

**Réponse de l'API :**
```json
{
  "id": 1,
  "title": "Ma tâche",
  "description": "Description optionnelle",
  "completed": false,
  "createdAt": "2026-02-10T21:30:00",
  "updatedAt": "2026-02-10T21:30:00"
}
```

## Configuration

### URL de l'API

L'URL de base de l'API est configurée dans `src/api/todoApi.js` :

```javascript
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});
```

Pour modifier le port ou l'hôte, modifiez cette valeur.

## Design et UX

- **Design minimaliste** avec un dégradé bleu/indigo en arrière-plan
- **Interface responsive** adaptée aux mobiles et tablettes
- **Conteneur centré** avec une largeur maximale de 672px
- **Animations** de transition sur les éléments interactifs
- **Feedback visuel** : spinner de chargement, messages d'erreur

## Gestion des erreurs

L'application gère les cas suivants :
- API non disponible → Message d'erreur avec possibilité de fermer
- Échec de création → Annulation de l'ajout optimiste
- Échec de mise à jour → Rollback à l'état précédent
- Échec de suppression → Restauration de la tâche

## Build pour la production

```bash
npm run build
```

Les fichiers optimisés seront générés dans le dossier `build/`.

## Auteur

Projet créé dans le cadre d'un exercice de développement frontend.

## Licence

Ce projet est libre d'utilisation à des fins éducatives.
