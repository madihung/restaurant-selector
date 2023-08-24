package model;

import model.exceptions.NoRestaurantFound;
import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.*;

/**
 * Represents restaurants the application will choose from
 * Restaurants (values) are organized by FoodType (key)
 */
public class RestaurantOptions implements Saveable {
    private Random ran = new Random();
    private HashMap<FoodType, Set<Restaurant>> restaurantOptions;
    private FoodType ft;
    private Restaurant chosen = null;

    //constructor
    public RestaurantOptions() {
        restaurantOptions = new HashMap<>();
    }

    //EFFECT: getter
    public HashMap<FoodType, Set<Restaurant>> getRestaurantOptions() {
        return restaurantOptions;
    }

    //MODIFIES: this
    //EFFECT: remembers a FoodType if it is not already in the hashmap and adds a restaurant to the set
    public void addRestaurant(FoodType foodType, Restaurant restaurant) {
        Set<Restaurant> restaurantSet = new HashSet<>();
        if (!restaurantOptions.containsKey(foodType)) {
            restaurantSet.add(restaurant);
            restaurantOptions.put(foodType, restaurantSet);
        } else {
            restaurantSet = restaurantOptions.get(foodType);
            restaurantSet.add(restaurant);
        }
    }

    //EFFECT: returns a randomly selected restaurant from the user made restaurant hash map
    public Restaurant chooseUserRestaurant() throws NoRestaurantFound {
        if (restaurantOptions.isEmpty()) {
            throw new NoRestaurantFound();
        } else {
            //randomly select a FoodType value until one in the user made list is picked
            int pick = new Random().nextInt(FoodType.values().length);
            FoodType randomft = FoodType.values()[pick];

            while (!restaurantOptions.containsKey(randomft)) {
                int pick2 = new Random().nextInt(FoodType.values().length);
                randomft = FoodType.values()[pick2];
            }
            chosen = randomRestaurant(randomft);
        }
        return chosen;
    }

    //EFFECT: returns a randomly selected restaurant from the provided restaurant hash map
    public Restaurant chooseDefaultRestaurant(String ft) throws NoRestaurantFound {
        FoodType pickedft = FoodType.valueOf(ft);
        if (!restaurantOptions.containsKey(pickedft)) {
            throw new NoRestaurantFound();
        }
        return randomRestaurant(pickedft);
    }

    public Restaurant randomRestaurant(FoodType ft) {
        Set<Restaurant> restaurantOfType = restaurantOptions.get(ft);
        int random = ran.nextInt(restaurantOfType.size());
        int i = 0;
        for (Restaurant res : restaurantOfType) {
            if (i == random) {
                chosen = res;
            }
            i++;
        }
        return chosen;
    }

    //EFFECT: returns true if the string input is a valid foodtype, false otherwise
    public boolean isValidft(String input) {
        for (FoodType ft : FoodType.values()) {
            if (ft.name().equals(input)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void save(PrintWriter printWriter) {
        for (Map.Entry<FoodType, Set<Restaurant>> mapEntry : restaurantOptions.entrySet()) {
            printWriter.print(mapEntry.getKey());
            for (Restaurant res : mapEntry.getValue()) {
                printWriter.print(Reader.DELIMITER);
                printWriter.print(res.getName());
            }
            printWriter.println("");
        }
    }


    /**For testing**/

    //EFFECT: returns true if a restaurant option contains parameter food type, false otherwise
    public boolean containsFoodType(FoodType ft) {
        return restaurantOptions.containsKey(ft);
    }

    //EFFECT: returns true if the restaurant option contains the given restaurant, false otherwise
    public boolean containsRestaurant(FoodType ft, Restaurant res) {
        if (containsFoodType(ft)) {
            Set<Restaurant> resSet = restaurantOptions.get(ft);
            for (Restaurant restaurant : resSet) {
                if (res.getName().equals(restaurant.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
