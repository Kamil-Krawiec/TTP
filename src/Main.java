import TTP.Algorithms.EAlgorithm;
import TTP.Algorithms.GreedyAlgorithm;
import TTP.Algorithms.RandomAlgorithm;
import TTP.Optimizer.Optimizer;
import TTP.Optimizer.TTPOptimizer;
import TTP.TTPInstance;

public class Main {
    public static void main(String[] args) throws Throwable {

        TTPInstance berlin = new TTPInstance();
        berlin = berlin.loadFromFile("/Users/kamil/IdeaProjects/Optimization Methods/Data/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");

        EAlgorithm greedyAlgo = new GreedyAlgorithm(berlin);
        Optimizer optimizerGreedy = new TTPOptimizer(greedyAlgo);
        optimizerGreedy.initialize();
        optimizerGreedy.evaluate();
        optimizerGreedy.optimize();


        EAlgorithm radom = new RandomAlgorithm(berlin);
        Optimizer optimizerRandom = new TTPOptimizer(radom);
        optimizerRandom.initialize();
        optimizerRandom.evaluate();
        optimizerRandom.optimize();

        System.out.println(optimizerRandom.getBestSolution().getFitness());
        System.out.println(optimizerGreedy.getBestSolution().getFitness());

    }
}