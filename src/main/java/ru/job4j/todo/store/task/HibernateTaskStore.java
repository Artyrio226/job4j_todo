package ru.job4j.todo.store.task;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.CrudRepository;

import java.util.*;

@Repository
@AllArgsConstructor
public class HibernateTaskStore implements TaskStore {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateTaskStore.class.getName());
    private final CrudRepository crudRepository;

    @Override
    public Task save(Task task) {
        try {
            crudRepository.run(session -> session.save(task));
        } catch (Exception e) {
            LOG.error("Ошибка при попытке сохранить задание", e);
        }
        return task;
    }

    @Override
    public boolean update(Task task) {
        boolean rsl = false;
        try {
            rsl = crudRepository.execute("""
                    UPDATE Task
                     SET title = :fTitle, description = :fDescription, done = :fDone
                     WHERE id = :fId
                    """, Map.of(
                            "fTitle", task.getTitle(),
                            "fDescription", task.getDescription(),
                            "fDone", task.isDone(),
                            "fId", task.getId())
            );
        } catch (Exception e) {
            LOG.error("Ошибка при попытке обновить задание", e);
        }
        return rsl;
    }

    @Override
    public boolean deleteById(int id) {
        boolean rsl = false;
        try {
            rsl = crudRepository.execute("DELETE Task WHERE id = :fId", Map.of("fId", id));
        } catch (Exception e) {
            LOG.error("Ошибка при попытке удалить задание", e);
        }
        return rsl;
    }

    @Override
    public List<Task> findAll() {
        List<Task> list = new ArrayList<>();
        try {
            list = crudRepository.query("from Task", Task.class);
        } catch (Exception e) {
            LOG.error("Ошибка при попытке найти список заданий", e);
        }
        return list;
    }

    @Override
    public Optional<Task> findById(int id) {
        Optional<Task> optionalTask = Optional.empty();
        try {
            optionalTask = crudRepository.optional("from Task where id = :fId", Task.class, Map.of("fId", id));
        } catch (Exception e) {
            LOG.error("Ошибка при попытке  найти задание", e);
        }
        return optionalTask;
    }

    @Override
    public Collection<Task> findByDone(boolean done) {
        List<Task> list = new ArrayList<>();
        try {
            list = crudRepository.query("from Task where done = :fDone", Task.class, Map.of("fDone", done));
        } catch (Exception e) {
            LOG.error("Ошибка при попытке найти список заданий по принципу новые и выполненные", e);
        }
        return list;
    }

    @Override
    public boolean complete(int id) {
        boolean rsl = false;
        try {
            rsl = crudRepository.execute("UPDATE Task SET done = :fDone WHERE id = :fId",
                     Map.of("fDone", true, "fId", id));
        } catch (Exception e) {
            LOG.error("Ошибка при попытке выполнить задание", e);
        }
        return rsl;
    }
}
