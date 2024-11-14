package ru.gb.example_hw_sem5.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.gb.example_hw_sem5.model.Task;
import ru.gb.example_hw_sem5.model.TaskStatus;
import ru.gb.example_hw_sem5.service.TaskService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
Контроллер
 */

@Controller
@RequiredArgsConstructor
@Log
public class TaskController {
    // подключаем наш сервис
    private final TaskService taskService;
    // получения списка всех задач с логированием
    @GetMapping("/tasks/{status}")
    public String getAllTasks(@PathVariable("status") Integer status, Model model) {
        log.info("Получение списка задач из базы данных");
        List<Task> tasks = new ArrayList<>();
        String statusTitle = "";
        if (status == -1) {
            tasks = taskService.getAllTasks();
            statusTitle = "Все";
        }
        if (status >= 0 && status < TaskStatus.values().length) {
            TaskStatus taskStatus = TaskStatus.values()[status];
            statusTitle = taskStatus.getValue();
            tasks = taskService.getTasksByStatus(taskStatus);
        }
        log.info("Количество задач, полученных из базы данных: " + tasks.size());
        model.addAttribute("tasks", tasks);
        model.addAttribute("status_title", statusTitle);
        log.info("Переход к /tasks-list");
        return "/tasks-list";
    }

    // Переход на страницу с новой задачей
    @GetMapping("/task-add")
    public String createTaskForm(Task task, Model model) {
        log.info("Создание формы /task-add");
        model.addAttribute("today", LocalDate.now());
        return "/task-add";
    }

    // Добавление новой задачи
    @PostMapping("/task-add")
    public String addTask(Task task) {
        task.setDate(LocalDate.now());
        task.setStatus(TaskStatus.NOT_STARTED);
        log.info("Попытка добавить новую задачу в базу данных: " + task);
        taskService.addTask(task);
        log.info("Новая задача добавлена в базу данных");
        return "redirect:/tasks/0";
    }

    // Удаление задачи
    @GetMapping("/task-delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        int status = taskService.findById(id).getStatus().ordinal();
        log.info("Удаление задачи с id = " + id);
        taskService.deleteTaskById(id);
        log.info("Задача успешно удалена из базы данных");
        return "redirect:/tasks/" + status;
    }

    // Обновление статуса задачи
    @GetMapping("/task-update/{id}/{status}")
    public String updateTaskStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        log.info("Изменение статуса задачи с id = " + id + " на статус " + status);
        Task task = taskService.findById(id);
        if (!task.getStatus().equals(TaskStatus.values()[status])) {
            task.setStatus(TaskStatus.values()[status]);
            taskService.updateTaskStatus(task);
            log.info("Статус задачи успешно обновлен");
        } else log.info("Статус задачи не менялся");
        return "redirect:/tasks/" + status;
    }

}
