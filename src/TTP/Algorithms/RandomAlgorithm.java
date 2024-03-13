package TTP.Algorithms;
import Helpers.Node;
import TTP.TTPInstance;
import TTP.TTPSolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class RandomAlgorithm extends EAlgorithm{

    public RandomAlgorithm(TTPInstance instance) {
        super(instance);
    }



    @Override
    public void initialize(int populationSize) {

        for(int i = 0; i < populationSize; i++){

            TTPSolution solution = new TTPSolution();
            List<Integer> route = new ArrayList<>(instance.getNodes().keySet());
            Collections.shuffle(route);
            solution.setRoute(route);
            solution.setItems(new ArrayList<>(Collections.nCopies(instance.getDimension(),-1)));

            for(Integer node: solution.getRoute()){
                if(!validateSolution(solution) || !instance.getItems().containsKey(node)){
                    break;
                }
                List<Integer> itemsListForNode= instance.getNodes().get(node).getItems() ;

                for(Integer k: itemsListForNode){
                    double itemsWeight = instance.getItems().get(k).getWeight();
                    solution.updateWeightOfItems(itemsWeight);
                    if(random.nextBoolean() && validateSolution(solution)){
                        solution.getItems().set(route.indexOf(node),k);
                        break;
                    }
                    solution.updateWeightOfItems(-itemsWeight);
                }
            }
            solution.setFitness(fitness(solution));
            population.add(solution);
        }

    }
}
