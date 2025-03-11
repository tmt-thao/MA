import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TimeMatrix {

    private int[][] matrix;

    public TimeMatrix(String filename, int size) throws IOException {
        this.matrix = new int[size][size];
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        int row = 0;

        while ((line = br.readLine()) != null && row < size) {
            String[] parts = line.split(";");
            for (int col = 0; col < size; col++) {
                this.matrix[row][col] = Integer.parseInt(parts[col]);
            }
            row++;
        }
        
        br.close();
    }

    public int getTravelTime(int stopA, int stopB) {
        return this.matrix[stopA][stopB];
    }
}
