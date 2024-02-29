package ru.job4j.todo.store.user;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.task.HibernateTaskStore;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserStore implements UserStore {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateTaskStore.class.getName());
    private final SessionFactory sf;

    @Override
    public Optional<User> save(User user) {
        Session session = sf.openSession();
        Optional<User> optionalUser = Optional.empty();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            optionalUser = Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Ошибка при попытке зарегистрировать пользователя", e);
        } finally {
            session.close();
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        Optional<User> optionalUser = Optional.empty();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User where login = :fLogin and password = :fPassword", User.class)
                    .setParameter("fLogin", login)
                    .setParameter("fPassword", password);
            optionalUser = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Ошибка при попытке найти пользователя", e);
        } finally {
            session.close();
        }
        return optionalUser;
    }
}
