package TTP;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
public class TTPSolution {
    private List<Integer> route;
    private List<Integer>  items;
    private double totalProfit;
    private double totalWeight;
    private double totalTravelingTime;
    private double fitness;
    private double totalDistance;
    private double weightOfItems;
    private boolean isChanged;

    public TTPSolution(List<Integer> route,
                       List<Integer> items,
                       double totalProfit,
                       double totalWeight,
                       double totalTravelingTime,
                       double fitness,
                       double totalDistance,
                       double weightOfItems,
                       boolean isChanged) {
        this.route = route;
        this.items = items;
        this.totalProfit = totalProfit;
        this.totalWeight = totalWeight;
        this.totalTravelingTime = totalTravelingTime;
        this.fitness = fitness;
        this.totalDistance = totalDistance;
        this.weightOfItems = weightOfItems;
        this.isChanged = true;
    }

    public TTPSolution() {
        route = new ArrayList<>();
        items = new ArrayList<>();
        isChanged = true;
    }

    public TTPSolution(List<Integer> route, List<Integer> items) {
        this.route = route;
        this.items = items;
        isChanged = true;
    }

    public void updateTotalWeight(double weight) {
        totalWeight += weight;
    }

    public void updateWeightOfItems(double weight) {
        weightOfItems += weight;
    }

    public void setFitness(double fitness){
        isChanged = false;
        this.fitness = fitness;
    }

    @Override
    public int hashCode() {
        return Objects.hash(route, items);
    }

    @Override
    public String toString() {
        return "TTPSolution{" +
                "route=" + route +
                ", items=" + items +
                ", totalProfit=" + totalProfit +
                ", totalWeight=" + totalWeight +
                ", totalTravelingTime=" + totalTravelingTime +
                ", fitness=" + fitness +
                ", totalDistance=" + totalDistance +
                ", weightOfItems=" + weightOfItems +
                '}';
    }
}