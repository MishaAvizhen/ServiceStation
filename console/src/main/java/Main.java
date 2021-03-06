import dao.common.impl.BackUpablesManager;
import db.JdbcInit;
import db.JdbcTemplate;
import ui.menu.ConsoleMenu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        BackUpablesManager backUpablesManager = new BackUpablesManager();
        backUpablesManager.readFromFile();
        JdbcInit jdbcInit = new JdbcInit();
        jdbcInit.initDataBase();
        try {
            ConsoleMenu consoleMenu = new ConsoleMenu();
            consoleMenu.initMenuConsole();
        } finally {
            backUpablesManager.writeToFile();
        }




    }
}
