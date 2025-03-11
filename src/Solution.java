public class Solution {
    private Trip[] trips;
    private int fitness;
    private static TimeMatrix timeMatrix;

    public Solution(Trip[] trips, TimeMatrix timeMatrix) {
        this.trips = trips;
        Solution.timeMatrix = timeMatrix;
        evaluateFitness();
    }

    public Trip[] getTrips() {
        return trips;
    }

    public int getFitness() {
        return fitness;
    }

    public void evaluateFitness() {
        this.fitness = 1;

        for (int i = 0; i < this.trips.length - 1; i++) {
            Trip currentTrip = this.trips[i];
            Trip nextTrip = this.trips[i + 1];

            int currentStop = currentTrip.getEndStop();
            int nextStop = nextTrip.getStartStop();

            int travelTime = Solution.timeMatrix.getTravelTime(currentStop, nextStop);
            if (currentTrip.getEndTime() + travelTime > nextTrip.getStartTime()) {
                this.fitness++;
            }
        }
    }
}
