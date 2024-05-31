import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Formula1ChampionshipManager implements ChampionshipManager {
    private List<Formula1Driver> drivers;

    public Formula1ChampionshipManager() {
        this.drivers = new ArrayList<>();
    }

    public List<Formula1Driver> getDrivers() {
        return drivers;
    }

    @Override
    public void addDriver(Formula1Driver driver) {
        drivers.add(driver);
    }

    @Override
    public void removeDriver(String driverName) {
        drivers.removeIf(driver -> driver.getName().equals(driverName));
    }

    @Override
    public void displayDriverStats(String driverName) {
        for (Formula1Driver driver : drivers) {
            if (driver.getName().equals(driverName)) {
                System.out.println(driver);
                return;
            }
        }
        System.out.println("Driver not found.");
    }

    @Override
    public void displayAllDrivers() {
        for (Formula1Driver driver : drivers) {
            System.out.println(driver);
        }
    }

    @Override
    public void addRaceResult(String driverName, int position) {
        for (Formula1Driver driver : drivers) {
            if (driver.getName().equals(driverName)) {
                switch (position) {
                    case 1:
                        driver.incrementFirstPositions();
                        driver.addPoints(25);
                        break;
                    case 2:
                        driver.incrementSecondPositions();
                        driver.addPoints(18);
                        break;
                    case 3:
                        driver.incrementThirdPositions();
                        driver.addPoints(15);
                        break;
                    case 4:
                        driver.addPoints(12);
                        break;
                    case 5:
                        driver.addPoints(10);
                        break;
                    case 6:
                        driver.addPoints(8);
                        break;
                    case 7:
                        driver.addPoints(6);
                        break;
                    case 8:
                        driver.addPoints(4);
                        break;
                    case 9:
                        driver.addPoints(2);
                        break;
                    case 10:
                        driver.addPoints(1);
                        break;
                    default:
                        System.out.println("Position not in the top 10. No points awarded.");
                }
                driver.incrementRacesParticipated();
                return;
            }
        }
        System.out.println("Driver not found.");
    }

    public void sortDriversByPoints() {
        drivers.sort(Comparator.comparingInt(Formula1Driver::getPoints).reversed());
    }

    public void sortDriversByFirstPositions() {
        drivers.sort(Comparator.comparingInt(Formula1Driver::getFirstPositions).reversed());
    }

    public void generateRandomRace() {
        Random rand = new Random();
        List<Integer> positions = new ArrayList<>();
        for (int i = 1; i <= drivers.size(); i++) {
            positions.add(i);
        }
        Collections.shuffle(positions);

        for (int i = 0; i < drivers.size(); i++) {
            addRaceResult(drivers.get(i).getName(), positions.get(i));
        }
    }

    public void generateProbabilisticRace() {
        Random rand = new Random();
        List<Integer> positions = new ArrayList<>();
        for (int i = 1; i <= drivers.size(); i++) {
            positions.add(i);
        }
        Collections.shuffle(positions);

        List<Integer> finalPositions = new ArrayList<>(positions);
        for (int i = 0; i < drivers.size(); i++) {
            int startPos = positions.get(i);
            double chance = rand.nextDouble();
            if (startPos == 1 && chance < 0.4) {
                finalPositions.set(i, 1);
            } else if (startPos == 2 && chance < 0.3) {
                finalPositions.set(i, 1);
            } else if ((startPos == 3 || startPos == 4) && chance < 0.1) {
                finalPositions.set(i, 1);
            } else if (startPos >= 5 && startPos <= 9 && chance < 0.02) {
                finalPositions.set(i, 1);
            }
        }

        for (int i = 0; i < drivers.size(); i++) {
            addRaceResult(drivers.get(i).getName(), finalPositions.get(i));
        }
    }
}
