package TTP.Optimizer;

import TTP.Algorithms.EAlgorithm;

import TTP.TTPSolution;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public abstract class Optimizer {
    protected EAlgorithm algorithm;
    protected final int POPULATION_SIZE = 100;
    protected final int NUM_GENERATIONS = 100;
    protected final double CROSSOVER_PROBABILITY = 0.7;
    protected final double MUTATION_PROBABILITY = 0.1;
    protected final int TOURNAMENT_SIZE = 5;
    protected TTPSolution bestSolution;


    public Optimizer(EAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    // Method to execute the optimization process
    public abstract void optimize();

    // Method to initialize first population
    public abstract void initialize();

    protected TTPSolution mutate(TTPSolution solution) {
        return null;
    }

    protected TTPSolution crossover(TTPSolution solution1, TTPSolution solution2) {
        return null;
    }

    protected TTPSolution select(TTPSolution[] population) {
        return null;
    }

    public abstract void evaluate();

}
