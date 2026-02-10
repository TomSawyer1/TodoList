package com.todolist.controller;

import com.todolist.dto.TodoRequest;
import com.todolist.dto.TodoResponse;
import com.todolist.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST pour la gestion des tâches.
 */
@RestController
@RequestMapping("/api/todos")
@Tag(name = "Todo", description = "API de gestion des tâches")
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    @Operation(summary = "Créer une tâche", description = "Crée une nouvelle tâche dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tâche créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<TodoResponse> createTodo(
            @Valid @RequestBody TodoRequest request) {
        logger.info("POST /api/todos - Création d'une tâche");
        TodoResponse response = todoService.createTodo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Lister les tâches", description = "Récupère toutes les tâches")
    @ApiResponse(responseCode = "200", description = "Liste des tâches récupérée")
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        logger.info("GET /api/todos - Récupération de toutes les tâches");
        List<TodoResponse> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une tâche", description = "Récupère une tâche par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tâche trouvée"),
            @ApiResponse(responseCode = "404", description = "Tâche non trouvée")
    })
    public ResponseEntity<TodoResponse> getTodoById(
            @Parameter(description = "ID de la tâche") @PathVariable Long id) {
        logger.info("GET /api/todos/{} - Récupération d'une tâche", id);
        TodoResponse response = todoService.getTodoById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une tâche", description = "Met à jour une tâche existante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tâche mise à jour"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Tâche non trouvée")
    })
    public ResponseEntity<TodoResponse> updateTodo(
            @Parameter(description = "ID de la tâche") @PathVariable Long id,
            @Valid @RequestBody TodoRequest request) {
        logger.info("PUT /api/todos/{} - Mise à jour d'une tâche", id);
        TodoResponse response = todoService.updateTodo(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une tâche", description = "Supprime une tâche par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tâche supprimée"),
            @ApiResponse(responseCode = "404", description = "Tâche non trouvée")
    })
    public ResponseEntity<Void> deleteTodo(
            @Parameter(description = "ID de la tâche") @PathVariable Long id) {
        logger.info("DELETE /api/todos/{} - Suppression d'une tâche", id);
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
