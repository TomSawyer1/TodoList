package com.todolist.service;

import com.todolist.dto.TodoRequest;
import com.todolist.dto.TodoResponse;

import java.util.List;

/**
 * Interface du service de gestion des tâches.
 */
public interface TodoService {

    /**
     * Crée une nouvelle tâche.
     */
    TodoResponse createTodo(TodoRequest request);

    /**
     * Récupère toutes les tâches.
     */
    List<TodoResponse> getAllTodos();

    /**
     * Récupère une tâche par son ID.
     */
    TodoResponse getTodoById(Long id);

    /**
     * Met à jour une tâche existante.
     */
    TodoResponse updateTodo(Long id, TodoRequest request);

    /**
     * Supprime une tâche par son ID.
     */
    void deleteTodo(Long id);
}
