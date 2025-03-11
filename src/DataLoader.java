
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataLoader {

    public static HashMap<Integer, Integer> loadStopIdToIndex(String filename) throws Exception {
        HashMap<Integer, Integer> stopIdToIndex = new HashMap<>();
        int index = 0;

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            int stopId = Integer.parseInt(parts[0]);
            stopIdToIndex.put(stopId, index);
            index++;
        }

        reader.close();
        return stopIdToIndex;
    }

    public static List<Trip> loadTrips(String filename, HashMap<Integer, Integer> stopIdToIndex) throws Exception {
        List<Trip> trips = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            int index = Integer.parseInt(parts[0]);
            int startStop = stopIdToIndex.get(Integer.parseInt(parts[4]));
            int endStop = stopIdToIndex.get(Integer.parseInt(parts[5]));
            int startTime = Integer.parseInt(parts[6]);
            int endTime = Integer.parseInt(parts[7]);
            double consumption = Double.parseDouble(parts[10]);

            Trip trip = new Trip(index, startStop, endStop, startTime, endTime, consumption);
            trips.add(trip);
        }

        reader.close();
        return trips;
    }
}
