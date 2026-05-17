package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();

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
        SessionFactory sessionFactory = Util.getSessionFactory();

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
        SessionFactory sessionFactory = Util.getSessionFactory();

        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();

            currentSession.createNativeQuery(sql)
                    .setParameter(1, name)
                    .setParameter(2, lastName)
                    .setParameter(3, age)
                    .executeUpdate();

            transaction.commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactory = Util.getSessionFactory();

        String sql = "DELETE FROM users WHERE id = ?";

        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();

            currentSession.createNativeQuery(sql)
                    .setParameter(1, id)
                    .executeUpdate();

            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory sessionFactory = Util.getSessionFactory();

        List<User> users;

        String sql = "SELECT * FROM users";

        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();

            users = currentSession.createNativeQuery(sql, User.class)
                    .getResultList();

            transaction.commit();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();

        String sql = "DELETE FROM users";

        try (Session currentSession = sessionFactory.openSession()) {
            Transaction transaction = currentSession.beginTransaction();

            currentSession.createNativeQuery(sql)
                    .executeUpdate();

            transaction.commit();
        }
    }
}
