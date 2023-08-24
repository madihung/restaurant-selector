package ui;

import model.FoodType;
import model.Restaurant;
import model.RestaurantOptions;
import model.exceptions.NoRestaurantFound;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

//Methods that the user interacts with
public class UserConsoleDefault {

    private static final String RESTAURANT_OPTIONS = "options";
    private static final String NEW = "new";
    private static final String ADD_TO = "add";
    private static final String CHOOSE = "choose";
    private static final String QUIT_COMMAND = "quit";

    private static boolean runProgram;
    private static Scanner input;

    private RestaurantOptions resOptions;

    public UserConsoleDefault(RestaurantOptions resOptions) {
        input = new Scanner(System.in);
        runProgram = true;
        this.resOptions = resOptions;
    }

    //EFFECT: prints options the user can do
    private static void printOptions() {
        System.out.println("\nEnter '" + RESTAURANT_OPTIONS + "' to see the restaurant options we provide.");
        System.out.println("Enter '" + ADD_TO + "' to add to the list of provided restaurants.");
        System.out.println("Enter '" + CHOOSE + "' to randomly generate a restaurant from our list.");
        System.out.println("Enter '" + NEW + "' create or load your own restaurant options.");
        System.out.println("To quit at any time, enter '" + QUIT_COMMAND + "'.");
    }

    //EFFECTS: parses user input until user quits
    //NOTE: code taken from FitLifeGym project
    public void handleUserInput() {
        printOptions();
        String str;

        while (runProgram) {
            if (input.hasNext()) {
                str = input.nextLine();
                parseInput(str);
            }
        }
    }

    //EFFECTS: prints menu options and info depending on input str
    //NOTE: code based off method from fitLifeGym
    private void parseInput(String str) {
        if (str.length() > 0) {
            switch (str) {
                case RESTAURANT_OPTIONS:
                    printRestaurants();
                    break;
                case ADD_TO:
                    handlesAddRestoDefault();
                    break;
                case CHOOSE:
                    handleChooseFromDefault();
                    break;
                case NEW:
                    UserConsoleNew ucn = new UserConsoleNew();
                    ucn.handleGUI();
                    break;
                case QUIT_COMMAND:
                    runProgram = false;
                    break;
                default:
                    System.out.println("Sorry, I didn't understand that command. Please try again.");
                    break;
            }
        }
    }

    //EFFECTS: prints out the list of restaurants provided
    private void printRestaurants() {
        System.out.println("We have the following restaurant options: ");
        for (Map.Entry<FoodType, Set<Restaurant>> e : resOptions.getRestaurantOptions().entrySet()) {
            Set<Restaurant> restaurantSet = e.getValue();
            for (Restaurant res : restaurantSet) {
                System.out.println(e.getKey() + ": " + res.getName());
            }
        }
        printOptions();
    }

    //MODIFIES: this
    //EFFECT: Adds a user constructed restaurant to the default list
    private void handlesAddRestoDefault() {
        Restaurant restaurant;
        System.out.println("\nEnter the name of the restaurant you want to add,"
                + " or enter 'choose' for a restaurant from this list to be selected.");
        String name = input.nextLine();

        if (name.equalsIgnoreCase("choose")) {
            handleChooseFromDefault();
        } else {
            try {
                FoodType type = userFoodTypeInput();
                restaurant = new Restaurant(name);
                resOptions.addRestaurant(type, restaurant);
                System.out.println(name + ", " + type + " has been added!");

                handlesAddRestoDefault();
            } catch (IllegalArgumentException e) {
                System.out.println("Not a valid food type. Please try again.");
                handlesAddRestoDefault();
            }
        }
    }

    //EFFECT: helper method that returns the foodtype of the string the user enters
    private FoodType userFoodTypeInput() {
        System.out.println("Enter the type of food:");
        String userInput = input.nextLine();
        if (resOptions.isValidft(userInput)) {
            FoodType type = FoodType.valueOf(userInput);
            return type;
        } else {
            throw new IllegalArgumentException();
        }
    }

    //EFFECT: returns a random restaurant with user's selected food type from provided list
    private void handleChooseFromDefault() {
        System.out.println("Enter the type of food you want to eat: ");
        String type = input.nextLine();
        try {
            Restaurant selected = resOptions.chooseDefaultRestaurant(type);
            System.out.println("We've chosen: " + selected.getName());
            runProgram = false;
        } catch (Exception e) {
            System.out.print("We have no restaurant that fits your criteria. Please try again.\n");
            printOptions();
        }
    }

    //EFFECTS: stops receiving user input
    //NOTE: method taken from fitLifeGym project
    public void endProgram() {
        System.out.println("Quitting...");
        UserConsoleDefault.input.close();
    }
}
