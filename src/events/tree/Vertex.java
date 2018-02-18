package events.tree;

public class Vertex implements Cloneable {

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

    public int getDegree() {
        return degree;
    }

    @Override
    public Vertex clone() {
        try {
            return (Vertex) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
