package TTP.Algorithms;

import Helpers.Item;
import TTP.TTPInstance;
import TTP.TTPSolution;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public abstract class EAlgorithm {

    protected TTPInstance instance;
    protected TTPSolution bestSolution;
    protected Set<TTPSolution> population = new TreeSet<>(Comparator.comparingDouble(TTPSolution::getFitness).reversed());

    public TTPSolution getBestSolution() {
        return population.stream().findFirst().orElse(null);
    }


    public EAlgorithm(TTPInstance instance) {
        this.instance = instance;
    }

    // Method to initialize the algorithm
    public abstract void initialize(int populationSize);

    // Method to execute the algorithm and give n first solutions for a population in generation
    public void execute() {
        for (TTPSolution solution : population) {
            solution.setFitness(fitness(solution));
        }
    }

    // g(y) - sum of items stolen
    // f(x,y) - total traveling time
    // G(x,y) = g(y) - f(x,y)
    //G(x,y)
    protected double fitness(TTPSolution solution) {
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
        return solution.getWeightOfItems()<instance.getCapacityOfKnapsack();
    }


}
