package ru.job4j.todo.store.user;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.CrudRepository;
import ru.job4j.todo.store.task.HibernateTaskStore;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserStore implements UserStore {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateTaskStore.class.getName());
    private final CrudRepository crudRepository;

    @Override
    public Optional<User> save(User user) {
        Optional<User> optionalUser = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(user));
            optionalUser = Optional.of(user);
        } catch (Exception e) {
            LOG.error("Ошибка при попытке зарегистрировать пользователя", e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Optional<User> optionalUser = Optional.empty();
        try {
            optionalUser = crudRepository.optional("from User where login = :fLogin and password = :fPassword",
                    User.class, Map.of("fLogin", login, "fPassword", password));
        } catch (Exception e) {
            LOG.error("Ошибка при попытке найти пользователя", e);
        }
        return optionalUser;
    }
}
