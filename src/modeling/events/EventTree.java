package modeling.events;

import modeling.events.tree.Edge;
import modeling.events.tree.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class EventTree {

    protected final int maxDegree;
    protected final int vertexCount;
    private int leafLazyCounter;
    private List<Vertex> leafList;
    protected List<Vertex> linkedVertexes;
    protected HashSet<Edge> edges = new HashSet<>();
    protected HashSet<Vertex> maxDegreeVertexes = new HashSet<>();
    protected ThreadLocalRandom random = ThreadLocalRandom.current();
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
            linkedVertex = getCheckedLinkedVertex();
            link(linkedVertex, leaf);
            markAsLinked(leaf);
        }
    }

    private void markAsLinked(Vertex leaf) {
        linkedVertexes.add(linkedLazyCounter, leaf);
        linkedLazyCounter++;
    }

    private Vertex getCheckedLinkedVertex() {
        int randomIndex = random.nextInt(linkedLazyCounter);
        Vertex vertex = linkedVertexes.get(randomIndex);
        if (vertex.getDegree() == maxDegree - 1) {
            deleteLinkedElement(randomIndex);
            maxDegreeVertexes.add(vertex);
        }
        return vertex;
    }

    protected Vertex getRandomLinkedVertex() {
        int randomIndex = random.nextInt(linkedLazyCounter);
        return linkedVertexes.get(randomIndex);
    }

    @Deprecated
    private void deleteLinkedElement(int index) {
        linkedLazyCounter--;
        if (index < linkedLazyCounter) {
            Collections.swap(linkedVertexes, index, linkedLazyCounter);
        }
        linkedVertexes.set(linkedLazyCounter, null);
    }

    protected void deleteLinkedElement(Vertex vertex) {
        linkedLazyCounter--;
        linkedVertexes.remove(vertex);
    }

    private void initializeRoot() {
        Vertex root = getRandomLeafVertex();
        Vertex child = getRandomLeafVertex();
        System.out.println("ROOT VERTEX: " + root);
        link(root, child);
        linkedVertexes.add(root);
        linkedVertexes.add(child);
        linkedLazyCounter = 2;
    }

    protected void link(Vertex parent, Vertex child) {
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

    public void printMaxDegreeVertexes() {
        System.out.println("##########MAX DEGREE##########");
        System.out.println(maxDegreeVertexes.toString());
    }
}
