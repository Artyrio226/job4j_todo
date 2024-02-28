package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskStore implements TaskStore {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateTaskStore.class.getName());
    private final SessionFactory sf;

    @Override
    public Task save(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Ошибка при попытке сохранить задание", e);
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public boolean update(Task task) {
        boolean rsl = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            rsl = session.createQuery("""
                                     UPDATE Task
                                      SET title = :fTitle, description = :fDescription, done = :fDone
                                      WHERE id = :fId
                                     """)
                    .setParameter("fTitle", task.getTitle())
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fDone", task.isDone())
                    .setParameter("fId", task.getId())
                    .executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Ошибка при попытке обновить задание", e);
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public boolean deleteById(int id) {
        boolean rsl = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            rsl = session.createQuery("DELETE Task WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Ошибка при попытке удалить задание", e);
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        List<Task> list = new ArrayList<>();
        try {
            session.beginTransaction();
            list = session.createQuery("from Task", Task.class)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Ошибка при попытке найти список заданий", e);
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Optional<Task> optionalTask = Optional.empty();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery("from Task where id = :fId", Task.class)
                    .setParameter("fId", id);
            optionalTask = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Ошибка при попытке  найти задание", e);
        } finally {
            session.close();
        }
        return optionalTask;
    }

    @Override
    public Collection<Task> findByDone(boolean done) {
        Session session = sf.openSession();
        List<Task> list = new ArrayList<>();
        try {
            session.beginTransaction();
            list = session.createQuery("from Task where done = :fDone", Task.class)
                    .setParameter("fDone", done)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Ошибка при попытке найти список заданий по принципу новые и выполненные", e);
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public boolean complete(int id) {
        boolean rsl = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            rsl = session.createQuery("""
                                     UPDATE Task SET done = :fDone
                                      WHERE id = :fId
                                     """)
                          .setParameter("fDone", true)
                          .setParameter("fId", id)
                          .executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Ошибка при попытке выполнить задание", e);
        } finally {
            session.close();
        }
        return rsl;
    }
}
