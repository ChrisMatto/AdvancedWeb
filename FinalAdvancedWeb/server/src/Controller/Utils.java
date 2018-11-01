package Controller;

import API.AuthAPI;
import API.CoursesAPI;
import org.apache.commons.lang3.math.NumberUtils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

public class Utils {

    public static int getCurrentYear() {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int year = date.getYear();
        if(month <= 8)
            year = year-1;
        return year;
    }

    public static Object getYear(String year) {
        int anno;
        if (year.equals("current")) {
            anno = Utils.getCurrentYear();
        } else {
            if (NumberUtils.isParsable(year)) {
                anno = Integer.parseInt(year);
                if (anno > 0) {
                    if (year.length() != 4) {
                        return YearError.error400;
                    }
                } else {
                    return YearError.error404;
                }
            } else {
                return YearError.error404;
            }
        }
        return anno;
    }

    public static Controllers getController(String controllerName) {
        Controllers controller;
        try {
            controller = Controllers.valueOf(controllerName);
        } catch (IllegalArgumentException iae) {
            controller = Controllers.noController;
        }
        return controller;
    }

}

