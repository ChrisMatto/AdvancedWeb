package Controller;

import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class Utils {

    static ServletContext context;

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

    public static File getFile(String filePath) {
        return new File(context.getRealPath("") + filePath);
    }

    public static Boolean shouldDeleteSession(Timestamp timestamp)
    {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        long diff = Math.abs(currentTimestamp.getTime() - timestamp.getTime());
        long days = TimeUnit.MILLISECONDS.toDays(diff);
        return days >= 1;
    }

    public static String saveNewFile(String encodedFile, String filePath, String fileExtension) {
        String extension = "." + fileExtension;
        byte[] fileBytes = Base64.getDecoder().decode(encodedFile);
        String basePath = context.getRealPath("");
        File existingFile = new File(basePath + filePath + extension);
        int i = 1;
        String newFilePath = filePath;
        while (existingFile.exists()) {
            newFilePath += i;
            i++;
            existingFile = new File(basePath + newFilePath + extension);
        }
        InputStream fileStream = new ByteArrayInputStream(fileBytes);
        try {
            Files.copy(fileStream, Paths.get(basePath + newFilePath + extension));
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return newFilePath + extension;
    }

    public static String updateFile(String encodedFile, String filePath, String fileExtension) {
        String extension = "." + fileExtension;
        byte[] fileBytes = Base64.getDecoder().decode(encodedFile);
        String basePath = context.getRealPath("");
        File existingFile = new File(basePath + filePath + extension);
        while (existingFile.exists()) {
            try {
                Files.delete(Paths.get(basePath + filePath + extension));
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
        InputStream fileStream = new ByteArrayInputStream(fileBytes);
        try {
            Files.copy(fileStream, Paths.get(basePath + filePath + extension));
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return filePath + extension;
    }

    public static String getEncodedFile(String filePath) {
        String basePath = context.getRealPath("");
        File file = new File(basePath + filePath);
        if (file.exists()) {
            try {
                byte[] fileBytes = Files.readAllBytes(Paths.get(basePath + filePath));
                return Base64.getEncoder().encodeToString(fileBytes);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
        return null;
    }

    public static void deleteFile(String filePath) {
        String basePath = context.getRealPath("");
        File file = new File(basePath + filePath);
        if (file.exists()) {
            try {
                Files.delete(Paths.get(basePath + filePath));
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }
}

