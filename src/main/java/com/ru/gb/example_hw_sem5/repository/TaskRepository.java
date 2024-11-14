package ru.gb.example_hw_sem5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.example_hw_sem5.model.Task;
import ru.gb.example_hw_sem5.model.TaskStatus;

import java.util.List;
// интерфейс репозитория, наследующий JPA репозиторий
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByStatus(TaskStatus status);
}