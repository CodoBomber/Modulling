package events.tree;

public class Vertex {

    private int id;
    private int degree;

    public Vertex(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    void incrementDegree() {
        degree++;
    }
}
