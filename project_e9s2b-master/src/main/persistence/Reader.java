package persistence;

import model.FoodType;
import model.Restaurant;
import model.RestaurantOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

// A reader that can read restaurant option data from a file
//code taken and modified from TellerApp
public class Reader {
    public static final String DELIMITER = ",";

    // EFFECTS: returns a restaurant options parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static RestaurantOptions readRestaurantOptions(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    private static RestaurantOptions parseContent(List<String> fileContent) {
        RestaurantOptions resOptions = new RestaurantOptions();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);

            //parseOptions
            FoodType foodtype = FoodType.valueOf(lineComponents.get(0));

            for (int i = 1; i < lineComponents.size(); i++) {
                Restaurant res = new Restaurant(lineComponents.get(i));
                resOptions.addRestaurant(foodtype, res);
            }
        }
        return resOptions;
    }
}
