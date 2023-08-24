package ui;

import model.Restaurant;
import model.RestaurantOptions;
import model.exceptions.NoRestaurantFound;

import javax.swing.*;

import static model.FoodType.*;

public class Main extends JFrame {
    private static RestaurantOptions defaultRestaurants;
    private static UserConsoleDefault userConsoleDefault;

    public static void main(String[] args) throws NoRestaurantFound {
        defaultRestaurants = new RestaurantOptions();
        userConsoleDefault = new UserConsoleDefault(defaultRestaurants);

        loadRestaurants();

        System.out.println("Hello!");
        userConsoleDefault.handleUserInput();
        userConsoleDefault.endProgram();
        System.out.println("Enjoy your meal!");
    }

    /**
     * Load the default restaurants in
     */
    public static void loadRestaurants() {
        defaultRestaurants.addRestaurant(Canadian, new Restaurant("Cactus Club"));
        defaultRestaurants.addRestaurant(Canadian, new Restaurant("Earl's"));
        defaultRestaurants.addRestaurant(Japanese, new Restaurant("The Eatery"));
        defaultRestaurants.addRestaurant(Japanese, new Restaurant("Shizenya"));
        defaultRestaurants.addRestaurant(Japanese, new Restaurant("Miku"));
        defaultRestaurants.addRestaurant(Japanese, new Restaurant("Ramen Dambo"));
        defaultRestaurants.addRestaurant(Malaysian, new Restaurant("Banana Leaf"));
        defaultRestaurants.addRestaurant(Vietnamese, new Restaurant("Gold Train Express"));
        defaultRestaurants.addRestaurant(Mexican, new Restaurant("Chipotle"));
        defaultRestaurants.addRestaurant(Mexican, new Restaurant("La Taqueria"));
        defaultRestaurants.addRestaurant(Korean, new Restaurant("Sura"));
        defaultRestaurants.addRestaurant(Italian, new Restaurant("Trattoria"));
    }

}
