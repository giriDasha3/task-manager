package com.exercise.taskmanager.util;

import com.exercise.taskmanager.model.Priority;
import com.exercise.taskmanager.model.Task;

import java.util.*;

public class TaskPriorityQueue {

    private Map<Priority, Queue<Task>> priorityQueueMap;

    public TaskPriorityQueue() {
        priorityQueueMap = new HashMap<>();
        priorityQueueMap.put(Priority.HIGH, new PriorityQueue<>(Comparator.comparing(Task::getDueDate)));
        priorityQueueMap.put(Priority.MEDIUM, new PriorityQueue<>(Comparator.comparing(Task::getDueDate)));
        priorityQueueMap.put(Priority.LOW, new PriorityQueue<>(Comparator.comparing(Task::getDueDate)));
    }

    public void addTask(Task task, String priority) {
        Queue<Task> priorityQueue = priorityQueueMap.get(priority);
        if (priorityQueue != null) {
            priorityQueue.offer(task);
        } else {
            throw new IllegalArgumentException("Invalid priority level: " + priority);
        }
    }

    public Task getNextTask() {
        for (var priority : Arrays.asList(Priority.values())) {
            Queue<Task> priorityQueue = priorityQueueMap.get(priority);
            if (!priorityQueue.isEmpty()) {
                return priorityQueue.peek();
            }
        }
        return null;
    }

    public Task removeNextTask() {
        for (var priority : Arrays.asList(Priority.values())) {
            Queue<Task> priorityQueue = priorityQueueMap.get(priority);
            if (!priorityQueue.isEmpty()) {
                return priorityQueue.poll();
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return priorityQueueMap.values().stream().allMatch(Queue::isEmpty);
    }
}
