
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ui;

import model.FoodType;
import model.Restaurant;
import model.RestaurantOptions;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import java.util.Set;

public class AddRestaurantGUI extends JPanel implements ActionListener {
    protected static JTextArea textArea;
    private static UserConsoleNew userConsoleNew;
    private static final String AUDIO_FILE = "./data/button_push_audio.wav";

    protected JTextField resEntry;
    protected JLabel resLabel;
    protected JLabel ftLabel;
    protected JComboBox<FoodType> ftCombo;
    protected JButton chooseButton;
    protected JButton saveButton;
    protected JButton loadButton;
    protected JLabel loadLabel;
    private FoodType selectedft;

    public AddRestaurantGUI() {
        super(new GridBagLayout());

        //initialize components
        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);

        ftCombo = new JComboBox<>(FoodType.values());
        ftCombo.addActionListener(this);
        ftLabel = new JLabel("Select which food type:");

        resEntry = new JTextField(20);
        resEntry.addActionListener(this);
        resLabel = new JLabel("Enter the Restaurant name:");

        chooseButton = new JButton("Choose a restaurant");
        chooseButton.addActionListener(this);
        saveButton = new JButton("Save List");
        saveButton.addActionListener(this);
        loadButton = new JButton("Load");
        loadButton.addActionListener(this);
        loadLabel = new JLabel("Load your previously saved list:");

        addComponents();
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Add a new restaurant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new AddRestaurantGUI());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    //setter
    public static void setUserConsole(UserConsoleNew ucn) {
        userConsoleNew = ucn;
    }

    //EFFECT: prints string to text area
    public static void printToGUI(String text) {
        textArea.append(text);
    }

    //prints text from loaded file to text area
    public static void printRestoGUI(RestaurantOptions loadedList) {
        for (Map.Entry<FoodType, Set<Restaurant>> e : loadedList.getRestaurantOptions().entrySet()) {
            Set<Restaurant> restaurantSet = e.getValue();
            for (Restaurant res : restaurantSet) {
                textArea.append("\n" + e.getKey() + ": " + res.getName());
            }
        }
    }

    //add initialized components to JFrame
    private void addComponents() {
        JScrollPane scrollPane = new JScrollPane(textArea);
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;

        add(ftLabel, c);
        add(ftCombo, c);
        add(resLabel, c);
        add(resEntry, c);
        add(scrollPane, c);
        add(chooseButton, c);
        add(saveButton, c);
        add(loadLabel, c);
        add(loadButton, c);

    }

    //EFFECT: directs GUI inputs to UserConsole after action is performed
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == ftCombo) {
            selectedft = (FoodType) ftCombo.getSelectedItem();

        } else if (e.getSource() == resEntry) {
            String name = resEntry.getText();
            //Makes sure the new text is visible, even if there was a selection in the text area.
            textArea.setCaretPosition(textArea.getDocument().getLength());
            resEntry.selectAll();

            userConsoleNew.addUserRes(name, selectedft);

        } else if (e.getSource() == chooseButton) {
            userConsoleNew.handleChooseFromUsers();

        } else if (e.getSource() == loadButton) {
            userConsoleNew.loadOptions();

        } else if (e.getSource() == saveButton) {
            userConsoleNew.saveOptions();
            playSound("button_push_audio.wav");

        } else {
            System.out.println("Please enter a Restaurant name.");
        }
    }

    public void playSound(String soundName) {
        try {
            File audio = new File(AUDIO_FILE);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

}


