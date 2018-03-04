package modeling.events.tree;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;
        return id == vertex.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
