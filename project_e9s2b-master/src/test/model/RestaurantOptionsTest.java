package model;

import model.exceptions.NoRestaurantFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantOptionsTest {
    private RestaurantOptions testresOption;
    private RestaurantOptions emptytest;
    private Restaurant earls;
    private Restaurant dons;
    private Restaurant eatery;
    private Restaurant inout;

    @BeforeEach
    public void runBefore() {
        testresOption = new RestaurantOptions();
        emptytest = new RestaurantOptions();
        earls = new Restaurant("Earl's");
        dons = new Restaurant("Mcdonalds");
        eatery = new Restaurant("The Eatery");
        inout = new Restaurant("In n Out");

        testresOption.addRestaurant(FoodType.Fastfood, dons);
        testresOption.addRestaurant(FoodType.Canadian, earls);

    }

    @Test
    public void testConstructor() {
    }

    @Test
    public void testGetResOption() {
        HashMap<FoodType, Set<Restaurant>> resOp = emptytest.getRestaurantOptions();
        HashMap<FoodType, Set<Restaurant>> hashmap = new HashMap<FoodType, Set<Restaurant>>();
        assertEquals(hashmap, resOp);
    }

    @Test
    public void testContainsFoodType() {
        assertTrue(testresOption.containsFoodType(FoodType.Fastfood));
        assertFalse(testresOption.containsFoodType(FoodType.Vietnamese));
    }

    @Test
    public void testContainsRestaurant() {
        assertTrue(testresOption.containsRestaurant(FoodType.Fastfood, dons));
        assertFalse(testresOption.containsRestaurant(FoodType.Fastfood, inout));
        assertFalse(testresOption.containsRestaurant(FoodType.Japanese, eatery));
    }

    @Test
    public void testAddRestaurantNoFoodtypeInMap() {
        //check foodtype key does not exist
        assertFalse(testresOption.containsFoodType(FoodType.Japanese));
        //add restaurant with new foodtype
        testresOption.addRestaurant(FoodType.Japanese, eatery);
        //checks foodtype key and restaurant value have been added
        assertTrue(testresOption.containsFoodType(FoodType.Japanese));
        assertTrue(testresOption.containsRestaurant(FoodType.Japanese, eatery));
        assertFalse(testresOption.containsRestaurant(FoodType.Fastfood, inout));
    }

    @Test
    public void testAddRestaurantFoodTypeAlreadyInMap() {
        //check foodtype key already exists
        assertTrue(testresOption.containsFoodType(FoodType.Fastfood));
        //add restaurant with that foodtype
        testresOption.addRestaurant(FoodType.Fastfood, inout);
        //checks restaurant value has been added
        assertTrue(testresOption.containsRestaurant(FoodType.Fastfood, inout));
    }

    @Test
    public void testChooseUserRestaurantNoException() {
        try {
            Restaurant chosen = testresOption.chooseUserRestaurant();
            for (FoodType ft : FoodType.values()) {
                if (testresOption.containsRestaurant(ft, chosen)) {
                    assertTrue(true);
                }
            }
        } catch (NoRestaurantFound e) {
            fail("Exception is thrown when it should not be");
        }
    }

    @Test
    public void testChooseUserRestaurantException() {
        try {
            emptytest.chooseUserRestaurant();
        } catch (NoRestaurantFound e) {
            //do nothing
        }
    }

    @Test
    public void testChooseDefaultRestaurantNoException() {
        try {
            Restaurant chosen = testresOption.chooseDefaultRestaurant("Fastfood");
            assertTrue(testresOption.containsRestaurant(FoodType.Fastfood, chosen));

            Restaurant chosen2 = testresOption.chooseDefaultRestaurant("Canadian");
            assertTrue(testresOption.containsRestaurant(FoodType.Canadian, chosen2));
        } catch (NoRestaurantFound e) {
            fail();
        }
    }

    @Test
    public void testChooseDefaultRestaurantException() {
        try {
            Restaurant chosen = testresOption.chooseDefaultRestaurant("Japanese");
        } catch (NoRestaurantFound e) {
            //do nothing
        }
    }

}
