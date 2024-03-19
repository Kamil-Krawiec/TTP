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
        String fileName = "berlin52_n51_bounded-strongly-corr_01.ttp";
        berlin = berlin.loadFromFile("/Users/kamil/IdeaProjects/Optimization Methods/data/berlin52-ttp/"+fileName);

        System.out.println("Greedy");
        String algoNameGreedy = "Greedy";
        EAlgorithm greedyAlgo = new GreedyAlgorithm(berlin);
        Optimizer optimizerGreedy = new TTPOptimizer(greedyAlgo);
        optimizerGreedy.initialize();
        optimizerGreedy.evaluate();
        optimizerGreedy.optimize();

        System.out.println("Random");
        String algoNameRandom = "Random";
        EAlgorithm radom = new RandomAlgorithm(berlin);
        Optimizer optimizerRandom = new TTPOptimizer(radom);
        optimizerRandom.initialize();
        optimizerRandom.evaluate();
        optimizerRandom.optimize();

        System.out.println("Extended greedy");
        String algoNameExGreedy = "Extended greedy";
        EAlgorithm extendedGreedyAlgo = new ExtendedGreedyAlgo(berlin);
        Optimizer optimizerGreedyAlgo = new TTPOptimizer(extendedGreedyAlgo);
        optimizerGreedyAlgo.initialize();
        optimizerGreedyAlgo.evaluate();
        optimizerGreedyAlgo.optimize();

        System.out.println("Evolutionary");
        String algoNameExEvolutionary = "Evolutionary OX SWAP";
        EAlgorithm evolutionaryAlgo = new EvolutionaryAlgorithm(berlin);
        Optimizer optimizerEvolutionary = new RealOptimizer(evolutionaryAlgo,true,true);
        optimizerEvolutionary.initialize();
        optimizerEvolutionary.evaluate();
        optimizerEvolutionary.optimize();

        System.out.println("Evolutionary2");
        String algoNameExEvolutionary2 = "Evolutionary CX INVERSE";
        EAlgorithm evolutionaryAlgo2 = new EvolutionaryAlgorithm(berlin);
        Optimizer optimizerEvolutionary2 = new RealOptimizer(evolutionaryAlgo2,false,false);
        optimizerEvolutionary2.initialize();
        optimizerEvolutionary2.evaluate();
        optimizerEvolutionary2.optimize();



        TTPSolution bestSolutionRandom = optimizerRandom.getBestSolution();
        TTPSolution bestSolutionGreedy = optimizerGreedy.getBestSolution();
        TTPSolution bestSolutionExtendedGreedy = optimizerGreedyAlgo.getBestSolution();
        TTPSolution bestSolutionEvolutionary = optimizerEvolutionary.getBestSolution();
        TTPSolution bestSolutionEvolutionary2 = optimizerEvolutionary2.getBestSolution();



        CSVUtil.saveSolutionToCSV(bestSolutionRandom,"./solutions/data.csv",algoNameRandom,fileName);
        CSVUtil.saveSolutionToCSV(bestSolutionGreedy,"./solutions/data.csv",algoNameGreedy,fileName);
        CSVUtil.saveSolutionToCSV(bestSolutionExtendedGreedy,"./solutions/data.csv",algoNameExGreedy,fileName);
        CSVUtil.saveSolutionToCSV(bestSolutionEvolutionary,"./solutions/data.csv",algoNameExEvolutionary,fileName, "OX","SWAP");
        CSVUtil.saveSolutionToCSV(bestSolutionEvolutionary2,"./solutions/data.csv",algoNameExEvolutionary2,fileName, "CX","INVERSE");





    }
}