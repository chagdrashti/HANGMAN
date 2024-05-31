public class Formula1Driver extends Driver {
    private int firstPositions;
    private int secondPositions;
    private int thirdPositions;
    private int points;
    private int racesParticipated;

    public Formula1Driver(String name, String location, String team) {
        super(name, location, team);
        this.firstPositions = 0;
        this.secondPositions = 0;
        this.thirdPositions = 0;
        this.points = 0;
        this.racesParticipated = 0;
    }

    public int getFirstPositions() {
        return firstPositions;
    }

    public int getSecondPositions() {
        return secondPositions;
    }

    public int getThirdPositions() {
        return thirdPositions;
    }

    public int getPoints() {
        return points;
    }

    public int getRacesParticipated() {
        return racesParticipated;
    }

    public void incrementFirstPositions() {
        this.firstPositions++;
    }

    public void incrementSecondPositions() {
        this.secondPositions++;
    }

    public void incrementThirdPositions() {
        this.thirdPositions++;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void incrementRacesParticipated() {
        this.racesParticipated++;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", firstPositions=" + firstPositions +
                ", secondPositions=" + secondPositions +
                ", thirdPositions=" + thirdPositions +
                ", points=" + points +
                ", racesParticipated=" + racesParticipated;
    }
}
