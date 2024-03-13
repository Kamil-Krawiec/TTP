package TTP.Algorithms;

import Helpers.Item;
import TTP.TTPInstance;
import TTP.TTPSolution;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public abstract class EAlgorithm {

    protected Random random = new Random();
    protected TTPInstance instance;
    protected TTPSolution bestSolution;
    protected List<TTPSolution> population = new ArrayList<>();

    public TTPSolution getBestSolution() {
        return population.stream().findFirst().orElse(null);
    }


    public EAlgorithm(TTPInstance instance) {
        this.instance = instance;
    }

    // Method to initialize the algorithm
    public abstract void initialize(int populationSize);

    public void execute() {
        population.stream().filter(TTPSolution::isChanged).forEach(solution-> solution.setFitness(fitness(solution)));
    }

    public void execute(TTPSolution solution) {
        solution.setFitness(fitness(solution));
    }

    // g(y) - sum of items stolen
    // f(x,y) - total traveling time
    // G(x,y) = g(y) - f(x,y)
    //G(x,y)
    protected double fitness(TTPSolution solution) {
        solution.setTotalWeight(0);
        for (int i = 0; i < instance.getDimension(); i++) {
            int curr = solution.getRoute().get(i);
            int next = solution.getRoute().get((i + 1) % instance.getDimension());
            double nextDistance = instance.getNodes()
                    .get(curr)
                    .getDistanceTo(instance.getNodes().get(next));

            solution.setTotalDistance(solution.getTotalDistance() + nextDistance);
            int stolenItemIndex = solution.getItems().get(i);
            if(stolenItemIndex!=-1){
                Item currItem = instance.getItems().get(stolenItemIndex);
                solution.updateTotalWeight(currItem.getWeight());
                solution.setTotalProfit(solution.getTotalProfit() + currItem.getProfit());
            }

            solution.setTotalTravelingTime(solution.getTotalTravelingTime() +
                    totalTravelingTime(nextDistance,
                            instance.getMaxSpeed(),
                            instance.getMinSpeed(),
                            instance.getCapacityOfKnapsack(),
                            solution.getTotalWeight()));
        }
        solution.setFitness(solution.getTotalProfit() - solution.getTotalTravelingTime());

        return solution.getFitness();
    }


    protected double totalTravelingTime(double distance,
                                        double maxSpeed,
                                        double minSpeed,
                                        double capacityOfKnapsack,
                                        double itemWeights) {
        double currentVelocity = maxSpeed - itemWeights * (maxSpeed - minSpeed) / capacityOfKnapsack;

        return distance / currentVelocity;
    }

    public boolean validateSolution(TTPSolution solution) {
        return solution.getWeightOfItems()<=instance.getCapacityOfKnapsack() &&solution.getTotalWeight()<=instance.getCapacityOfKnapsack();
    }


}
