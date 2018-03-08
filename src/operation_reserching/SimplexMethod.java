package operation_reserching;

public class SimplexMethod {

    private double[][] A;
    private int leadColumn;
    private int leadRow;

    public SimplexMethod(double[][] matrix) {
        this.A = matrix;
    }

    public void findOptimalSolutions() {
        for (int min = findLeadColumn(A[0]); min != -1; min = findLeadColumn(A[0])) {
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
        System.out.println(A[1][A[1].length - 1]);
        System.out.println(A[2][A[2].length - 1]);
        System.out.println(A[0][A[0].length - 1]);
        System.out.println("###################################");
    }

    private void calculateRow(int rowIndex) {
        double leadElement = A[rowIndex][leadColumn];
        for (int i = 0; i < A[rowIndex].length; i++) {
            A[rowIndex][i] -= leadElement * A[leadRow][i];
        }
    }

    private void calculateLeadRow() {
        double leadElement = A[leadRow][leadColumn];
        for (int i = 0; i < A[leadRow].length; i++) {
            A[leadRow][i] /= leadElement;
        }
    }

    private void findLeadRow() {
        /*double min;
        double local_min;
        for (int minIndex = 1; minIndex < A.length; minIndex++) {
            min = A[minIndex][A[minIndex].length - 1] / A[minIndex][leadColumn];
            local_min = A[minIndex][A[minIndex].length - 1] / A[minIndex][leadColumn];
            if (local_min >= 0 && local_min < min) {
                min = local_min;
                leadRow = minIndex;
            }
        }*/
        double min = A[1][A[1].length - 1] / A[1][leadColumn], localMin;
        leadRow = 1;
        for (int i = 2; i < A.length; i++) {
            localMin = A[i][A[i].length - 1] / A[i][leadColumn];
            if (localMin > 0 && localMin < min) {
                min = localMin;
                leadRow = i;
            }
        }
    }

    private int findLeadColumn(double[] row) {
        int min = 0;
        double minValue = row[0];
        for (int i = 1; i < row.length; i++) {
            if (row[i] < minValue) {
                minValue = row[i];
                min = i;
            }
        }
        if (row[min] < 0) {
            leadColumn = min;
            return min;
        }
        return -1;
    }

    public void prettyPrint() {
        System.out.println("############MATRIX#PRINT###########");
        for (double[] row : A) {
            for (double column : row) {
                System.out.print(column + " ");
            }
            System.out.println();
        }
        System.out.println("###################################");
    }
}
