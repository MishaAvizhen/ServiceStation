package command.impl;

import java.util.Date;

public class CommonMethods {
    public static final int TODAY_INDEX = 1;
    public static final int TOMORROW_INDEX = 2;

    static Date getDate(Integer dayIndex) {
        if (dayIndex == TODAY_INDEX) {
            return new Date();
        } else if (dayIndex == TOMORROW_INDEX) {
            Date today = new Date();
            return new Date(today.getTime() + (1000 * 60 * 60 * 24));
        } else {
            System.out.println("Incorrect Date");
        }
        return null;
    }

}
