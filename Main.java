import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Main {
    private static Formula1ChampionshipManager manager = new Formula1ChampionshipManager();
    private static DefaultTableModel tableModel;

    public static void main(String[] args) {
        // Sample data for testing
        manager.addDriver(new Formula1Driver("Hamilton", "UK", "Mercedes"));
        manager.addDriver(new Formula1Driver("Vettel", "Germany", "Ferrari"));
        manager.addDriver(new Formula1Driver("Verstappen", "Netherlands", "Red Bull"));
        
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Formula 1 Championship Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        // Table to display driver statistics
        String[] columnNames = {"Name", "Location", "Team", "1st Positions", "2nd Positions", "3rd Positions", "Points", "Races"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton displayButton = new JButton("Display Drivers");
        JButton sortByPointsButton = new JButton("Sort by Points");
        JButton sortByFirstPositionsButton = new JButton("Sort by 1st Positions");
        JButton generateRandomRaceButton = new JButton("Generate Random Race");
        JButton generateProbabilisticRaceButton = new JButton("Generate Probabilistic Race");

        buttonPanel.add(displayButton);
        buttonPanel.add(sortByPointsButton);
        buttonPanel.add(sortByFirstPositionsButton);
        buttonPanel.add(generateRandomRaceButton);
        buttonPanel.add(generateProbabilisticRaceButton);
        
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        // Button Actions
        displayButton.addActionListener(e -> displayDrivers());
        sortByPointsButton.addActionListener(e -> sortDriversByPoints());
        sortByFirstPositionsButton.addActionListener(e -> sortDriversByFirstPositions());
        generateRandomRaceButton.addActionListener(e -> generateRandomRace());
        generateProbabilisticRaceButton.addActionListener(e -> generateProbabilisticRace());

        frame.setVisible(true);
    }

    private static void displayDrivers() {
        tableModel.setRowCount(0);
        List<Formula1Driver> drivers = manager.getDrivers();
        for (Formula1Driver driver : drivers) {
            Object[] rowData = {
                    driver.getName(),
                    driver.getLocation(),
                    driver.getTeam(),
                    driver.getFirstPositions(),
                    driver.getSecondPositions(),
                    driver.getThirdPositions(),
                    driver.getPoints(),
                    driver.getRacesParticipated()
            };
            tableModel.addRow(rowData);
        }
    }

    private static void sortDriversByPoints() {
        manager.sortDriversByPoints();
        displayDrivers();
    }

    private static void sortDriversByFirstPositions() {
        manager.sortDriversByFirstPositions();
        displayDrivers();
    }

    private static void generateRandomRace() {
        manager.generateRandomRace();
        displayDrivers();
    }

    private static void generateProbabilisticRace() {
        manager.generateProbabilisticRace();
        displayDrivers();
    }
}
