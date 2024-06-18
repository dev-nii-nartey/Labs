import java.util.Scanner;

public class PeakColumnsFinder {
  public static void main(String[] args) {
    System.out.println("LETS BUILD YOUR MATRIX: ");
    int[][] matrixStructure = getDimension();
    int[] matrixValues = getIntegers(matrixStructure.length, matrixStructure[0].length);
    int[][] matrixA = buildMatrix(matrixStructure, matrixValues);

    findPeakColumns(matrixA);
  }

  private static final Scanner SC = new Scanner(System.in);

  private static int[][] getDimension() {
    while (true) {
      try {
        System.out.println("Enter Dimensions of the Matrix separated by a comma (,)");
        String[] userInput = SC.nextLine().split(",");
        if (userInput.length != 2) {
          throw new Exception("This APP handles only two-dimensional matrices\n");
        }
        int row = Integer.parseInt(userInput[0]);
        int col = Integer.parseInt(userInput[1]);
        return new int[row][col];
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
          System.out.println("Enter values for row number " + (i + 1) + " separated by comma");
          String rowValues = SC.nextLine();
          int inputLength = rowValues.split(",").length;
          if (inputLength != col) {
            throw new Exception("Exceeded columns range, try again \n");
          }
          values += rowValues + ",";
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
        structure[i][j] = values[arrIdx++];
      }
    }
    return structure;
  }

  private static void findPeakColumns(int[][] matrix) {
    boolean peakColumnFound = false;
    for (int j = 0; j < matrix[0].length; j++) {
      int minInColumn = Integer.MAX_VALUE;
      int rowIndex = -1;
      // Find the minimum in the current column
      for (int i = 0; i < matrix.length; i++) {
        if (matrix[i][j] < minInColumn) {
          minInColumn = matrix[i][j];
          rowIndex = i;
        }
      }
      // Check if this minimum is also the maximum in its row
      boolean isMaxInRow = true;
      for (int k = 0; k < matrix[0].length; k++) {
        if (matrix[rowIndex][k] > minInColumn) {
          isMaxInRow = false;
          break;
        }
      }
      if (isMaxInRow) {
        peakColumnFound = true;
        System.out.println("(" + (rowIndex + 1) + "," + (j + 1) + ") = " + minInColumn);
      }
    }
    if (!peakColumnFound) {
      System.out.println("No peak-columns found.");
    }
  }

}