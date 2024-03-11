package TTP.Optimizer;

import TTP.Algorithms.EAlgorithm;


public class TTPOptimizer extends Optimizer {

    public TTPOptimizer(EAlgorithm algorithm) {
        super(algorithm);
    }



    @Override
    public void optimize() {
        if( bestSolution == null){
            bestSolution = getAlgorithm().getBestSolution();
        }
        else if(getAlgorithm().getBestSolution().getFitness() > bestSolution.getFitness()){
            bestSolution = getAlgorithm().getBestSolution();
        }
    }

    public void evaluate(){
        getAlgorithm().execute();
    }

    @Override
    public void initialize() {
        getAlgorithm().initialize(getPOPULATION_SIZE());
    }
}
