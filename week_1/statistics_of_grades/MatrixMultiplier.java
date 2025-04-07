import java.util.Scanner;

/**
 * MatrixMultiplier is a program that allows users to input two matrices,
 * performs matrix multiplication if possible, and displays the result.
 * 
 * The program validates that the matrices can be multiplied (columns of matrix
 * A must equal rows of matrix B) and handles various input errors gracefully.
 * The output is formatted with proper column alignment.
 * 
 */

public class MatrixMultiplier {

  /**
   * The main method that drives the matrix multiplication program.
   * It handles user input, matrix multiplication, and result display.
   * 
   * @param args Command line arguments (not used)
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    try {
      int[][] matrixA = inputMatrix(scanner, "A");
      int[][] matrixB = inputMatrix(scanner, "B");
      if (matrixA[0].length != matrixB.length) {
        throw new IllegalArgumentException(
            "Matrix multiplication not possible. Columns of A (" + matrixA[0].length +
                ") must equal rows of B (" + matrixB.length + ").");
      }
      int[][] matrixC = multiplyMatrices(matrixA, matrixB);
      System.out.println("\nMatrix C:");
      printMatrix(matrixC);
    } catch (IllegalArgumentException e) {
      System.err.println("Error: " + e.getMessage());
    } finally {
      scanner.close();
    }
  }

  /**
   * Reads a matrix from user input. The method prompts the user to enter the
   * dimensions of the matrix, then reads the matrix elements row by row.
   * 
   * @param scanner Scanner object for input
   * @param name    Name of the matrix (for prompts)
   * @return The input matrix as a 2D integer array
   * @throws IllegalArgumentException If the input format is invalid, dimensions
   *                                  are non-positive,
   *                                  or if the number of elements in a row
   *                                  doesn't match the specified column count
   */
  private static int[][] inputMatrix(Scanner scanner, String name) {
    System.out.printf("Enter the number of rows and columns of matrix %s (Format: rows,columns):\n", name);
    System.out.printf("Matrix %s: ", name);
    String dimensionInput = scanner.nextLine();
    int rows, cols;
    try {
      String[] parts = dimensionInput.split(",");
      if (parts.length != 2) {
        throw new IllegalArgumentException("Invalid dimension format for dimensions");
      }
      rows = Integer.parseInt(parts[0].trim());
      cols = Integer.parseInt(parts[1].trim());
      if (rows <= 0 || cols <= 0) {
        throw new IllegalArgumentException("Rows and columns must be positive integers");
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid dimension format. Use 'rows,columns'");
    }
    System.out.printf("Enter elements of matrix %s (row-wise, space separated):\n", name);
    int[][] matrix = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      String[] rowValues = scanner.nextLine().trim().split("\\s+");
      if (rowValues.length != cols) {
        throw new IllegalArgumentException(
            "Expected " + cols + " values for row " + (i + 1) + ", got " + rowValues.length);
      }
      for (int j = 0; j < cols; j++) {
        matrix[i][j] = Integer.parseInt(rowValues[j]);
      }
    }
    return matrix;
  }

  /**
   * Multiplies two matrices following the standard matrix multiplication
   * algorithm.
   * 
   * For matrices A (n×m) and B (m×p), the result is a matrix C (n×p) where each
   * element
   * C[i][j] is the dot product of the ith row of A and the jth column of B.
   * 
   * @param a First matrix (n×m)
   * @param b Second matrix (m×p)
   * @return Resulting matrix C (n×p)
   */
  private static int[][] multiplyMatrices(int[][] a, int[][] b) {
    int n = a.length; // Rows of matrix A
    int m = a[0].length; // Columns of matrix A / Rows of matrix B
    int p = b[0].length; // Columns of matrix B
    int[][] result = new int[n][p];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < p; j++) {
        for (int k = 0; k < m; k++) {
          result[i][j] += a[i][k] * b[k][j];
        }
      }
    }
    return result;
  }

  /**
   * Prints a matrix with proper formatting and column alignment.
   * 
   * Each row is enclosed in vertical bars (|) and each element is displayed
   * with consistent column width based on the largest value in the matrix.
   * This ensures that columns are properly aligned regardless of the number of
   * digits.
   * 
   * @param matrix The matrix to print
   */
  private static void printMatrix(int[][] matrix) {
    int maxWidth = 1;
    for (int[] row : matrix) {
      for (int val : row) {
        int width = String.valueOf(val).length();
        if (width > maxWidth)
          maxWidth = width;
      }
    }
    for (int[] row : matrix) {
      System.out.print("| ");
      for (int val : row) {
        System.out.printf("%" + maxWidth + "d ", val);
      }
      System.out.println("|");
    }
  }
}
