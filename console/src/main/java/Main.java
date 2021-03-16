import dao.common.impl.BackUpablesManager;
import ui.menu.ConsoleMenu;


public class Main {
    public static void main(String[] args) {

        BackUpablesManager backUpablesManager = new BackUpablesManager();
        backUpablesManager.readFromFile();
        try {
            ConsoleMenu consoleMenu = new ConsoleMenu();
            consoleMenu.initMenuConsole();
        } finally {
            backUpablesManager.writeToFile();
        }




    }
}
