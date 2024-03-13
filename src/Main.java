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
        String fileName = "fnl4461-ttp/fnl4461_n4460_bounded-strongly-corr_02.ttp";
        berlin = berlin.loadFromFile("/Users/kamil/IdeaProjects/Optimization Methods/data/"+fileName);

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


        CSVUtil.saveSolutionToCSV(bestSolutionRandom,"./solutions/data.csv","Random",fileName);
        CSVUtil.saveSolutionToCSV(bestSolutionGreedy,"./solutions/data.csv","Greedy",fileName);
        CSVUtil.saveSolutionToCSV(bestSolutionExtendedGreedy,"./solutions/data.csv","ExtendedGreedy",fileName);
        CSVUtil.saveSolutionToCSV(bestSolutionEvolutionary,"./solutions/data.csv","Evolutionary",fileName);



    }
}