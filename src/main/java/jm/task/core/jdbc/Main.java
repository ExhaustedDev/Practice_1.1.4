package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl(new UserDaoHibernateImpl());

        userService.createUsersTable();

        userService.saveUser("John", "Smith", (byte) 25);
        userService.saveUser("Alex", "Brown", (byte) 46);
        userService.saveUser("Ann", "Williams", (byte) 19);
        userService.saveUser("Mike", "Jones", (byte) 28);

        List<User> users = userService.getAllUsers();

        users.forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
