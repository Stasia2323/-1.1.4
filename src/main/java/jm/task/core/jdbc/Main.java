package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Ivan", "Ivanov", (byte) 33);
        userDao.saveUser("Sergey", "Ivanov", (byte) 22);
        userDao.saveUser("Ivan", "Petrov", (byte) 33);
        userDao.saveUser("Petr", "Ivano", (byte) 22);

        for (User user : userDao.getAllUsers()) {
            System.out.println(user);
        }


        // добавлен цикл

        //for (int i = 1; i < 5; i++) {
           // userDao.saveUser("User" + i, "Userov", (byte) 40);
      //  }


        userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}

