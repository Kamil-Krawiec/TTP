package TTP.Optimizer;

import TTP.Algorithms.EAlgorithm;


public class TTPOptimizer extends Optimizer {

    public TTPOptimizer(EAlgorithm algorithm) {
        super(algorithm);
    }



    @Override
    public void optimize() {
        if( bestSolution == null){
            bestSolution = algorithm.getBestSolution();
        }
        else if(algorithm.getBestSolution().getFitness() > bestSolution.getFitness()){
            bestSolution = getAlgorithm().getBestSolution();
        }
    }

    public void evaluate(){
        algorithm.execute();
    }

    @Override
    public void initialize() {
        algorithm.initialize(POPULATION_SIZE);
    }
}
