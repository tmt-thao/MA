import java.util.*;

public class MemeticAlgorithm {
    private List<Trip> trips;
    private TimeMatrix timeMatrix;

    private int populationSize;
    private int generations;
    private double mutationProbability;
    private double localSearchProbability;

    private Solution[] population;
    private Solution bestSolution;
    private Random random = new Random();

    public MemeticAlgorithm(List<Trip> trips, TimeMatrix timeMatrix, int populationSize, int generations, double mutationProbability, double localSearchProbability) {
        this.trips = trips;
        this.timeMatrix = timeMatrix;

        this.populationSize = populationSize;
        this.generations = generations;
        this.mutationProbability = mutationProbability;
        this.localSearchProbability = localSearchProbability;

        this.population = new Solution[populationSize];
        this.bestSolution = null;
    }

    public void initializePopulation() {
        Trip[] basePermutation = this.trips.toArray(new Trip[0]);
        for (int i = 0; i < this.populationSize; i++) {
            Trip[] newPermutation = basePermutation.clone();
            Collections.shuffle(Arrays.asList(newPermutation));
            this.population[i] = new Solution(newPermutation, this.timeMatrix);
            updateBestSolution(this.population[i]);
        }
    }

    private void updateBestSolution(Solution solution) {
        if (this.bestSolution == null || solution.getFitness() < this.bestSolution.getFitness()) {
            this.bestSolution = solution;
        }
    }

    private Solution tournamentSelection() {
        int tournamentSize = 3;
        Solution best = null;
        for (int i = 0; i < tournamentSize; i++) {
            Solution candidate = population[random.nextInt(populationSize)];
            if (best == null || candidate.getFitness() < best.getFitness()) {
                best = candidate;
            }
        }
        return best;
    }

    public Solution[] crossover(Solution parent1, Solution parent2) {
        int length = parent1.getTrips().length;
        int crossoverPoint1 = random.nextInt(length);
        int crossoverPoint2 = random.nextInt(length);
        if (crossoverPoint1 > crossoverPoint2) {
            int temp = crossoverPoint1;
            crossoverPoint1 = crossoverPoint2;
            crossoverPoint2 = temp;
        }

        Trip[] child1 = new Trip[length];
        Trip[] child2 = new Trip[length];

        HashMap<Trip, Trip> mapping1 = new HashMap<>();
        HashMap<Trip, Trip> mapping2 = new HashMap<>();

        for (int i = crossoverPoint1; i <= crossoverPoint2; i++) {
            child1[i] = parent2.getTrips()[i];
            child2[i] = parent1.getTrips()[i];
            mapping1.put(parent2.getTrips()[i], parent1.getTrips()[i]);
            mapping2.put(parent1.getTrips()[i], parent2.getTrips()[i]);
        }

        fillRemainingTripsPMX(child1, parent1.getTrips(), mapping1, crossoverPoint1, crossoverPoint2);
        fillRemainingTripsPMX(child2, parent2.getTrips(), mapping2, crossoverPoint1, crossoverPoint2);

        return new Solution[]{new Solution(child1, this.timeMatrix), new Solution(child2, this.timeMatrix)};
    }

    private void fillRemainingTripsPMX(Trip[] child, Trip[] parent, HashMap<Trip, Trip> mapping, int start, int end) {
        for (int i = 0; i < child.length; i++) {
            if (i >= start && i <= end) continue;
            Trip original = parent[i];
            while (mapping.containsKey(original)) {
                original = mapping.get(original);
            }
            child[i] = original;
        }
    }

    public Solution mutate(Solution solution) {
        if (random.nextDouble() < mutationProbability) {
            Trip[] trips = solution.getTrips().clone();
            int i = random.nextInt(trips.length);
            int j = random.nextInt(trips.length);
            Trip temp = trips[i];
            trips[i] = trips[j];
            trips[j] = temp;
            Solution s = new Solution(trips, this.timeMatrix);
            updateBestSolution(s);
            return s;
        }
        return solution;
    }

    public Solution localSearch(Solution solution) {
        if (random.nextDouble() < localSearchProbability) {
            Solution newSolution = solution;
            Trip[] trips = solution.getTrips().clone();
            for (int i = 0; i < trips.length - 1; i++) {
                Trip temp = trips[i];
                trips[i] = trips[i + 1];
                trips[i + 1] = temp;
                 newSolution = new Solution(trips, this.timeMatrix);
                if (newSolution.getFitness() < solution.getFitness()) {
                    updateBestSolution(newSolution);
                    
                }
                temp = trips[i];
                trips[i] = trips[i + 1];
                trips[i + 1] = temp;
            }
            return newSolution;
        }
        return solution;
    }

    public void evolve() {
        for (int gen = 0; gen < generations; gen++) {
            Solution[] newPopulation = new Solution[populationSize];
            newPopulation[populationSize - 1] = bestSolution;

            for (int i = 0; i < populationSize / 2; i++) {
                Solution parent1 = tournamentSelection();
                Solution parent2 = tournamentSelection();

                Solution[] children = crossover(parent1, parent2);
                Solution child1 = localSearch(mutate(children[0]));
                Solution child2 = localSearch(mutate(children[1]));

                newPopulation[2 * i] = child1;
                newPopulation[2 * i + 1] = child2;
            }
            population = newPopulation;
        }
    }

    public Solution getBestSolution() {
        return bestSolution;
    }
}
