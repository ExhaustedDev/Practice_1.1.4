package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    lastName VARCHAR(255) NOT NULL,
                    age INT
                )
                """;

        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();

            currentSession.createNativeQuery(sql)
                    .executeUpdate();

            transaction.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();

            currentSession.createNativeQuery(sql)
                    .executeUpdate();

            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();

            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);

            currentSession.persist(user);

            transaction.commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();

            User user = currentSession.get(User.class, id);

            if (user != null) {
                currentSession.remove(user);
            }

            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;

        try (Session currentSession = sessionFactory.openSession()) {
            users = currentSession.createQuery("select u from User u", User.class)
                    .getResultList();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();

            currentSession.createQuery("delete from User")
                    .executeUpdate();

            transaction.commit();
        }
    }
}
