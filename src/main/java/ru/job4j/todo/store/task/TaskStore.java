package ru.job4j.todo.store.task;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskStore {
    Task save(Task task);
    boolean update(Task task);
    boolean deleteById(int id);
    Collection<Task> findAll();
    Optional<Task> findById(int id);
    Collection<Task> findByDone(boolean done);
    boolean complete(int id);
}
