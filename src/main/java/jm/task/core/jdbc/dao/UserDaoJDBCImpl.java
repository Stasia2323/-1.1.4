package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.engine.jdbc.spi.StatementPreparer;

import javax.persistence.Id;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    //добавлен закрытие конекшн

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(200), lastname VARCHAR(200), age INT)");
            getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("DROP TABLE IF  EXISTS users");
            getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = Util.getConnection().prepareStatement
                ("INSERT INTO users (name,lastname,age)" + "VALUES (?,?,?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.execute();

            System.out.println("User с именем – " + name + " добавлен в базу данных");
            getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = Util.getConnection().prepareStatement
                ("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<User> getAllUsers() {
        String getAll = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();

        try (Statement statement = Util.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(getAll)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                getConnection().close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public void cleanUsersTable() {
        try ( Statement statement = Util.getConnection().createStatement()) {
            statement.execute("TRUNCATE TABLE users");
            getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

