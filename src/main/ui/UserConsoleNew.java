package ui;

import model.FoodType;
import model.Restaurant;
import model.RestaurantOptions;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class UserConsoleNew {
    private static final String RES_FILE = "./data/resOptions.txt";

    private RestaurantOptions emptyRestaurantOptions;

    //constructor
    public UserConsoleNew() {
        emptyRestaurantOptions = new RestaurantOptions();
    }

    //EFFECT: transitions user interface from console to GUI
    public void handleGUI() {
        AddRestaurantGUI.setUserConsole(this);
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                System.out.println("Please refer to the popup window");
                AddRestaurantGUI.createAndShowGUI();
            }
        });
    }

    //MODIFIES: this
    //EFFECT: creates a restaurant based on user inputs from GUI and adds it to a user made list
    public void addUserRes(String name, FoodType selectedft) {
        Restaurant restaurant;
        restaurant = new Restaurant(name);
        emptyRestaurantOptions.addRestaurant(selectedft, restaurant);
        AddRestaurantGUI.printToGUI("\n" + selectedft + " food: " + name);
    }


    //EFFECT: returns a random restaurant from user made restaurant options
    public void handleChooseFromUsers() {
        try {
            Restaurant chosen = emptyRestaurantOptions.chooseUserRestaurant();
            AddRestaurantGUI.printToGUI("\nWe've chosen: " + chosen.getName());
        } catch (Exception e) {
            AddRestaurantGUI.printToGUI("\nThere are no restaurants to chose from.");
        }
    }


    // MODIFIES: this
    // EFFECTS: saves state of restaurantOption to RES_FILE
    //code modified from TellerApp
    public void saveOptions() {
        try {
            Writer writer = new Writer(new File(RES_FILE));
            writer.write(emptyRestaurantOptions);
            writer.close();
            System.out.println("Restaurants saved to file " + RES_FILE);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("Unable to save restaurants to " + RES_FILE);
        }
    }

    //EFFECT: loads previously save restaurant options
    public void loadOptions() {
        try {
            emptyRestaurantOptions = Reader.readRestaurantOptions(new File(RES_FILE));
            System.out.println("Your options have been loaded!");
            AddRestaurantGUI.printRestoGUI(emptyRestaurantOptions);
        } catch (IOException e) {
            init();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes restaurant hashmap
    private void init() {
        emptyRestaurantOptions = new RestaurantOptions();
    }
}
