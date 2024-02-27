package ru.job4j.todo.service;

import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface ITaskService {
    Task save(Task task);
    boolean update(Task task);
    boolean deleteById(int id);
    Collection<TaskDto> findAll();
    Optional<Task> findById(int id);
}
