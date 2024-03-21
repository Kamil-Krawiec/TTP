package Helpers;

import TTP.TTPSolution;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CSVUtil {

    public static void saveSolutionToCSV(TTPSolution solution, String filePath, String algorithmName,
                                         String instanceName, String... args) throws IOException {
        File file = new File(filePath);
        boolean isNewFile = file.createNewFile(); // Attempts to create the file, returns true if the file did not exist and was successfully created

        try (FileWriter writer = new FileWriter(file, true)) { // Use 'file' instead of 'filePath' to utilize the File object
            if (isNewFile) {
                // Write the header only if the file did not exist and was created
                String header = "AlgorithmName,InstanceName,TotalProfit,TotalWeight,TotalTravelingTime,Fitness,TotalDistance,WeightOfItems,SwapType,CrossoverType,Route,Items\n";
                writer.append(header);
            }

            // Converting route and items list to a string representation
            String routeString = solution.getRoute().toString().replaceAll("[\\[\\] ]", "").replaceAll(",", "->");
            String itemsString = solution.getItems().toString().replaceAll("[\\[\\]\\s]", "").replaceAll(",", "->");

            // CSV Data
            String data = String.format("%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%s,%s,%s,%s\n",
                    algorithmName,
                    instanceName,
                    solution.getTotalProfit(),
                    solution.getTotalWeight(),
                    solution.getTotalTravelingTime(),
                    solution.getFitness(),
                    solution.getTotalDistance(),
                    solution.getWeightOfItems(),
                    args.length>0 ? args[0] : "N/A",
                    args.length>0 ? args[1] : "N/A",
                    routeString,
                    itemsString);

            writer.append(data);
        }
    }

    public static void saveFitnessesToCSV(String instanceName,List<Double> bestFitness, List<Double> worstFitness, List<Double> avgFitness,
                                          String swap,String cross,String filePath) throws IOException {
        File file = new File(filePath);

        boolean isNewFile = file.createNewFile();

        try (FileWriter writer = new FileWriter(file, true)) {
            if (isNewFile) {
                String header = "InstanceName,Swap,Cross,Worst,Best,Avg\n";
                writer.append(header);
            }

            for (int i = 0; i < bestFitness.size(); i++) {
                double best = bestFitness.get(i);
                double worst = worstFitness.get(i);
                double avg = avgFitness.get(i);

                String data = String.format("%s,%s,%s,%.4f,%.4f,%.4f\n", instanceName , swap,cross,worst, best,avg);
                writer.append(data);
            }
        }
    }
}
