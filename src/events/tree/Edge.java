package events.tree;

import java.util.stream.IntStream;

public class Edge {

    private Vertex startVertex;
    private Vertex endVertex;

    private Edge(int vertexStartId, int vertexEndId) {
        this.startVertex = new Vertex(vertexStartId);
        this.endVertex = new Vertex(vertexEndId);
    }

    public Edge(Vertex startVertex, Vertex endVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        startVertex.incrementDegree();
        endVertex.incrementDegree();
    }

    public static Edge createForCompare(/*not null*/Vertex startVertex, /*not null*/Vertex endVertex) {
        if (startVertex.getId() <= endVertex.getId()) {
            return new Edge(startVertex.getId(), endVertex.getId());
        } else {
            return new Edge(endVertex.getId(), startVertex.getId());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (startVertex != null ? !startVertex.equals(edge.startVertex) : edge.startVertex != null) return false;
        return endVertex != null ? endVertex.equals(edge.endVertex) : edge.endVertex == null;
    }

    @Override
    public int hashCode() {
        int nullSafetyStartHash = startVertex != null ? startVertex.getId() + 10 : 0;
        int nullSafetyEndHash = endVertex != null ? endVertex.getId() + 10 : 0;
        return nullSafetyStartHash + nullSafetyEndHash;
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
