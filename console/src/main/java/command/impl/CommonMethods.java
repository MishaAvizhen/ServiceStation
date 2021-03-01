package command.impl;

import java.util.Date;

public class CommonMethods {
     static Date getDate(Integer dayIndex) {
        if (dayIndex == 1) {
            return new Date();
        } else if (dayIndex == 2) {
            Date today = new Date();
            return new Date(today.getTime() + (1000 * 60 * 60 * 24));
        } else {
            System.out.println("Incorrect Date");
        }
        return null;
    }

}
