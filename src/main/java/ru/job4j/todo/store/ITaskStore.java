package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface ITaskStore {
    Task save(Task task);
    boolean update(Task task);
    boolean deleteById(int id);
    Collection<Task> findAll();
    Optional<Task> findById(int id);
}
