package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    lastName VARCHAR(255) NOT NULL,
                    age INT
                )
                """;

        try (Connection connection = Util.getConnection()) {
            try (Statement createTableStatement = connection.createStatement()) {
                createTableStatement.executeUpdate(sql);
                System.out.println("Таблица users создана или уже существует");
            }
        } catch (SQLException e) {
            System.out.printf("Произошла ошибка в классе %s : метод createUsersTable\n", getClass().getName());
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getConnection();
             Statement dropTableStatement = connection.createStatement()) {
            dropTableStatement.executeUpdate(sql);
            System.out.println("Таблица users была удалена");
        } catch (SQLException e) {
            System.out.printf("Произошла ошибка в классе %s : метод dropUsersTable\n", getClass().getName());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement addUserPreparedStatement = connection.prepareStatement(sql)) {

            addUserPreparedStatement.setString(1, name);
            addUserPreparedStatement.setString(2, lastName);
            addUserPreparedStatement.setInt(3, age);

            int rows = addUserPreparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.printf("Пользователь { %s %s %d } добавлен\n", name, lastName, age);
            }
        } catch (SQLException e) {
            System.out.printf("Произошла ошибка в классе %s : метод saveUser\n", getClass().getName());
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection();
            PreparedStatement deleteUserPreparedStatement = connection.prepareStatement(sql)) {
            deleteUserPreparedStatement.setLong(1, id);

            int rows = deleteUserPreparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.printf("Пользователь c id %d удален\n", id);
            }
        } catch (SQLException e) {
            System.out.printf("Произошла ошибка в классе %s : метод removeUserById\n", getClass().getName());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.printf("Произошла ошибка в классе %s : метод getAllUsers\n", getClass().getName());
        }

        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";

        try (Connection connection = Util.getConnection();
            Statement deleteFromUsersStatement = connection.createStatement()) {
            deleteFromUsersStatement.executeUpdate(sql);
            System.out.println("Таблица users была очищена");
        } catch (SQLException e) {
            System.out.printf("Произошла ошибка в классе %s : метод cleanUsersTable\n", getClass().getName());
        }
    }
}
