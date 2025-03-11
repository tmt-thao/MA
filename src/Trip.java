public class Trip {
    private int index;
    private int startStop;
    private int endStop;
    private int startTime;
    private int endTime;
    private double consumption;

    public Trip(int index, int startStop, int endStop, int startTime, int endTime, double consumption) {
        this.index = index;
        this.startStop = startStop;
        this.endStop = endStop;
        this.startTime = startTime;
        this.endTime = endTime;
        this.consumption = consumption;
    }

    public int getIndex() {
        return index;
    }

    public int getStartStop() {
        return startStop;
    }

    public int getEndStop() {
        return endStop;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public double getConsumption() {
        return consumption;
    }
}
