package flightapp;
import flightapp.controller.FlightController;
import flightapp.view.FlightTest;
import flightapp.view.Form1;

import javax.swing.*;


public class MainApp {
    public static void main(String[] args) {
        //Create static instance of controller
        //This way, we don't have to create a new file to store FlightTest data, because we're using the same controller(sharing for both FlightTest and GUI form)
        FlightController controller = new FlightController();

        FlightTest.consoleApp(controller);

        SwingUtilities.invokeLater(() -> {
            Form1 form1 = new Form1(controller);
            form1.setVisible(true);
        });

    }
}
