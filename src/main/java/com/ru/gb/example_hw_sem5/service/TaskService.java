package ru.gb.example_hw_sem5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.example_hw_sem5.model.Task;
import ru.gb.example_hw_sem5.model.TaskStatus;
import ru.gb.example_hw_sem5.repository.TaskRepository;


import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskService {
    // Добавляем наш репозиторий
    private final TaskRepository taskRepository;

    // Добавление задачи
    public void addTask(Task task) {
        taskRepository.save(task);
    }

    // Получение списка всех задач
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Поиск задачи по ID
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    // Получение списка задач по статусу
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findAllByStatus(status);
    }

    // Изменение статуса
    public void updateTaskStatus(Task task) {
        taskRepository.save(task);
    }

    // Удаление задачи
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
}