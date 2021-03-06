package ui.menu;

import command.MenuCommand;
import command.impl.*;
import dao.common.impl.BackUpablesManager;
import dao.impl.InMemoryUserDao;
import entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleMenu {

    private Map<Integer, MenuCommand> actionNumberToMenuCommandMap = new HashMap<>();

    public ConsoleMenu() {
        actionNumberToMenuCommandMap.put(1, new FindAllUsersMenuCommand());
        actionNumberToMenuCommandMap.put(2, new FindAllRepairRequestMenuCommand());
        actionNumberToMenuCommandMap.put(3, new FindAllRepairRecordsMenuCommand());
        actionNumberToMenuCommandMap.put(4, new FindUserByUsernameMenuCommand());
        actionNumberToMenuCommandMap.put(5, new CreateAppointmentMenuCommand());
        actionNumberToMenuCommandMap.put(6, new FindAllAppointmentMenuCommand());
        actionNumberToMenuCommandMap.put(7, new CreateUserMenuCommand());
        actionNumberToMenuCommandMap.put(8, new CreateRepairRequestMenuCommand());
        actionNumberToMenuCommandMap.put(9, new CreateRepairRecordMenuCommand());
        actionNumberToMenuCommandMap.put(10, new DeleteUserByIdMenuCommand());
        actionNumberToMenuCommandMap.put(11, new DeleteRepairRequestMenuCommand());
        actionNumberToMenuCommandMap.put(12, new DeleteRepairRecordMenuCommand());
        actionNumberToMenuCommandMap.put(13, new GetSumPriceMenuCommand());
    }

    public void initMenuConsole() {
        int number;
        int exitNumber = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("1- Find all user \n" +
                "2- Find all repair request \n" + "3- Find all repair record \n" +
                "4- Find user by username \n" + "5- Create appointment \n" +
                "6- Find all appointment \n" + "7- Create user  \n" +
                "8- Create repair request \n" + "9- Create repair record(For admin or Master)  \n" +
                "10- Delete user by username \n" + "11- Delete repair request  \n" +
                "12- Delete repair record \n" + "13- Get price for repair record  \n" +
                "0- Exit \n");
        do {
            System.out.println("Enter number: ");
            number = scanner.nextInt();
            MenuCommand menuCommand = actionNumberToMenuCommandMap.get(number);
            if (menuCommand != null) {
                menuCommand.execute();
            } else {
                System.out.println("Not recognized command!");
            }
        } while (number != exitNumber);
        System.out.println("Goodbye");


    }
}
