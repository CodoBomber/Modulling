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
    private HashMap<Vertex, List<Vertex>> availableVertexes;

    public EventTreeGenerator(int vertexCount, int edgeCount, int maxDegree) {
        super(vertexCount, maxDegree);
        this.edgeCount = edgeCount;
        buildTree();
        cleaning();
        //ツリーを作り上げる間に以下のファンクションをやった方が早い
        calculateLinkedIndexes();
    }

    private void cleaning() {
        linkedVertexes = linkedVertexes.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void calculateLinkedIndexes() {
        this.availableVertexes = new HashMap<>();
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
        List<Vertex> availableList = availableVertexes.computeIfAbsent(vertex, k -> new ArrayList<>());
        availableList.add(addition);
    }

    public void generate() {
        Vertex vertex = getRandomLinkedVertex();
        List<Vertex> linkedVertexes = availableVertexes.get(vertex);
        int randomSibling = random.nextInt(availableVertexes.size());
        Vertex sibling = linkedVertexes.get(randomSibling);
        link(vertex, sibling);
    }

}
