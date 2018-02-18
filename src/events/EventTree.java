package events;

import events.tree.Edge;
import events.tree.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class EventTree {

    private final int maxDegree;
    private final int vertexCount;
    private int leafLazyCounter;
    private List<Vertex> leafList;
    private List<Vertex> linkedVertexes;
    private List<Edge> edges = new LinkedList<>();
    private List<Vertex> maxDegreeVertexes = new ArrayList<>();
    private ThreadLocalRandom random = ThreadLocalRandom.current();
    private int linkedLazyCounter;

    public EventTree(int vertexCount, int maxDegree) {
        this.vertexCount = vertexCount;
        this.leafLazyCounter = vertexCount;
        this.maxDegree = maxDegree;
        leafList = new ArrayList<>(vertexCount);
        linkedVertexes = new ArrayList<>(vertexCount);
        IntStream.rangeClosed(1, vertexCount)
                .boxed()
                .forEach(i -> leafList.add(new Vertex(i)));
    }

    public void buildTree() {
        initializeRoot();
        fillTree();
    }

    private void fillTree() {
        Vertex leaf;
        Vertex linkedVertex;
        while (leafLazyCounter > 0) {
            leaf = getRandomLeafVertex();
            linkedVertex = getRandomLinkedVertex();
            link(linkedVertex, leaf);
            markAsLinked(leaf);
        }
    }

    private void markAsLinked(Vertex leaf) {
        linkedVertexes.add(linkedLazyCounter, leaf);
        linkedLazyCounter++;
    }

    private Vertex getRandomLinkedVertex() {
        int randomIndex = random.nextInt(linkedLazyCounter);
        Vertex vertex = linkedVertexes.get(randomIndex).clone();
        if (vertex.getDegree() == maxDegree - 1) {
            deleteLinkedElement(randomIndex);
            maxDegreeVertexes.add(vertex);
        }
        return vertex;
    }

    @Deprecated
    private void deleteLinkedElement(int index) {
        linkedLazyCounter--;
        if (index < linkedLazyCounter) {
            Collections.swap(linkedVertexes, index, linkedLazyCounter);
        }
        linkedVertexes.set(linkedLazyCounter, null);
    }

    private void initializeRoot() {
        Vertex root = getRandomLeafVertex();
        Vertex child = getRandomLeafVertex();
        link(root, child);
        linkedVertexes.add(root);
        linkedVertexes.add(child);
        linkedLazyCounter = 2;
    }

    private void link(Vertex parent, Vertex child) {
        //При создании ребра степени вершин увеличиваются автоматически
        edges.add(new Edge(parent, child));
    }

    private Vertex getRandomLeafVertex() {
        if (leafLazyCounter == 1) {
            Vertex lastLeaf = leafList.get(0);
            leafLazyCounter--;
            return lastLeaf;
        }
        int randomIndex = random.nextInt(leafLazyCounter);
        Vertex leaf = leafList.get(randomIndex).clone();
        deleteLeafLazily(randomIndex);
        return leaf;
    }

    private void deleteLeafLazily(int index) {
        leafLazyCounter--;
        if (index < leafLazyCounter) {
            Collections.swap(leafList, index, leafLazyCounter);
        }
        leafList.set(leafLazyCounter, null);
    }

    public void printEdges() {
        edges.forEach(System.out::println);
    }
}
