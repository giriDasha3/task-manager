package com.exercise.taskmanager.service;

import com.exercise.taskmanager.exception.BadRequestException;
import com.exercise.taskmanager.exception.NotFoundException;
import com.exercise.taskmanager.model.Task;
import com.exercise.taskmanager.model.TaskStatistics;
import com.exercise.taskmanager.model.TaskStatus;
import com.exercise.taskmanager.model.User;
import com.exercise.taskmanager.repository.TaskRepository;
import com.exercise.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Task updatedTask) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found with id: " + taskId));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());
        task.setStatus(updatedTask.getStatus());
        task.setCompletedDate(updatedTask.getCompletedDate());
        task.setPriority(updatedTask.getPriority());

        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public void assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found with id: " + taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        task.getAssignedUsers().add(user);
        taskRepository.save(task);
    }

    public void setTaskProgress(Long taskId, int progressPercentage) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found with id: " + taskId));

        if (progressPercentage < 0 || progressPercentage > 100) {
            throw new BadRequestException("Progress percentage must be between 0 and 100.");
        }

        task.setProgressPercentage(progressPercentage);
        taskRepository.save(task);
    }

    public List<Task> getOverdueTasks() {
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        return taskRepository.findByDueDateBeforeAndStatusNot(currentDate, TaskStatus.COMPLETED.toString());
    }

    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    public TaskStatistics getTaskStatistics() {
        long totalTasks = taskRepository.count();
        long completedTasks = taskRepository.countByStatus(TaskStatus.COMPLETED.toString());
        double completionPercentage = (completedTasks / (double) totalTasks) * 100;

        TaskStatistics statistics = new TaskStatistics();
        statistics.setTotalTasks(totalTasks);
        statistics.setCompletedTasks(completedTasks);
        statistics.setCompletionPercentage(completionPercentage);

        return statistics;
    }
}

