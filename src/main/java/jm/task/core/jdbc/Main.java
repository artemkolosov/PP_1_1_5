package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.cleanUsersTable();
        userService.saveUser("Джимми", "Хендрикс", (byte) 27);
        userService.saveUser("Дженис", "Джоплин", (byte) 27);
        userService.saveUser("Джим", "Моррисон", (byte) 27);
        userService.saveUser("Курт", "Кобейн", (byte) 27);
        userService.removeUserById(3);
        userService.removeUserById(1);
        userService.saveUser("Эми", "Уайнхаус", (byte) 27);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }


}
