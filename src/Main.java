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

public class Main {
    public static void main(String[] args) throws Throwable {
        for(int i=0;i<11;i++){
            System.out.println("Running iteration "+i);
            OptimizationRunner.main(args);
        }
    }


}