package gui;

/**
 * @author Jacob Permansson
 *
 * This is the view. This is what the user sees and interacts with.
 */

import reader.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;

public class GUI extends JFrame{

   private JPanel panel;
    Controller controller;

    public GUI(Controller controller) {
        this.controller = controller;

        createUI();
        JFrame frame = new JFrame();
        System.out.println("Frame created.");
        frame.setTitle("Weather App");
        frame.setSize(500, 300);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(200, 200);
        System.out.println("Frame set up.");
        frame.add(panel);
        System.out.println("Panel added to frame.");
    }

    public void createUI() {

        panel = new JPanel();
        panel.setSize(400, 250);
        System.out.println("Panel created");

        //Set layout manager
        panel.setLayout(null);
        System.out.println("Layout set to null");
        //Create component

        JButton button = new JButton("Show weather!");
        JButton cacheButton = new JButton("Update cachetime");

        System.out.println("Button created.");
        JLabel cacheLabel = new JLabel("Set cache time: ");


        JTextField cache = new JTextField();


        JTextArea message = new JTextArea("");
        JTextArea weather = new JTextArea("");

        JComboBox hoursList;
        String[] stringHourList = new String[24];
        for (int i = 0; i < 24; i++) {
            stringHourList[i] = Integer.toString(i);
        }
        hoursList = new JComboBox(stringHourList);
        System.out.println("Hours list created.");

        JComboBox locationList;
        String[] locations = {"Skelleftea", "Kage", "Stockholm"};
        locationList = new JComboBox(locations);
        System.out.println("locationList created");

        JLabel locationLabel = new JLabel("Choose your location:");
        JLabel hoursLabel = new JLabel("What time?");
        Insets insets = panel.getInsets();

        //Add components to the panel
        panel.add(locationLabel);
        panel.add(locationList);
        panel.add(hoursLabel);
        panel.add(hoursList);
        panel.add(button);
        panel.add(cache);
        panel.add(cacheLabel);
        panel.add(cacheButton);
        panel.add(message);
        panel.add(weather);

        //Location settings

        Dimension size = locationLabel.getPreferredSize();
        locationLabel.setBounds(10 + insets.left, 15 + insets.top, size.width, size.height);

        size = locationList.getPreferredSize();
        locationList.setBounds(350 + insets.left, 10 + insets.top, size.width, size.height);

        //Hours settings

        size = hoursLabel.getPreferredSize();
        hoursLabel.setBounds(10 + insets.left, 50 + insets.top, size.width, size.height);

        size = hoursList.getPreferredSize();
        hoursList.setBounds(350 + insets.left, 45 + insets.top, size.width, size.height);

        //cache settings

        size = cacheLabel.getPreferredSize();
        cacheLabel.setBounds(10 + insets.left, 130 + insets.top, size.width, size.height);

        size = cache.getPreferredSize();
        cache.setBounds(130 + insets.left, 130 + insets.top, size.width+ 100, size.height);

        //Button settings

        size = button.getPreferredSize();
        button.setBounds(350 + insets.left, 130 + insets.top, size.width, size.height);

        // cache button settings

        size = cacheButton.getPreferredSize();
       cacheButton.setBounds(90 + insets.left, 160 + insets.top, size.width, size.height);

        // message settings

        size = message.getPreferredSize();
        message.setBounds(50 + insets.left, 220 + insets.top, size.width+ 200, size.height);

        // weather settings

        size = weather.getPreferredSize();
        weather.setBounds(350 + insets.left, 220 + insets.top, size.width+ 50, size.height);



        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("button pressed");
                weather.setText(controller.search(
                        locationList.getSelectedItem().toString(),
                        hoursList.getSelectedItem().toString()));
            }

        });
        cacheButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("cacheBbutton pressed");

                try {
                    controller.updateCacheTime(cache.getText());
                } catch (DateTimeParseException e) {
                    System.out.println("cachetime not correct format");
                    message.setText("Cachetime not correct format.");
                }
            }
        });
    }
    public static void main(String[] args) {
    }

}
