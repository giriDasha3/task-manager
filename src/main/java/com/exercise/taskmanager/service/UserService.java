package com.exercise.taskmanager.service;

import com.exercise.taskmanager.model.User;
import com.exercise.taskmanager.model.Task;
import com.exercise.taskmanager.repository.TaskRepository;
import com.exercise.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getUserAssignedTasks(Long userId) {
        return taskRepository.findByAssignedUsers_Id(userId);
    }

}
