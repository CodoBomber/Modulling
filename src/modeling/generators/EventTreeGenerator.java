package modeling.generators;

import modeling.events.EventTree;
import modeling.events.tree.Edge;
import modeling.events.tree.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EventTreeGenerator extends EventTree {

    private final int edgeCount;
    private HashMap<Vertex, List<Vertex>> availableVertexes;

    public EventTreeGenerator(int vertexCount, int edgeCount, int maxDegree) {
        super(vertexCount, vertexCount - 1 > maxDegree ? maxDegree : vertexCount - 1);
        this.edgeCount = edgeCount;
        checkBuildableCondition();
        buildTree();
        cleaning();
        //ツリーを作り上げる間に以下のファンクションをやった方が早い
        calculateLinkedIndexes();
    }

    private void checkBuildableCondition() {
        /*if (minDegreeFactor < maxDegree) {
            throw new IllegalArgumentException("Передана недопустимая максимальная степень вершин. Максимальная доступная степень при текущих входных параметрах: " + minDegreeFactor);
        }*/
        int edgeMaxFactor = (vertexCount * maxDegree) / 2;
        int edgeMinFactor = vertexCount - 1;
        if(edgeMaxFactor < edgeCount) {
            throw new IllegalArgumentException("Введено недопустимое число рёбер. Максимальное доступное в этом случае: " + edgeMaxFactor);
        } else if (edgeMinFactor > edgeCount) {
            throw new IllegalArgumentException("Невозможно построить связный граф с кол-вом рёбер меньше минимального: " + edgeMinFactor);
        }
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
        System.out.println("START: " + edges);
        System.out.println("AVAILABLE: " + availableVertexes);
        while (edges.size() < edgeCount) {
            Vertex vertex = getRandomLinkedVertex();
            List<Vertex> linkedVertexes = availableVertexes.get(vertex);
            int randomSibling = random.nextInt(linkedVertexes.size());
            Vertex sibling = linkedVertexes.get(randomSibling);
            linkAvailable(vertex, sibling);
        }
    }

    private void linkAvailable(Vertex start, Vertex end) {
        super.link(start, end);
        System.out.println("LINK: " + start + " -- " + end);
        boolean isStartMax = handleMaxDegree(start);
        boolean isEndMax = handleMaxDegree(end);
        if (isStartMax || isEndMax) {
            return;
        }
        //遅い実装であります
        List<Vertex> availableList1 = availableVertexes.get(start);
        availableList1.remove(end);
        List<Vertex> availableList2 = availableVertexes.get(end);
        availableList2.remove(start);
        if (availableList1.isEmpty()) {
            vacuumCompletedVertexes(start);
        }
        if (availableList2.isEmpty()) {
            vacuumCompletedVertexes(end);
        }
        System.out.println("available: " + availableVertexes);
    }

    /**
     * Очищаем полносвязанные вершины после каждой итерации связывания
     */
    private void vacuumCompletedVertexes(Vertex vertex) {
        List<Vertex> vertexes = new ArrayList<>(availableVertexes.keySet());
        for (Vertex each : vertexes) {
            List<Vertex> filtered = availableVertexes.get(each)
                    .stream()
                    .filter(v -> v.getDegree() < maxDegree && !v.equals(vertex))
                    .collect(Collectors.toList());
            if (filtered.isEmpty()) {
                availableVertexes.remove(each);
            } else {
                availableVertexes.replace(
                        each,
                        filtered
                );
            }
        }
    }

    private boolean handleMaxDegree(Vertex vertex) {
        if (vertex.getDegree() == maxDegree) {
            availableVertexes.remove(vertex);
            cleaning();
            deleteLinkedElement(vertex);
            System.out.println("OH NO! MAX DEGREE! CLEAN VERTEX " + vertex);
            vacuumCompletedVertexes(vertex);
            System.out.println("AFTER CLEANING: " + availableVertexes);
            return true;
        }
        return false;
    }

}
