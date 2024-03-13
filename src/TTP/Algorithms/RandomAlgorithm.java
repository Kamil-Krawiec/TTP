package TTP.Algorithms;
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



            for(Integer j: instance.getItems().keySet()){
                if(!validateSolution(solution)){
                    break;
                }
                List<Integer> givenList = instance.getNodes().get(j).getItems();

                for(Integer k: givenList){
                    solution.updateWeightOfItems(instance.getItems().get(k).getWeight());
                    if(random.nextBoolean() && validateSolution(solution)){
                        solution.getItems().set(j,k);
                    }
                }
            }
            solution.setFitness(fitness(solution));
            population.add(solution);
        }

    }
}
