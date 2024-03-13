package TTP.Optimizer;

import TTP.Algorithms.EAlgorithm;
import TTP.TTPInstance;
import TTP.TTPSolution;

import java.util.*;

public class RealOptimizer extends Optimizer {


    public RealOptimizer(EAlgorithm algorithm) {
        super(algorithm);
    }

    @Override
    protected TTPSolution mutate(TTPSolution solution) {
        int minKeyNode = algorithm.getInstance().getNodes().keySet().stream().findFirst().orElse(0);


        int firstIndex = random.nextInt(minKeyNode, solution.getRoute().size() - 1);
        int secondIndex = random.nextInt(minKeyNode, solution.getRoute().size() - 1);

        Collections.swap(solution.getRoute(), firstIndex, secondIndex);
        Collections.swap(solution.getItems(), firstIndex, secondIndex);

        if (getMUTATION_PROBABILITY() > random.nextFloat()) {
            mutateItemsAtNode(solution, firstIndex);
            mutateItemsAtNode(solution, secondIndex);
        }

        return solution;
    }

    private void mutateItemsAtNode(TTPSolution solution, int index) {
        TTPInstance instance = algorithm.getInstance();
        int nodeId = solution.getRoute().get(index);
        List<Integer> availableItems = instance.getNodes().get(nodeId).getItems();

        if (availableItems.isEmpty()) return; // No items to add or remove at this node

        int currentItemIndex = solution.getItems().get(index);
        boolean itemPresent = currentItemIndex != -1;

        if (itemPresent) {
            // If an item is present, consider removing it
            solution.updateTotalWeight(-instance.getItems().get(currentItemIndex).getWeight());
            solution.getItems().set(index, -1); // Remove the item
        } else {

            for (Integer itemIndex : availableItems) {
                double itemWeight = instance.getItems().get(itemIndex).getWeight();
                if (solution.getTotalWeight() + itemWeight <= algorithm.getInstance().getCapacityOfKnapsack()) {
                    solution.updateTotalWeight(itemWeight);
                    solution.getItems().set(index, itemIndex);
                    break;
                }
            }
        }
    }

    //    XO1 crossover
    @Override
    protected TTPSolution crossover(TTPSolution solution1, TTPSolution solution2) {
        List<Integer> parent1Route = new ArrayList<>(solution1.getRoute());
        List<Integer> parent2Route = new ArrayList<>(solution2.getRoute());
        int routeSize = parent1Route.size();

        // Step 1: Select two random crossover points
        int crossoverPoint1 = random.nextInt(routeSize);
        int crossoverPoint2 = random.nextInt(routeSize);

        // Ensure crossoverPoint1 < crossoverPoint2
        if (crossoverPoint1 > crossoverPoint2) {
            int temp = crossoverPoint1;
            crossoverPoint1 = crossoverPoint2;
            crossoverPoint2 = temp;
        }

        // Step 2: Create the child by copying the segment from parent1 into the child
        List<Integer> childRoute = new ArrayList<>(Collections.nCopies(routeSize, -1)); // Initialize with dummy values
        for (int i = crossoverPoint1; i <= crossoverPoint2; i++) {
            childRoute.set(i, parent1Route.get(i));
        }

        // Step 3: Fill the remaining positions with cities from parent2, preserving their order
        int parent2Index = 0;
        for (int i = 0; i < routeSize; i++) {
            if (!childRoute.contains(parent2Route.get(parent2Index))) {

                // Find the next empty position in the child
                int emptyPosition = childRoute.indexOf(-1);
                if (emptyPosition != -1) {
                    childRoute.set(emptyPosition, parent2Route.get(parent2Index));
                }
            }
            parent2Index++;
            if (parent2Index >= routeSize) break; // Safety check
        }

        TTPSolution offspring = new TTPSolution(childRoute, new ArrayList<>(Collections.nCopies(childRoute.size(), -1)));
        handleItems(solution1, solution2, offspring);

        return offspring;
    }

    private void handleItems(TTPSolution parent1, TTPSolution parent2, TTPSolution offspring) {
        copyItemsFromParent(parent1, offspring);
        copyItemsFromParent(parent2, offspring);
    }


    private void copyItemsFromParent(TTPSolution parent, TTPSolution offspring) {
        List<Integer> parentItems = parent.getItems();
        TTPInstance instance = algorithm.getInstance();

        for (int i = 0; i < parent.getRoute().size(); i++) {
            int node = parent.getRoute().get(i);
            int itemParent = parentItems.get(i);

            if (itemParent != -1) {
                int offspringIndex = offspring.getRoute().indexOf(node);

                if (offspring.getItems().get(offspringIndex) == -1) {
                    double itemsWeight = instance.getItems().get(itemParent).getWeight();

                    offspring.updateTotalWeight(itemsWeight);
                    if (algorithm.validateSolution(offspring)) {
                        offspring.getItems().set(offspringIndex, itemParent);
                    } else {
                        offspring.updateTotalWeight(-itemsWeight);
                    }
                }
            }
        }
    }


    @Override
    protected TTPSolution select(List<TTPSolution> population) {
        List<TTPSolution> tournament = new ArrayList<>();

        // Select k individuals at random to form a tournament
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int randomIndex = random.nextInt(population.size());
            tournament.add(population.get(randomIndex));
        }

        // Find and return the best solution in the tournament based on fitness
        return tournament.stream()
                .max(Comparator.comparingDouble(TTPSolution::getFitness))
                .orElse(null); // Returns null if, for some reason, no solution was selected; consider a more robust error handling here
    }


    @Override
    public void optimize() {

        for (int i = 0; i < NUM_GENERATIONS; i++) {
            List<TTPSolution> newPopulation = new ArrayList<>();
            List<TTPSolution> population = algorithm.getPopulation();

            while (newPopulation.size() < population.size()) {
                TTPSolution parent1 = select(population);
                TTPSolution parent2 = select(population);
                TTPSolution offspring;

                if (random.nextFloat(0, 1) < CROSSOVER_PROBABILITY) {
                    offspring = crossover(parent1, parent2);
                    offspring.setChanged(true);
                } else {
                    offspring = new TTPSolution(parent1);
                    offspring.setChanged(false);
                }

                if (random.nextFloat(0, 1) < MUTATION_PROBABILITY) {
                    offspring = mutate(offspring);
                    offspring.setChanged(true);
                }

                newPopulation.add(offspring);
            }
            algorithm.setPopulation(newPopulation);
            algorithm.execute();

            if (!population.stream().filter(x -> x.getTotalWeight() > algorithm.getInstance().getCapacityOfKnapsack()).toList().isEmpty()) {
                System.out.println("Mutation or crossover caused the solution to be invalid");
            }

            population.sort(Comparator.comparingDouble(TTPSolution::getFitness).reversed());
            bestSolution = bestSolution == null || population.get(0).getFitness() > bestSolution.getFitness() ?
                    population.get(0) : bestSolution;
        }
//        newPopulation.stream().filter(TTPSolution::isChanged).filter(x-> x.getTotalWeight()>algorithm.instance.getCapacityOfKnapsack()).toList()
    }

    @Override
    public void initialize() {
        algorithm.initialize(POPULATION_SIZE);
    }

    @Override
    public void evaluate() {
        algorithm.execute();
    }
}
