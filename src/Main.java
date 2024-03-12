import Helpers.CSVUtil;
import TTP.Algorithms.EAlgorithm;
import TTP.Algorithms.ExtendedGreedyAlgo;
import TTP.Algorithms.GreedyAlgorithm;
import TTP.Algorithms.RandomAlgorithm;
import TTP.Optimizer.Optimizer;
import TTP.Optimizer.TTPOptimizer;
import TTP.TTPInstance;
import TTP.TTPSolution;

public class Main {
    public static void main(String[] args) throws Throwable {

        TTPInstance berlin = new TTPInstance();
        berlin = berlin.loadFromFile("/Users/kamil/IdeaProjects/Optimization Methods/data/pr152-ttp/pr152_n151_bounded-strongly-corr_02.ttp");

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

        EAlgorithm extendedGreedyAlgo = new ExtendedGreedyAlgo(berlin);
        Optimizer optimizerGreedyAlgo = new TTPOptimizer(extendedGreedyAlgo);
        optimizerGreedyAlgo.initialize();
        optimizerGreedyAlgo.evaluate();
        optimizerGreedyAlgo.optimize();

        TTPSolution bestSolutionRandom = optimizerRandom.getBestSolution();
        TTPSolution bestSolutionGreedy = optimizerGreedy.getBestSolution();
        TTPSolution bestSolutionExtendedGreedy = optimizerGreedyAlgo.getBestSolution();

        CSVUtil.saveSolutionToCSV(bestSolutionRandom,"./solutions/data.csv","Random",berlin.getProblemName());
        CSVUtil.saveSolutionToCSV(bestSolutionGreedy,"./solutions/data.csv","Greedy",berlin.getProblemName());
        CSVUtil.saveSolutionToCSV(bestSolutionExtendedGreedy,"./solutions/data.csv","ExtendedGreedy",berlin.getProblemName());


    }
}