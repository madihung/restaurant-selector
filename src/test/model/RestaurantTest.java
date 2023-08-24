package model;

import model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantTest {
    private Restaurant testres;

    @BeforeEach
    public void runBefore() {
        testres = new Restaurant("Denny's");
    }

    @Test
    public void testConstructor() {
        Restaurant testres2 = new Restaurant("In n Out");
        assertEquals("In n Out", testres2.getName());
    }

    @Test
    public void testgetName() {
        assertEquals("Denny's", testres.getName());
    }
}
