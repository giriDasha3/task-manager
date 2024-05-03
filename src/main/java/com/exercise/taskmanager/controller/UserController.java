package com.exercise.taskmanager.controller;

import com.exercise.taskmanager.model.Task;
import com.exercise.taskmanager.model.User;
import com.exercise.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<Task>> getUserAssignedTasks(@PathVariable("userId") Long userId) {
        List<Task> assignedTasks = userService.getUserAssignedTasks(userId);
        return ResponseEntity.ok().body(assignedTasks);
    }

}
