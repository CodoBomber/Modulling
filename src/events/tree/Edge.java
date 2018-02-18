package events.tree;

public class Edge {

    private Vertex startVertex;
    private Vertex endVertex;

    public Edge(Vertex startVertex, Vertex endVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        startVertex.incrementDegree();
        endVertex.incrementDegree();
    }

    @Override
    public String toString() {
        if (endVertex.getId() < startVertex.getId()) {
            return "[" + endVertex.getId() + ", " + startVertex.getId() + "]";
        } else {
            return "[" + startVertex.getId() + ", " + endVertex.getId() + "]";
        }
    }
}
