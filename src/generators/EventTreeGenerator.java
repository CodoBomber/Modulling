package generators;

import events.EventTree;
import events.tree.Edge;
import events.tree.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EventTreeGenerator extends EventTree {

    private final int edgeCount;
    private HashMap<Vertex, List<Vertex>> linkedIndexes;

    public EventTreeGenerator(int vertexCount, int edgeCount, int maxDegree) {
        super(vertexCount, maxDegree);
        this.edgeCount = edgeCount;
        buildTree();
        cleaning();
        //ツリーを作り上げる間に以下のファンクションをやった方が早い
        calculateLinkedIndexes();
        System.out.println();
    }

    private void cleaning() {
        linkedVertexes = linkedVertexes.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void calculateLinkedIndexes() {
        this.linkedIndexes = new HashMap<>();
        //dirty code, alarm!
        for (int i = 0; i < linkedVertexes.size(); i++) {
            for (int j = 0; j < linkedVertexes.size(); j++) {
                    if (j == i) {
                        continue;
                    }
                    Edge edge = Edge.createForCompare(linkedVertexes.get(i), linkedVertexes.get(j));
                    if (!edges.contains(edge)) {
                        putLinkedIndexes(linkedVertexes.get(i), linkedVertexes.get(j));
                    }
            }
        }
    }

    private void putLinkedIndexes(Vertex vertex, Vertex addition) {
        List<Vertex> availableList = linkedIndexes.computeIfAbsent(vertex, k -> new ArrayList<>());
        availableList.add(addition);
    }

}
