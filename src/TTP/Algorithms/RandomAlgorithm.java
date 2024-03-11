package TTP.Algorithms;
import TTP.TTPInstance;
import TTP.TTPSolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class RandomAlgorithm extends EAlgorithm{

    public RandomAlgorithm(TTPInstance instance, int startingNode) {
        super(instance, startingNode);
    }



    @Override
    public void initialize(int populationSize) {
        Random random = new Random();

        for(int i = 0; i < populationSize; i++){

            TTPSolution solution = new TTPSolution();
            List<Integer> route = new ArrayList<>(instance.getNodes().keySet());
            Collections.shuffle(route);
            Collections.swap(route, 0, route.indexOf(startingNode));
            solution.setRoute(route);



            for(int n = 0; n < instance.getNodes().size(); n++){
                solution.getItems().add(new ArrayList<>());
            }

            for(Integer j: instance.getItems().keySet()){
                List<Integer> givenList = instance.getNodes().get(j).getItems();


                for(Integer k: givenList){
                    if(random.nextBoolean() ){
                        solution.updateWeightOfItems(instance.getItems().get(k).getWeight());
                        if(validateSolution(solution)){
                            solution.getItems().get(j).add(k);
                        }else{
                            break;
                        }
                    }
                }
            }
            solution.setFitness(fitness(solution));
            population.add(solution);
        }

    }
}
