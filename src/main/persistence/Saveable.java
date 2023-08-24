package persistence;

import model.FoodType;

import java.io.PrintWriter;

// Represents data that can be saved to file
//code taken from TellerApp
public interface Saveable {
    // MODIFIES: printWriter
    // EFFECTS: writes the saveable to printWriter
    void save(PrintWriter printWriter);
}