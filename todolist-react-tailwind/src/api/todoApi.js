import axios from 'axios';

// Configuration de base d'Axios
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Récupérer toutes les tâches
export const getTodos = async () => {
  const response = await api.get('/todos');
  return response.data;
};

// Créer une nouvelle tâche
export const createTodo = async (todo) => {
  const response = await api.post('/todos', todo);
  return response.data;
};

// Mettre à jour une tâche (marquer comme terminée)
export const updateTodo = async (id, todoData) => {
  const response = await api.put(`/todos/${id}`, todoData);
  return response.data;
};

// Supprimer une tâche
export const deleteTodo = async (id) => {
  await api.delete(`/todos/${id}`);
};

export default api;
