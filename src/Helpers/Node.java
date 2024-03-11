package Helpers;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.util.List;
import java.util.Objects;

@Getter @Setter @Builder
public class Node {
    private int index;
    private double x;
    private double y;
    private List<Integer> items;

    public Node(){}

    public Node(int index, double x, double y, List<Integer> items) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.items = items;
    }

    public double getDistanceTo(Node other){
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public void addItem(int item){
        this.items.add(item);
    }

    public int hashCode(){
        return Objects.hash(index, x, y, items);
    }

    @Override
    public String toString() {
        return "Node{" +
                "index=" + index +
                ", x=" + x +
                ", y=" + y +
                ", items=" + items +
                "}\n";
    }

    public boolean isItemInNode(int item){
        return this.items.contains(item);
    }

    public Node(Node other){
        this.index = other.index;
        this.x = other.x;
        this.y = other.y;
        this.items= other.items;
    }
}

