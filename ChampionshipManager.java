public interface ChampionshipManager {
    void addDriver(Formula1Driver driver);
    void removeDriver(String driverName);
    void displayDriverStats(String driverName);
    void displayAllDrivers();
    void addRaceResult(String driverName, int position);
}
