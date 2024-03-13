package TTP.Algorithms;

import Helpers.Node;
import TTP.TTPInstance;
import TTP.TTPSolution;

import java.util.*;

public class ExtendedGreedyAlgo extends GreedyAlgorithm {


    public ExtendedGreedyAlgo(TTPInstance instance) {
        super(instance);
    }

    @Override
    public void initialize(int populationSize) {
        // Best solution found starting from any node
        TTPSolution bestSolution = null;
        double bestFitness = Double.NEGATIVE_INFINITY;

        for (Integer startNode : instance.getNodes().keySet()) {
            TTPSolution solution = new TTPSolution();
            solution.setItems(new ArrayList<>(Collections.nCopies(instance.getDimension(), -1)));
            solution.setRoute(createGreedyRoute(startNode));
            stealTheMostValuableItems(solution);
            solution.setFitness(fitness(solution));

            if (solution.getFitness() > bestFitness) {
                bestFitness = solution.getFitness();
                bestSolution = solution;
            }
        }

        if (bestSolution != null) {
            population.add(bestSolution);
        }
    }

    protected List<Integer> createGreedyRoute(Integer startNode) {
        Set<Integer> visitedNodes = new HashSet<>();
        List<Integer> route = new ArrayList<>();
        int currentNode = startNode;
        route.add(currentNode);
        visitedNodes.add(currentNode);

        while (visitedNodes.size() < instance.getNodes().size()) {
            int closestNode = -1;
            double closestDistance = Double.MAX_VALUE;

            for (Integer nextNode : instance.getNodes().keySet()) {
                if (!visitedNodes.contains(nextNode)) {
                    if(instance.getNodes().get(nextNode)==null || instance.getNodes().get(currentNode) == null){
                        System.out.println("ooo");
                    }
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


}