package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;




public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS USER ( id INT NOT NULL AUTO_INCREMENT," +
            "name VARCHAR(45) NOT NULL, lastname VARCHAR(45) NOT NULL, age INT NOT NULL, PRIMARY KEY (id))";

    private static final String DROP = "DROP TABLE IF EXISTS USER";

    private static final String CLEAN = "DELETE User";

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    private Transaction transaction = null;

    User user;



    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(CREATE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        System.out.println("Таблица создана через Hibernate");
    }

    @Override
    public void dropUsersTable() {

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(DROP).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println("Таблица удалена через Hibernate");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        user = new User(name,lastName, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println(name + " " + lastName + " добавлен(а) в базу данных через Hibernate");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }


    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        }
    }

    @Override
    public List<User> getAllUsers() {

        try (Session session = Util.getSessionFactory().openSession()) {
            System.out.println("Список юзеров получен через Hibernate");
            return session.createQuery("from User", User.class).list();
        }

    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery(CLEAN).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        System.out.println("Таблица очищена через Hibernate");
    }
}
