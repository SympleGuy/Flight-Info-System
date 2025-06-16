package flightapp.view;

import flightapp.controller.FlightController;
import flightapp.model.Flight;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class Form1 extends JFrame {
    // Create static instance of controller
    private final FlightController controller;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    //Swing components
    private JTable flightTable;
    private DefaultTableModel tableModel;
    private JButton displayButton;
    private JComboBox<String> sortComboBox;
    private JTextField fromField;
    private JTextField toField;

    public Form1(FlightController controller) {
        this.controller = controller;

        //Frame setup
        setTitle("Flight Information System");
        setSize(1200,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();

        addEventListeners();
        //Load default sortList
        updateTableData();
    }

    private void initComponents() {
        // Use BorderLayout for the main frame layout.
        setLayout(new BorderLayout(10, 10)); // 10px horizontal and vertical gaps

        //Top Panel (for controls)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Left-aligned FlowLayout
        topPanel.add(new JLabel("From:"));
        fromField = new JTextField(12);
        topPanel.add(fromField);

        topPanel.add(new JLabel("To:"));
        toField = new JTextField(12);
        topPanel.add(toField);

        displayButton = new JButton("Filter/Refresh");
        topPanel.add(displayButton);

        topPanel.add(new JSeparator(SwingConstants.VERTICAL));

        topPanel.add(new JLabel("Sort by:"));
        String[] sortOptions = {"Flight Number", "Departure Time", "Airfare", "Distance"};
        sortComboBox = new JComboBox<>(sortOptions);
        topPanel.add(sortComboBox);

        add(topPanel, BorderLayout.NORTH);

        //Center Panel (data table)
        //Define column headers for the table.
        String[] columnNames = {"Airline", "Flight Number", "Origin", "Destination", "Departure Time", "Arrival Time", "Duration", "Airfare", "Seats", "Distance"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // Create the JTable with the defined table model.
        flightTable = new JTable(tableModel);
        flightTable.setFillsViewportHeight(true); // Makes the table use the entire height of its container
        flightTable.setRowHeight(25);

        // Create a JScrollPane to hold the table. This adds scroll bars if the content is too large.
        add(new JScrollPane(flightTable), BorderLayout.CENTER);
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void addEventListeners() {
        ActionListener updateAction = e -> updateTableData();
        displayButton.addActionListener(updateAction);
        sortComboBox.addActionListener(updateAction);
    }

    private void updateTableData(){
        String from = fromField.getText();
        String to = toField.getText();
        String sortBy = ((String) sortComboBox.getSelectedItem()).toLowerCase().replace(" ", "");

        List<Flight> flights = controller.getFlights();
        if (!from.isEmpty()) {
            flights.removeIf(f -> !f.getFlightOrigin().equalsIgnoreCase(from));
        }
        if (!to.isEmpty()) {
            flights.removeIf(f -> !f.getFlightDestination().equalsIgnoreCase(to));
        }

        switch(sortBy) {
            case "airfare":
                flights.sort(Comparator.comparing(Flight::getAirfare));
                break;
            case "departuretime":
                flights.sort(Comparator.comparing(Flight::getDepartureTime));
                break;
            case "distance":
                flights.sort(Comparator.comparing(Flight::getDistance));
                break;
            default:
                flights.sort(Comparator.comparing(Flight::getFlightNumber));
                break;
        }


        populateTable(flights);
    }
    private void populateTable(List<Flight> flights) {
        tableModel.setRowCount(0); //Remove old data

        if (flights == null) return;

        for (Flight flight : flights) {
            Object[] rowData = {
                    flight.getAirlineName(),
                    flight.getFlightNumber(),
                    flight.getFlightOrigin(),
                    flight.getFlightDestination(),
                    flight.getDepartureTime().format(formatter),
                    flight.getArrivalTime().format(formatter),
                    flight.getDuration(),
                    String.format("%.2f", flight.getAirfare()),
                    flight.getAvailableSeat(),
                    flight.getDistance()
            };
            tableModel.addRow(rowData);
        }
    }

}
