package persistence;

import model.FoodType;
import model.Restaurant;
import model.RestaurantOptions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTests {
    private Restaurant taqueria = new Restaurant("La Taqueria");
    private Restaurant earls = new Restaurant("Earl's");
    private Restaurant cactus = new Restaurant("Cactus Club");
    private Restaurant dons = new Restaurant("McDonalds");
    private Restaurant aw = new Restaurant("A & W");

    @Test
    public void testParseAccountsFile1() {
        File file1 = new File("./data/testResOptionFile1.txt");
        try {
            RestaurantOptions resOptions = Reader.readRestaurantOptions(file1);

            assertTrue(resOptions.containsFoodType(FoodType.Canadian));
            assertTrue(resOptions.containsFoodType(FoodType.Mexican));
            assertTrue(resOptions.containsRestaurant(FoodType.Canadian, earls));
            assertTrue(resOptions.containsRestaurant(FoodType.Canadian, cactus));
            assertTrue(resOptions.containsRestaurant(FoodType.Mexican, taqueria));
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testParseAccountsFile2() {
        File file2 = new File("./data/testResOptionFile2.txt");
        try {
            RestaurantOptions resOptions = Reader.readRestaurantOptions(file2);

            assertTrue(resOptions.containsFoodType(FoodType.Canadian));
            assertTrue(resOptions.containsFoodType(FoodType.Fastfood));
            assertTrue(resOptions.containsRestaurant(FoodType.Canadian, earls));
            assertTrue(resOptions.containsRestaurant(FoodType.Fastfood, dons));
            assertTrue(resOptions.containsRestaurant(FoodType.Fastfood, aw));
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testParseAccountExeption() {
        File file3 = new File("./data/testResOptionFile2.txt");
        try{
            RestaurantOptions resOptions = Reader.readRestaurantOptions(file3);
        } catch (IOException e) {
            //do nothing
        }
    }
}
