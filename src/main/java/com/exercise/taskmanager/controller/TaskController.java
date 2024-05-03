package com.exercise.taskmanager.controller;

import com.exercise.taskmanager.model.Task;
import com.exercise.taskmanager.model.TaskStatistics;
import com.exercise.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok().body(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable("taskId") Long taskId, @RequestBody Task updatedTask) {
        Task task = taskService.updateTask(taskId, updatedTask);
        return ResponseEntity.ok().body(task);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{taskId}/assign")
    public ResponseEntity<Void> assignTaskToUser(@PathVariable Long taskId, @RequestBody Long userId) {
        taskService.assignTaskToUser(taskId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{taskId}/progress")
    public ResponseEntity<Void> setTaskProgress(@PathVariable Long taskId, @RequestBody int progressPercentage) {
        taskService.setTaskProgress(taskId, progressPercentage);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Task>> getOverdueTasks() {
        List<Task> overdueTasks = taskService.getOverdueTasks();
        return ResponseEntity.ok().body(overdueTasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable("status") String status) {
        List<Task> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/statistics")
    public ResponseEntity<TaskStatistics> getTaskStatistics() {
        TaskStatistics taskStatistics = taskService.getTaskStatistics();
        return ResponseEntity.ok().body(taskStatistics);
    }
}
