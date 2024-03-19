package TTP.Algorithms;

import TTP.TTPInstance;
import TTP.TTPSolution;

import java.util.*;

public class TabuAlgorithm extends ExtendedGreedyAlgo{


    private final int[][] tabuList;
    private int tabuTenure = 3;

    private int iterations;

    public TabuAlgorithm(TTPInstance instance) {
        super(instance);
        this.tabuList = new int[instance.getDimension()][instance.getDimension()];
    }

    @Override
    public void initialize(int iterations) {
        this.iterations = iterations;
        TTPSolution solution = new TTPSolution();
        solution.setItems(new ArrayList<>(Collections.nCopies(instance.getDimension(), -1)));
        int minKeyNode = instance.getNodes().keySet().stream().findFirst().orElse(0);
        int randomStartingNode = random.nextInt(minKeyNode,instance.getDimension());
        solution.setRoute(createGreedyRoute(randomStartingNode));
        stealTheMostValuableItems(solution);
        solution.setFitness(fitness(solution));
        population.add(solution);
    }

    @Override
    public void execute() {
        executeTabuSearch();
    }

    public void executeTabuSearch() {
        if (population.isEmpty()) {
            System.err.println("Population is empty.");
            return;
        }

        TTPSolution currentSolution = population.get(0);
        setBestSolution(new TTPSolution(currentSolution));

        for (int i = 0; i < iterations; i++) {
            TTPSolution neighborSolution = findBestNeighbor(currentSolution);

            if (neighborSolution.getFitness() > bestSolution.getFitness()) {
                bestSolution = new TTPSolution(neighborSolution);
            }

            currentSolution = neighborSolution;
            updateTabuListAfterIteration();
//            System.out.println("Iteration: " + i + " Best fitness: " + bestSolution.getFitness() + " Current fitness: " + currentSolution.getFitness());
        }

        setBestSolution(bestSolution);
    }

    private void updateTabuListAfterIteration() {
        for (int i = 0; i < instance.getDimension(); i++) {
            for (int j = i+1; j < instance.getDimension(); j++) {
                if (tabuList[i][j] > 0) {
                    tabuList[i][j]--;
                }
            }
        }
    }


    private TTPSolution findBestNeighbor(TTPSolution solution){
        TTPSolution bestNeighbor = new TTPSolution(solution);
        double bestNeighborFitness = Double.NEGATIVE_INFINITY;
        int bestI = -1;
        int bestJ = -1;

        for (int i = 0; i < instance.getDimension(); i++) {
            for (int j = i + 1; j < instance.getDimension(); j++) {
                if (isTabu(i, j)) {
                    continue;
                }

                TTPSolution neighbor = new TTPSolution(solution);
                mutateSwap(neighbor, i, j);
                neighbor.setFitness(fitness(neighbor));

//                Here we adjust the fitness of the neighbor based on the frequency of the swap count
                if (neighbor.getFitness() - 0.1*neighbor.getFitness()*getFrequency(i,j)> bestNeighborFitness) {
                    bestNeighbor = neighbor;
                    bestNeighborFitness = neighbor.getFitness();
                    bestJ = j;
                    bestI = i;
                }
            }
        }

        updateTabuList(bestI, bestJ);
        return bestNeighbor;
    }

    private void updateTabuList(int i, int j){
        tabuList[i][j] = tabuTenure;
        tabuList[j][i] +=1;
    }


    private boolean isTabu(int i, int j){
        return tabuList[i][j] > 0;
    }

    private int getFrequency(int i, int j){
        return tabuList[j][i];
    }


    private void mutateItemsAtNode(TTPSolution solution, int index) {
        int nodeId = solution.getRoute().get(index);
        List<Integer> availableItems = instance.getNodes().get(nodeId).getItems();

        if (availableItems.isEmpty()) return; // No items to add or remove at this node

        int currentItemIndex = solution.getItems().get(index);
        boolean itemPresent = currentItemIndex != -1;

        if (itemPresent) {
            // If an item is present, remove it
            solution.updateTotalWeight(-instance.getItems().get(currentItemIndex).getWeight());
            solution.getItems().set(index, -1); // Remove the item
        } else {
            // If no item is present, add one if possible
            for (Integer itemIndex : availableItems) {
                double itemWeight = instance.getItems().get(itemIndex).getWeight();
                if (solution.getTotalWeight() + itemWeight <= instance.getCapacityOfKnapsack()) {
                    solution.updateTotalWeight(itemWeight);
                    solution.getItems().set(index, itemIndex);
                    break;
                }
            }
        }
    }
    private void mutateSwap(TTPSolution solution, int i, int j) {
        Collections.swap(solution.getRoute(), i, j);
        Collections.swap(solution.getItems(), i, j);

        mutateItemsAtNode(solution, i);
        mutateItemsAtNode(solution, j);
    }


}
