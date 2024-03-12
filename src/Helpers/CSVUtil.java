package Helpers;

import TTP.TTPSolution;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVUtil {

    public static void saveSolutionToCSV(TTPSolution solution, String filePath, String algorithmName,String instanceName) throws IOException {
        File file = new File(filePath);
        boolean isNewFile = file.createNewFile(); // Attempts to create the file, returns true if the file did not exist and was successfully created

        try (FileWriter writer = new FileWriter(file, true)) { // Use 'file' instead of 'filePath' to utilize the File object
            if (isNewFile) {
                // Write the header only if the file did not exist and was created
                String header = "AlgorithmName,InstanceName,TotalProfit,TotalWeight,TotalTravelingTime,Fitness,TotalDistance,WeightOfItems,Route,Items\n";
                writer.append(header);
            }

            // Converting route and items list to a string representation
            String routeString = solution.getRoute().toString().replaceAll("[\\[\\] ]", "").replaceAll(",", "->");
            String itemsString = solution.getItems().toString().replaceAll("[\\[\\]\\s]", "").replaceAll(",", "->");

            // CSV Data
            String data = String.format("%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%s,%s\n",
                    algorithmName,
                    instanceName,
                    solution.getTotalProfit(),
                    solution.getTotalWeight(),
                    solution.getTotalTravelingTime(),
                    solution.getFitness(),
                    solution.getTotalDistance(),
                    solution.getWeightOfItems(),
                    routeString,
                    itemsString);

            writer.append(data);
        }
    }
}
