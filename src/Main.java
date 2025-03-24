import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            HashMap<Integer, Integer> stopIdToIndex = DataLoader.loadStopIdToIndex("data/ZastavkyAll.csv");
            List<Trip> trips = DataLoader.loadTrips("data/spoje_id_T4_3.csv", stopIdToIndex);
            TimeMatrix timeMatrix = new TimeMatrix("data/matrixTime.txt", stopIdToIndex.size());

            MemeticAlgorithm memeticAlgorithm = new MemeticAlgorithm(trips, timeMatrix, 500, 1000, 0.1, 0.1);
            
            LocalDateTime startDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            System.out.println("Začiatok výpočtu: " + startDateTime.format(formatter));
            long startTime = System.nanoTime();

            memeticAlgorithm.initializePopulation();
            memeticAlgorithm.evolve();

            long endTime = System.nanoTime();
            LocalDateTime endDateTime = LocalDateTime.now();
            System.out.println("Koniec výpočtu: " + endDateTime.format(formatter));
            double duration = (endTime - startTime) / 1_000_000_000.0;

            Solution bestSolution = memeticAlgorithm.getBestSolution();
            for (Trip trip : bestSolution.getTrips()) {
                System.out.print(trip.getIndex() + " ");
            }
            
            System.out.println("\nPočet turnusov najlepšieho riešenia: " + bestSolution.getFitness());
            System.out.println("Trvanie výpočtu: " + duration + " sekúnd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
