import Helpers.CSVUtil;
import TTP.Algorithms.*;
import TTP.Optimizer.Optimizer;
import TTP.Optimizer.RealOptimizer;
import TTP.Optimizer.TTPOptimizer;
import TTP.TTPInstance;
import TTP.TTPSolution;

public class Main {
    public static void main(String[] args) throws Throwable {

        TTPInstance berlin = new TTPInstance();
        berlin = berlin.loadFromFile("/Users/kamil/IdeaProjects/Optimization Methods/data/berlin52-ttp/berlin52_n51_bounded-strongly-corr_01.ttp");

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

        EAlgorithm evolutionaryAlgo = new EvolutionaryAlgorithm(berlin);
        Optimizer optimizerEvolutionary = new RealOptimizer(evolutionaryAlgo);
        optimizerEvolutionary.initialize();
        optimizerEvolutionary.evaluate();
        optimizerEvolutionary.optimize();

        TTPSolution bestSolutionRandom = optimizerRandom.getBestSolution();
        TTPSolution bestSolutionGreedy = optimizerGreedy.getBestSolution();
        TTPSolution bestSolutionExtendedGreedy = optimizerGreedyAlgo.getBestSolution();
        TTPSolution bestSolutionEvolutionary = optimizerEvolutionary.getBestSolution();


        CSVUtil.saveSolutionToCSV(bestSolutionRandom,"./solutions/data.csv","Random",berlin.getProblemName());
        CSVUtil.saveSolutionToCSV(bestSolutionGreedy,"./solutions/data.csv","Greedy",berlin.getProblemName());
        CSVUtil.saveSolutionToCSV(bestSolutionExtendedGreedy,"./solutions/data.csv","ExtendedGreedy",berlin.getProblemName());
        CSVUtil.saveSolutionToCSV(bestSolutionEvolutionary,"./solutions/data.csv","Evolutionary",berlin.getProblemName());



    }
}