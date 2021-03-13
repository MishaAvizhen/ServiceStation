package ui.menu;

import command.MenuCommand;
import command.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class ConsoleMenu {

    private Map<Integer, MenuCommand> actionNumberToMenuCommandMap = new HashMap<>();

    @Autowired
    public ConsoleMenu(List<MenuCommand> allMenuCommands) {
        for (MenuCommand menuCommand : allMenuCommands) {
            actionNumberToMenuCommandMap.put(menuCommand.getHandledMenuNumber(), menuCommand);
        }
//        actionNumberToMenuCommandMap.put(1, new FindAllUsersMenuCommand());
//        actionNumberToMenuCommandMap.put(2, new FindAllRepairRequestMenuCommand());
//        actionNumberToMenuCommandMap.put(3, new FindAllRepairRecordsMenuCommand());
//        actionNumberToMenuCommandMap.put(4, new FindUserByUsernameMenuCommand());
//        actionNumberToMenuCommandMap.put(5, new CreateAppointmentMenuCommand());
//        actionNumberToMenuCommandMap.put(6, new FindAllAppointmentMenuCommand());
//        actionNumberToMenuCommandMap.put(7, new CreateUserMenuCommand());
//        actionNumberToMenuCommandMap.put(8, new CreateRepairRequestMenuCommand());
//        actionNumberToMenuCommandMap.put(9, new CreateRepairRecordMenuCommand());
//        actionNumberToMenuCommandMap.put(10, new DeleteUserByIdMenuCommand());
//        actionNumberToMenuCommandMap.put(11, new DeleteRepairRequestMenuCommand());
//        actionNumberToMenuCommandMap.put(12, new DeleteRepairRecordMenuCommand());
//        actionNumberToMenuCommandMap.put(13, new GetSumPriceMenuCommand());
//        actionNumberToMenuCommandMap.put(14, new UpdateUserMenuCommand());
//        actionNumberToMenuCommandMap.put(15, new UpdateRepairRequestMenuCommand());
//        actionNumberToMenuCommandMap.put(16, new UpdateRepairRecordMenuCommand());
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
                "14- Update user \n" + "15- Update repair request  \n" +
                "16- Update repair record \n" + "17- ----  \n" +
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
