import java.util.Scanner;

public class MatrixMultiplication {
 public static void main(String[] args) {
    System.out.println("LETS BUILD YOUR FIRST MATRIX: ");
    int[][] matrixStructure1 = getDimension();
    int[] matrix1values = getIntegers(matrixStructure1.length, matrixStructure1[0].length);
    int[][] matrixA = buildMatrix(matrixStructure1, matrix1values);

    System.out.println("LETS BUILD YOUR SECOND MATRIX: ");
    int[][] matrixStructure2 = getDimension();
    int[] matrix2values = getIntegers(matrixStructure2.length, matrixStructure2[0].length);
    int[][] matrixB = buildMatrix(matrixStructure2, matrix2values);

    int[][] matrixOutput = multiplyMatrix(matrixA, matrixB);
    printMatrix(matrixOutput);

    for (int i = 0; i < matrixA.length; i++) {
      System.out.print(" Matrix A  \n");
      printMatrix(matrixA);
      System.out.println("\n  multiplied by  ");
      System.out.println("\n Matrix B ");
      printMatrix(matrixB);
      System.out.println("\n  the product is  ");
      printMatrix(matrixOutput);
      System.out.println();
    }

  }

  private static final Scanner SC = new Scanner(System.in);

  private static int[][] getDimension() {
    while (true) {
      try {
        System.out.println("Enter Dimensions of the Matrix seperated by a comma (,)");
        String[] userInput = SC.nextLine().split(",");
        if (userInput.length != 2) {
          throw new Exception("This APP handles only two dimensional matrix\n");
        }
        int row = Integer.parseInt(userInput[0]);
        int col = Integer.parseInt(userInput[1]);
        int[][] matrix = new int[row][col];
        return matrix;
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private static int[] getIntegers(int row, int col) {
    while (true) {
      try {

        String values = "";
        for (int i = 0; i < row; i++) {
          System.out.println("Enter values for row number " + (i + 1) + " seperated by comma");
          String rowValues = SC.nextLine();
          int inputLength = rowValues.split(",").length;
          if (inputLength != col) {
            throw new Exception("Exceeded columns range, try again \n");
          }
          values = values + rowValues + ",";
        }
        String[] valArr = values.split(",");
        int[] userIntegers = new int[valArr.length];
        for (int i = 0; i < valArr.length; i++) {
          userIntegers[i] = Integer.parseInt(valArr[i]);
        }
        return userIntegers;
      } catch (Exception e) {
        System.out.println("Invalid figures entered as part of integers for matrix, try again\n");
      }
    }
  }

  private static int[][] buildMatrix(int[][] structure, int[] values) {
    int arrIdx = 0;
    for (int i = 0; i < structure.length; i++) {
      for (int j = 0; j < structure[0].length; j++) {
        structure[i][j] = values[arrIdx];
        arrIdx++;
      }
    }
    return structure;
  };

  private static int[][] multiplyMatrix(int[][] A, int[][] B) {
    if (A[0].length != B.length) {
      return null;
    }
    int[][] result = new int[A.length][B[0].length];

    for (int i = 0; i < A.length; i++) {
      for (int j = 0; j < B[0].length; j++) {
        result[i][j] = 0;
        for (int k = 0; k < B.length; k++) {
          result[i][j] += A[i][k] * B[k][j];
        }
      }
    }
    return result;
  }

  private static void printMatrix(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      System.out.print("| ");
      for (int j = 0; j < matrix[0].length; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println("|");
    }
  }

}