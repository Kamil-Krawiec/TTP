package Helpers;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter @Builder
public class Item {
    private int index;
    private int profit;
    private int weight;
    private int assignedNodeNumber;

    public Item() {
    }

    public Item(Item other){
        this.index = other.index;
        this.profit = other.profit;
        this.weight = other.weight;
        this.assignedNodeNumber = other.assignedNodeNumber;
    }

    public Item(int index, int profit, int weight, int assignedNodeNumber) {
        this.index = index;
        this.profit = profit;
        this.weight = weight;
        this.assignedNodeNumber = assignedNodeNumber;
    }

    public int hashCode(){
        return Objects.hash(index, profit, weight, assignedNodeNumber);
    }

}
