package persistence;

import model.FoodType;
import model.Restaurant;
import model.RestaurantOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WriterTest {
    private static final String TEST_FILE = "./data/testResOptions.txt";
    private Writer testWriter;
    private RestaurantOptions restaurantOptions;
    private Restaurant taqueria;
    private Restaurant chipotle;
    private Restaurant earls;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));

        restaurantOptions = new RestaurantOptions();
        taqueria = new Restaurant("La Taqueria");
        chipotle = new Restaurant("Chipotle");
        earls = new Restaurant("Earl's");

        restaurantOptions.addRestaurant(FoodType.Mexican, taqueria);
        restaurantOptions.addRestaurant(FoodType.Mexican, chipotle);
        restaurantOptions.addRestaurant(FoodType.Canadian, earls);
    }

    @Test
    void testWriteAccounts() {
        // save RestaurantOptions to file
        testWriter.write(restaurantOptions);
        testWriter.close();

        // now read them back in and verify that the RestaurantOption has the expected values
        try {
            RestaurantOptions resOption = Reader.readRestaurantOptions(new File(TEST_FILE));

            assertTrue(resOption.containsFoodType(FoodType.Canadian));
            assertTrue(resOption.containsFoodType(FoodType.Mexican));
            assertTrue(resOption.containsRestaurant(FoodType.Canadian, earls));
            assertTrue(resOption.containsRestaurant(FoodType.Mexican, chipotle));
            assertTrue(resOption.containsRestaurant(FoodType.Mexican, taqueria));

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
