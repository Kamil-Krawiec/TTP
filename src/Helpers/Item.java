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
    private boolean isStolen;

    public Item() {
    }

    public Item(Item other){
        this.index = other.index;
        this.profit = other.profit;
        this.weight = other.weight;
        this.assignedNodeNumber = other.assignedNodeNumber;
        this.isStolen = other.isStolen;
    }

    public Item(int index, int profit, int weight, int assignedNodeNumber, boolean isStolen) {
        this.index = index;
        this.profit = profit;
        this.weight = weight;
        this.assignedNodeNumber = assignedNodeNumber;
        this.isStolen = isStolen;
    }

    public int hashCode(){
        return Objects.hash(index, profit, weight, assignedNodeNumber, isStolen);
    }

    public void stoleItem(){
        this.isStolen = true;
    }
}
