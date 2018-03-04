package operation_reserching;

import java.util.Arrays;

public class SimplexMethod {

    private double[][] A;
    private int leadColumn;
    private int leadRow;

    public SimplexMethod(double[][] matrix) {
        this.A = matrix;
    }

    public void findOptimalSolutions() {
        while (isNegativeNumberCondition(A[0])) {
            findLeadColumn(A[0]);
            findLeadRow();
            calculateLeadRow();
            for (int i = 0; i < A.length; i++) {
                //rows
                if (i == leadRow) continue;
                calculateRow(i);
            }
        }
        printOptimalSolutions();
    }

    private void printOptimalSolutions() {
        System.out.println("#########OPTIMAL SOLUTION:#########");
        System.out.println(A[0][A.length - 1]);
        System.out.println(A[1][A.length - 1]);
        System.out.println(A[2][A.length - 1]);
        System.out.println("###################################");
    }

    private void calculateRow(int rowIndex) {
        for (int i = 0; i < A[rowIndex].length; i++) {
            A[rowIndex][i] -= A[rowIndex][leadColumn] * A[leadRow][i];
        }
    }

    private void calculateLeadRow() {
        for (int i = 0; i < A[leadRow].length; i++) {
            A[leadRow][i] /= A[leadRow][leadColumn];
        }
    }

    private void findLeadRow() {
        double min = A[0][A[0].length - 1] / A[0][leadColumn];
        int minIndex;
        double local_min;
        for (minIndex = 1; minIndex < A.length; minIndex++) {
            local_min = A[minIndex][A[minIndex].length - 1] / A[minIndex][leadColumn];
            min = local_min >= 0 && local_min < min ? local_min : min;
        }
        leadRow = minIndex;
    }

    private boolean isNegativeNumberCondition(double[] row) {
        return Arrays.stream(row)
                .boxed()
                .anyMatch(v -> v < 0);
    }

    private void findLeadColumn(double[] row) {
        int min = 0;
        double minValue = Double.MAX_VALUE;
        for (int i = 0; i < row.length; i++) {
            if (minValue > row[i]) {
                minValue = row[i];
                min = i;
            }
        }
        leadColumn = min;
    }

    public void prettyPrint() {
        for (double[] row : A) {
            for (double column : row) {
                System.out.print(column + " ");
            }
            System.out.println();
        }
    }
}
