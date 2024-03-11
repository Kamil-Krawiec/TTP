package TTP.Algorithms;

import Helpers.Node;
import TTP.TTPInstance;
import TTP.TTPSolution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GreedyAlgorithm extends EAlgorithm {


    public GreedyAlgorithm(TTPInstance instance, int startingNode) {
        super(instance, startingNode);
    }

    @Override
    public void initialize(int populationSize) {
        TTPSolution solution = new TTPSolution();

        for (int n = 0; n < instance.getNodes().size(); n++) {
            solution.getItems().add(new ArrayList<>());
        }

        solution.setRoute(createGreedyRoute());
        stealTheMostValuableItems(solution);
        solution.setFitness(fitness(solution));
        population.add(solution);

    }

    private List<Integer> createGreedyRoute() {
        Set<Integer> visitedNodes = new HashSet<>(); // Keeps track of visited nodes
        List<Integer> route = new ArrayList<>(); // The greedy route to be built


        int currentNode = startingNode;
        route.add(currentNode);
        visitedNodes.add(currentNode);

        while (visitedNodes.size() < instance.getNodes().size()) {
            int closestNode = -1;
            double closestDistance = Double.MAX_VALUE;

            for (Integer nextNode : instance.getNodes().keySet()) {
                if (!visitedNodes.contains(nextNode)) {
                    double distance = instance.getNodes().get(currentNode).getDistanceTo(instance.getNodes().get(nextNode));
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestNode = nextNode;
                    }
                }
            }

            if (closestNode != -1) {
                route.add(closestNode);
                visitedNodes.add(closestNode);
                currentNode = closestNode;
            }
        }

        return route;
    }

    private void stealTheMostValuableItems(TTPSolution solution) {
        for (int i = 0; i < instance.getItems().size(); i++) {
            for (Node n : instance.getNodes().values()) {
                List<Integer> items = n.getItems();

                if (items.isEmpty()) {
                    continue;
                }

                int mostValuableItemIndex = -1;
                double mostValuableItemValue = -1;

                for (Integer item : items) {
                    double currProfit = instance.getItems().get(item).getProfit();
                    if (mostValuableItemValue < currProfit) {
                        mostValuableItemValue = currProfit;
                        mostValuableItemIndex = item;
                    }
                }

                solution.updateWeightOfItems(instance.getItems().get(mostValuableItemIndex).getWeight());
                if(validateSolution(solution)){
                    solution.getItems().get(n.getIndex()).add(mostValuableItemIndex);
                }else{
                    break;
                }
            }
        }
    }

}