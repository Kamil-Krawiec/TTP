import Helpers.CSVUtil;
import TTP.Algorithms.*;
import TTP.Optimizer.Optimizer;
import TTP.Optimizer.RealOptimizer;
import TTP.Optimizer.TTPOptimizer;
import TTP.TTPInstance;
import TTP.TTPSolution;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OptimizationRunner {
    private static final String filesDir = "./data/cases/";
    private static final String solutionDir = "./solutions/";
    private static final String populationAnalysisFileName = "populationAnalysis.csv";
    private static final String algorithmAnalysisFileName = "algorithmAnalysis.csv";

    public static void main(String[] args) throws Throwable {
        List<String> fileNames = processFilesInDirectory(filesDir);

        for (String fileName : fileNames) {
            runOptimizationForFile(filesDir + fileName, fileName);
        }
    }

    private static void runOptimizationForFile(String filePath, String fileName) throws Throwable {
        TTPInstance ttpInstance = new TTPInstance().loadFromFile(filePath);

        executeAndSaveOptimization(new GreedyAlgorithm(ttpInstance), "Greedy", fileName);
        executeAndSaveOptimization(new RandomAlgorithm(ttpInstance), "Random", fileName);
        executeAndSaveOptimization(new ExtendedGreedyAlgo(ttpInstance), "Extended greedy", fileName);
        executeAndSaveOptimization(new EvolutionaryAlgorithm(ttpInstance), "Evolutionary OX SWAP",
                fileName, "OX", "SWAP", "1", "1");
        executeAndSaveOptimization(new EvolutionaryAlgorithm(ttpInstance), "Evolutionary CX INVERSE",
                fileName, "CX", "INVERSE", "0", "0");
        executeAndSaveOptimization(new EvolutionaryAlgorithm(ttpInstance), "Evolutionary CX SWAP",
                fileName, "CX", "SWAP", "0", "1");
        executeAndSaveOptimization(new EvolutionaryAlgorithm(ttpInstance), "Evolutionary OX INVERSE",
                fileName, "OX", "INVERSE", "1", "0");
        executeAndSaveOptimization(new TabuAlgorithm(ttpInstance), "Tabu", fileName);
    }

    private static void executeAndSaveOptimization(EAlgorithm algorithm, String algorithmName, String instanceName, String... extraArgs) throws Throwable {

        if (algorithmName.contains("Evolutionary")) {
            String cross = algorithmName.split(" ")[1];
            String swap = algorithmName.split(" ")[2];
            RealOptimizer realOptimizer = new RealOptimizer(algorithm, Boolean.getBoolean(extraArgs[2]), Boolean.getBoolean(extraArgs[3]));
            realOptimizer.initialize();
            realOptimizer.evaluate();
            realOptimizer.optimize();
            CSVUtil.saveFitnessesToCSV(instanceName,realOptimizer.getBestSolutions(), realOptimizer.getWorstSolutions(),
                    realOptimizer.getAvgSolutions(), swap, cross, solutionDir + populationAnalysisFileName);
            TTPSolution bestSolution = realOptimizer.getBestSolution();
            CSVUtil.saveSolutionToCSV(bestSolution, solutionDir + algorithmAnalysisFileName,
                    algorithmName, instanceName, extraArgs);
        } else if("Tabu".equals(algorithmName)){
            // In this case population size is number of iterations
            algorithm.initialize(100);
            algorithm.execute();
            TTPSolution bestSolution = algorithm.getBestSolution();
            CSVUtil.saveSolutionToCSV(bestSolution, solutionDir + algorithmAnalysisFileName,
                    algorithmName, instanceName);
        }
        else {
            Optimizer optimizer = new TTPOptimizer(algorithm);
            optimizer.initialize();
            optimizer.evaluate();
            optimizer.optimize();
            TTPSolution bestSolution = optimizer.getBestSolution();
            CSVUtil.saveSolutionToCSV(bestSolution, solutionDir + algorithmAnalysisFileName,
                    algorithmName, instanceName, extraArgs);
        }
    }


    public static List<String> processFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        List<String> fileNames = new ArrayList<>();

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileNames.add(file.getName());
                        System.out.println("Added file: " + file.getName());
                    }
                }
            }
        } else {
            System.err.println("Directory does not exist: " + directoryPath);
        }
        return fileNames;
    }
}
