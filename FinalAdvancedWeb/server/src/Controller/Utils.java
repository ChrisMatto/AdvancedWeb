package Controller;

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

    public static int getYear(String year) {
        int anno;
        Response.ResponseBuilder responseBuilder;
        if (year.equals("current")) {
            anno = Utils.getCurrentYear();
        } else {
            if (NumberUtils.isParsable(year)) {
                anno = Integer.parseInt(year);
                if (anno > 0) {
                    if (year.length() != 4) {
                        responseBuilder = Response.status(400);
                        throw new WebApplicationException(responseBuilder.build());
                    }
                } else {
                    responseBuilder = Response.status(404);
                    throw new WebApplicationException(responseBuilder.build());
                }
            } else {
                responseBuilder = Response.status(404);
                throw new WebApplicationException(responseBuilder.build());
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

