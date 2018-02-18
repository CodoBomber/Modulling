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
    private ArrayList<Vertex> leafList;
    private ArrayList<Vertex> linkedVertexes;
    private LinkedList<Edge> edges = new LinkedList<>();
    private ThreadLocalRandom random = ThreadLocalRandom.current();

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
    }

    private void initializeRoot() {
        Vertex root = getRandomLeafVertex();
        Vertex child = getRandomLeafVertex();
        link(root, child);
    }

    private void link(Vertex parent, Vertex child) {
        //При создании ребра степени вершин увеличиваются автоматически
        edges.add(new Edge(parent, child));
    }

    private Vertex getRandomLeafVertex() {
        int rootIndex = random.nextInt(leafLazyCounter);
        Vertex root = leafList.get(rootIndex);
        leafLazyCounter--;
        deleteElementLazily(leafList, rootIndex);
        return root;
    }

    private void deleteElementLazily(List<Vertex> list, int index) {
        if (index < leafLazyCounter) {
            Collections.swap(list, index, leafLazyCounter);
        }
        list.set(leafLazyCounter, null);
    }
}
