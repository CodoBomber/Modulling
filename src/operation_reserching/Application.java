package operation_reserching;

public class Application {

    public static void main(String[] args) {
        SimplexMethod method = new SimplexMethod(
                new double[][] {
                        {1, -5, -4, 0, 0, 0, 0, 0},
                        {0, 6, 4, 1, 0, 0, 0, 24},
                        {0, 1, 2, 0, 1, 0, 0, 6},
                        {1, -1, 1, 0, 0, 1, 0, 1},
                        {0, 0, 1, 0, 0, 0, 1, 2}
                }
        );
        method.findOptimalSolutions();
        method.prettyPrint();
    }

}
