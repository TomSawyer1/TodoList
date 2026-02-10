package com.todolist.service;

import com.todolist.dto.TodoRequest;
import com.todolist.dto.TodoResponse;
import com.todolist.exception.ResourceNotFoundException;
import com.todolist.model.Todo;
import com.todolist.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service de gestion des tâches.
 */
@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoResponse createTodo(TodoRequest request) {
        logger.debug("Création d'une nouvelle tâche : {}", request.getTitle());

        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.getCompleted() != null ? request.getCompleted() : false);

        Todo savedTodo = todoRepository.save(todo);
        logger.info("Tâche créée avec l'ID : {}", savedTodo.getId());

        return TodoResponse.fromEntity(savedTodo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getAllTodos() {
        logger.debug("Récupération de toutes les tâches");

        List<Todo> todos = todoRepository.findAll();
        logger.info("Nombre de tâches récupérées : {}", todos.size());

        return todos.stream()
                .map(TodoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TodoResponse getTodoById(Long id) {
        logger.debug("Récupération de la tâche avec l'ID : {}", id);

        Todo todo = findTodoOrThrow(id);
        return TodoResponse.fromEntity(todo);
    }

    @Override
    public TodoResponse updateTodo(Long id, TodoRequest request) {
        logger.debug("Mise à jour de la tâche avec l'ID : {}", id);

        Todo todo = findTodoOrThrow(id);

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        
        if (request.getCompleted() != null) {
            todo.setCompleted(request.getCompleted());
        }

        Todo updatedTodo = todoRepository.save(todo);
        logger.info("Tâche mise à jour : {}", updatedTodo.getId());

        return TodoResponse.fromEntity(updatedTodo);
    }

    @Override
    public void deleteTodo(Long id) {
        logger.debug("Suppression de la tâche avec l'ID : {}", id);

        Todo todo = findTodoOrThrow(id);
        todoRepository.delete(todo);

        logger.info("Tâche supprimée : {}", id);
    }

    /**
     * Recherche une tâche par ID ou lève une exception si non trouvée.
     */
    private Todo findTodoOrThrow(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Tâche non trouvée avec l'ID : {}", id);
                    return new ResourceNotFoundException("Tâche non trouvée avec l'ID : " + id);
                });
    }
}
