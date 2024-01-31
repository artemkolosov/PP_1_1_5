package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection;


    private static final String CREATE = "CREATE TABLE IF NOT EXISTS USER ( id INT NOT NULL AUTO_INCREMENT, name VARCHAR(45) NOT NULL, lastname VARCHAR(45) NOT NULL, age INT NOT NULL, PRIMARY KEY (id))";

    private static final String DROP = "DROP TABLE IF EXISTS USER";
    private static final String SAVE = "INSERT INTO USER (name, lastName, age) VALUES (?, ?, ?)";
    private static final String REMOVEBYID = "DELETE FROM USER WHERE id = ?";
    private static final String GETALL = "SELECT * FROM USER";
    private static final String CLEAN = "TRUNCATE TABLE USER";


    public UserDaoJDBCImpl() {
       connection = Util.connect();
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE);
        } catch (Exception e) {
            throw new RuntimeException("...." + e.getMessage());
        }
        System.out.println("Таблица очищена через JDBC");
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP);
        } catch (Exception e) {
            throw new RuntimeException("...." + e.getMessage());

        }
        System.out.println("Таблица удалена через JDBC");
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            try (PreparedStatement sql = connection.prepareStatement(SAVE)) {
                sql.setString(1, name);
                sql.setString(2, lastName);
                sql.setByte(3, age);
                sql.executeUpdate();

            }
        } catch (Exception e) {
            throw new RuntimeException("...." + e.getMessage());
        }
        System.out.println(name + " " + lastName + " добавлен(а) в базу данных через JDBC");
    }

    public void removeUserById(long id) {
        try {
            try (PreparedStatement sql = connection.prepareStatement(REMOVEBYID)) {
                sql.setLong(1, id);
                sql.executeUpdate();
                System.out.println("id " + id + " удален из базы данных через JDBC");
            }
        } catch (Exception e) {
            throw new RuntimeException("...." + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement sql = connection.createStatement()) {
            sql.execute(GETALL);
            ResultSet re = sql.getResultSet();
            while (re.next()) {
                Long id = re.getLong("id");
                String name = re.getString("name");
                String lastName = re.getString("lastName");
                byte age = re.getByte("age");
                User user = new User(name, lastName, age);
                users.add(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("...." + e.getMessage());
        }
        System.out.println("Список юзеров получен через JDBC");
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CLEAN);
        } catch (Exception e) {
            throw new RuntimeException("...." + e.getMessage());
        }
        System.out.println("Таблица очищена через JDBC");
    }
}
