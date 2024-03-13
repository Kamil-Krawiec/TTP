package TTP.Algorithms;

import TTP.TTPInstance;
import TTP.TTPSolution;

import java.util.ArrayList;
import java.util.Collections;

public class EvolutionaryAlgorithm extends ExtendedGreedyAlgo{


    public EvolutionaryAlgorithm(TTPInstance instance) {
        super(instance);
    }

//    initialization of a population is done by creating sort of a greedy solution from random starting point
    @Override
    public void initialize(int populationSize) {
        for (int i=0;i<populationSize; i++) {
            TTPSolution solution = new TTPSolution();
            solution.setItems(new ArrayList<>(Collections.nCopies(instance.getDimension(),-1)));
            int minKeyNode = instance.getNodes().keySet().stream().findFirst().orElse(0);
            solution.setRoute(createGreedyRoute(random.nextInt(minKeyNode,instance.getDimension()-1)));
            stealTheMostValuableItems(solution);
            population.add(solution);
        }
    }
}
