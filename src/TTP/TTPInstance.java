package TTP;

import Helpers.Item;
import Helpers.Node;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


@Getter
@Setter
@Builder
public class TTPInstance {

    @NonNull
    private String problemName;
    @NonNull
    private String knapsackDataType;
    private int dimension;
    private int numberOfItems;
    private int capacityOfKnapsack;
    private double minSpeed;
    private double maxSpeed;
    private double rentingRatio;
    private String edgeWeightType;
    private HashMap<Integer, Node> nodes;
    private HashMap<Integer, Item> items;

    public TTPInstance(@NonNull String problemName, @NonNull String knapsackDataType,
                       int dimension, int numberOfItems, int capacityOfKnapsack,
                       double minSpeed, double maxSpeed, double rentingRatio,
                       String edgeWeightType, HashMap<Integer, Node> nodes, HashMap<Integer, Item> items) {
        this.problemName = problemName;
        this.knapsackDataType = knapsackDataType;
        this.dimension = dimension;
        this.numberOfItems = numberOfItems;
        this.capacityOfKnapsack = capacityOfKnapsack;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.rentingRatio = rentingRatio;
        this.edgeWeightType = edgeWeightType;
        this.nodes = nodes;
        this.items = items;
    }

    public TTPInstance() {
        this.nodes = new HashMap<>();
        this.items = new HashMap<>();
    }


    public TTPInstance loadFromFile(String filename) throws Throwable {
        TTPInstanceBuilder ttpInstanceBuilder = TTPInstance.builder();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(":", 2); // Split only at the first colon encountered

            String propertyName = parts[0].trim();
            String propertyValue = parts[1].trim();

            switch (propertyName) {
                case "PROBLEM NAME":
                    ttpInstanceBuilder.problemName(propertyValue);
                    break;
                case "KNAPSACK DATA TYPE":
                    ttpInstanceBuilder.knapsackDataType(propertyValue);
                    break;
                case "DIMENSION":
                    ttpInstanceBuilder.dimension(Integer.parseInt(propertyValue));
                    break;
                case "NUMBER OF ITEMS":
                    ttpInstanceBuilder.numberOfItems(Integer.parseInt(propertyValue));
                    break;
                case "CAPACITY OF KNAPSACK":
                    ttpInstanceBuilder.capacityOfKnapsack(Integer.parseInt(propertyValue));
                    break;
                case "MIN SPEED":
                    ttpInstanceBuilder.minSpeed(Double.parseDouble(propertyValue));
                    break;
                case "MAX SPEED":
                    ttpInstanceBuilder.maxSpeed(Double.parseDouble(propertyValue));
                    break;
                case "RENTING RATIO":
                    ttpInstanceBuilder.rentingRatio(Double.parseDouble(propertyValue));
                    break;
                case "EDGE_WEIGHT_TYPE":
                    ttpInstanceBuilder.edgeWeightType(propertyValue);
                    break;
                case "NODE_COORD_SECTION\t(INDEX, X, Y)":
                    HashMap<Integer, Node> nodes = new HashMap<>();

                    while (!(line = reader.readLine()).equals("ITEMS SECTION\t(INDEX, PROFIT, WEIGHT, ASSIGNED NODE NUMBER): ")) {
                        String[] nodeInfo = line.split("\\s+");
                        Node node = Node.builder()
                                .index(Integer.parseInt(nodeInfo[0]))
                                .x(Double.parseDouble(nodeInfo[1]))
                                .y(Double.parseDouble(nodeInfo[2]))
                                .items(new ArrayList<>())
                                .build();
                        nodes.put(node.getIndex(), node);
                    }
                    this.nodes = nodes;
                    ttpInstanceBuilder.nodes(nodes);
                case "ITEMS SECTION":
                    HashMap<Integer, Item> items = new HashMap<>();
                    while ((line = reader.readLine()) != null && !line.isEmpty()) {
                        String[] itemInfo = line.split("\\s+");
                        Item item = Item.builder()
                                .index(Integer.parseInt(itemInfo[0]))
                                .profit(Integer.parseInt(itemInfo[1]))
                                .weight(Integer.parseInt(itemInfo[2]))
                                .assignedNodeNumber(Integer.parseInt(itemInfo[3]))
                                .build();
                        items.put(item.getIndex(), item);
                        this.nodes.get(item.getAssignedNodeNumber()).addItem(item.getIndex());
                    }
                    ttpInstanceBuilder.items(items);
                    break;
                default:
                    break;
            }
        }
        reader.close();

        return ttpInstanceBuilder.build();
    }


    @Override
    public String toString() {
        return "TTP.TTPInstance{" +
                "problemName='" + problemName + '\'' +
                ", knapsackDataType='" + knapsackDataType + '\'' +
                ", dimension=" + dimension +
                ", numberOfItems=" + numberOfItems +
                ", capacityOfKnapsack=" + capacityOfKnapsack +
                ", minSpeed=" + minSpeed +
                ", maxSpeed=" + maxSpeed +
                ", rentingRatio=" + rentingRatio +
                ", edgeWeightType='" + edgeWeightType + '\'' +
                ", nodes=" + nodes +
                '}';
    }
}