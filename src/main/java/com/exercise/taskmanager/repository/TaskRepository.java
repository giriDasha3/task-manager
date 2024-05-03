package com.exercise.taskmanager.repository;

import com.exercise.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedUsers_Id(Long userId);

    List<Task> findByDueDateBeforeAndStatusNot(Timestamp currentDate, String completed);

    List<Task> findByStatus(String status);

    long countByStatus(String completed);
}
