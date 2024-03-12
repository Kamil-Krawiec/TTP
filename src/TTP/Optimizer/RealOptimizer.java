package TTP.Optimizer;

import TTP.Algorithms.EAlgorithm;
import TTP.TTPSolution;

import java.util.*;

public class RealOptimizer extends Optimizer {

    Random random = new Random();

    public RealOptimizer(EAlgorithm algorithm) {
        super(algorithm);
    }

    @Override
    protected TTPSolution mutate(TTPSolution solution) {


        int firstIndex = random.nextInt(solution.getRoute().size() - 1);
        int secondIndex = random.nextInt(solution.getRoute().size() - 1);

        Collections.swap(solution.getRoute(), firstIndex, secondIndex);
        Collections.swap(solution.getItems(), firstIndex, secondIndex);

        if (getMUTATION_PROBABILITY() > random.nextFloat(0, 1)) {

//            if there is an item in firstIndexNode, we throw it.
//            When there is no item picked in this node, and we can pick it up, we pick it up.
//            If there is more than 1 item in node, and we picked one before, we change it if we can to first found.
            List<Integer> items = getAlgorithm().getInstance().getNodes().get(firstIndex).getItems();
            int currentItem = solution.getItems().get(firstIndex);
            double itemsWeight = getAlgorithm().getInstance().getItems().get(firstIndex).getWeight();


            if (currentItem == -1 && items.size() == 1) {
                solution.updateWeightOfItems(itemsWeight);
                if (getAlgorithm().validateSolution(solution)) {
                    solution.getItems().set(firstIndex, items.get(0));
                } else {
                    solution.updateWeightOfItems(-itemsWeight);
                }
            } else if (items.size() > 1 && currentItem != -1) {
                solution.updateWeightOfItems(-itemsWeight);
                if (getAlgorithm().validateSolution(solution)) {
                    solution.getItems().set(firstIndex, items.stream()
                            .filter(x -> x != currentItem)
                            .findFirst().orElse(-1));
                } else {
                    solution.updateWeightOfItems(itemsWeight);
                }
            } else if (currentItem != -1) {
                solution.updateWeightOfItems(-itemsWeight);
                solution.getItems().set(firstIndex, -1);
            }
        }

        return solution;
    }

    //    XO1 crossover
    @Override
    protected TTPSolution crossover(TTPSolution solution1, TTPSolution solution2) {
        Random rand = new Random();
        List<Integer> parent1Route = new ArrayList<>(solution1.getRoute());
        List<Integer> parent2Route = new ArrayList<>(solution2.getRoute());
        int routeSize = parent1Route.size();

        // Step 1: Select two random crossover points
        int crossoverPoint1 = rand.nextInt(routeSize);
        int crossoverPoint2 = rand.nextInt(routeSize);

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

        TTPSolution offspring = new TTPSolution(childRoute,new ArrayList<>(Collections.nCopies(childRoute.size(),-1)));
        handleItems(solution1, solution2, offspring);

        return offspring;
    }

    private void handleItems(TTPSolution parent1, TTPSolution parent2, TTPSolution offspring) {

        List<Integer> parent1Items = parent1.getItems();
        List<Integer> parent2Items = parent2.getItems();


        // Iterate over the child route to decide on items
        for (int i=0;i<parent1.getRoute().size();i++) {
            int itemParent1 = parent1Items.get(i);
            int itemParent2 = parent2Items.get(i);
            double itemsWeight = 0;

            if(itemParent1!=-1){
                itemsWeight = getAlgorithm().getInstance().getItems().get(itemParent1).getWeight();
                offspring.updateWeightOfItems(itemsWeight);
                if (getAlgorithm().validateSolution(offspring)) {
                    offspring.getItems().set(i,itemParent1);
                } else {
                    offspring.updateWeightOfItems(-itemsWeight);
                }
            }else if(itemParent2!=-1){
                itemsWeight = getAlgorithm().getInstance().getItems().get(itemParent2).getWeight();
                offspring.updateWeightOfItems(itemsWeight);
                if (getAlgorithm().validateSolution(offspring)) {
                    offspring.getItems().set(i,itemParent2);
                } else {
                    offspring.updateWeightOfItems(-itemsWeight);
                }
            }
        }

    }



    @Override
    protected TTPSolution select(TTPSolution[] population) {
        return null;
    }

    @Override
    public void optimize() {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void evaluate() {
        getAlgorithm().execute();
    }
}
