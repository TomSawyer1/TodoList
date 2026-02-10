import React, { useState, useEffect, useCallback } from 'react';
import { getTodos, createTodo, updateTodo, deleteTodo } from '../api/todoApi';
import TodoItem from './TodoItem';
import TodoForm from './TodoForm';

const TodoApp = () => {
  const [todos, setTodos] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  // Charger les todos au montage
  const fetchTodos = useCallback(async () => {
    try {
      setIsLoading(true);
      setError(null);
      const data = await getTodos();
      setTodos(data);
    } catch (err) {
      setError('Erreur lors du chargement des tâches. Vérifiez que l\'API est en cours d\'exécution.');
      console.error('Erreur:', err);
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchTodos();
  }, [fetchTodos]);

  // Ajouter une nouvelle tâche (mise à jour optimiste)
  const handleAdd = async (title, description = '') => {
    const tempId = Date.now();
    const newTodo = { id: tempId, title, description, completed: false };
    
    // Mise à jour optimiste
    setTodos((prev) => [newTodo, ...prev]);
    
    try {
      const createdTodo = await createTodo({ title, description, completed: false });
      // Remplacer le todo temporaire par le vrai
      setTodos((prev) =>
        prev.map((todo) => (todo.id === tempId ? createdTodo : todo))
      );
    } catch (err) {
      // Annuler en cas d'erreur
      setTodos((prev) => prev.filter((todo) => todo.id !== tempId));
      setError('Erreur lors de l\'ajout de la tâche.');
      console.error('Erreur:', err);
    }
  };

  // Basculer l'état d'une tâche (mise à jour optimiste)
  const handleToggle = async (id, completed) => {
    const originalTodos = [...todos];
    const todoToUpdate = todos.find((todo) => todo.id === id);
    
    if (!todoToUpdate) return;
    
    // Mise à jour optimiste
    setTodos((prev) =>
      prev.map((todo) =>
        todo.id === id ? { ...todo, completed } : todo
      )
    );
    
    try {
      // L'API PUT nécessite title et completed
      await updateTodo(id, {
        title: todoToUpdate.title,
        description: todoToUpdate.description || '',
        completed,
      });
    } catch (err) {
      // Annuler en cas d'erreur
      setTodos(originalTodos);
      setError('Erreur lors de la mise à jour de la tâche.');
      console.error('Erreur:', err);
    }
  };

  // Supprimer une tâche (mise à jour optimiste)
  const handleDelete = async (id) => {
    const originalTodos = [...todos];
    
    // Mise à jour optimiste
    setTodos((prev) => prev.filter((todo) => todo.id !== id));
    
    try {
      await deleteTodo(id);
    } catch (err) {
      // Annuler en cas d'erreur
      setTodos(originalTodos);
      setError('Erreur lors de la suppression de la tâche.');
      console.error('Erreur:', err);
    }
  };

  // Compteurs
  const completedCount = todos.filter((todo) => todo.completed).length;
  const totalCount = todos.length;

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 py-8 px-4">
      <div className="max-w-2xl mx-auto">
        {/* En-tête */}
        <header className="text-center mb-8">
          <h1 className="text-4xl font-bold text-gray-800 mb-2">
            Ma Liste de Tâches
          </h1>
          <p className="text-gray-600">
            {totalCount > 0
              ? `${completedCount} / ${totalCount} tâches terminées`
              : 'Aucune tâche pour le moment'}
          </p>
        </header>

        {/* Formulaire d'ajout */}
        <TodoForm onAdd={handleAdd} isLoading={isLoading} />

        {/* Message d'erreur */}
        {error && (
          <div className="mb-4 p-4 bg-red-100 border border-red-300 text-red-700 rounded-lg">
            {error}
            <button
              onClick={() => setError(null)}
              className="ml-2 text-red-900 font-bold hover:text-red-700"
            >
              ×
            </button>
          </div>
        )}

        {/* État de chargement */}
        {isLoading && (
          <div className="text-center py-8">
            <div className="inline-block animate-spin rounded-full h-8 w-8 border-4 border-blue-500 border-t-transparent"></div>
            <p className="mt-2 text-gray-600">Chargement des tâches...</p>
          </div>
        )}

        {/* Liste des tâches */}
        {!isLoading && todos.length === 0 && !error && (
          <div className="text-center py-12 bg-white rounded-lg shadow-sm">
            <p className="text-gray-500 text-lg">
              Aucune tâche à afficher.
            </p>
            <p className="text-gray-400 mt-2">
              Commencez par ajouter une nouvelle tâche ci-dessus.
            </p>
          </div>
        )}

        {!isLoading && todos.length > 0 && (
          <ul className="space-y-3">
            {todos.map((todo) => (
              <TodoItem
                key={todo.id}
                todo={todo}
                onToggle={handleToggle}
                onDelete={handleDelete}
              />
            ))}
          </ul>
        )}

        {/* Pied de page */}
        <footer className="mt-8 text-center text-gray-500 text-sm">
          <p>TodoList React + Tailwind CSS + Spring Boot</p>
        </footer>
      </div>
    </div>
  );
};

export default TodoApp;
