import java.util.Scanner;

/**
 * This program that analyses student grades and displays statistics including
 * maximum, minimum, average grades, and a visual grade distribution graph.
 * 
 */

public class GradeStatistics {

  /**
   * The main method that orchestrates the grade analysis process.
   * 
   * @param args Command line arguments (not used)
   */
  public static void main(String[] args) {

    int[] scores = getScores();
    if (scores.length == 0)
      return;

    printStatistics(scores);
  }

  /**
   * Reads and parses student scores from user input.
   * 
   * @return An array of integer scores entered by the user
   * @throws NumberFormatException if non-numeric input is provided
   */
  private static int[] getScores() {
    Scanner input = new Scanner(System.in);
    System.out.print("Enter student scores (space-separated): ");

    String[] parts = input.nextLine().split(" ");
    int[] scores = new int[parts.length];

    try {
      for (int i = 0; i < parts.length; i++) {
        scores[i] = Integer.parseInt(parts[i]);
      }
    } catch (NumberFormatException e) {
      System.err.println("Invalid input. Please enter numbers only.");
      return new int[0];
    } finally {
      input.close();
    }

    return scores;
  }

  /**
   * Calculates and prints grade statistics including max, min, and average.
   * 
   * @param scores The array of student scores to analyze
   */
  private static void printStatistics(int[] scores) {
    int max = scores[0], min = scores[0], sum = 0;
    int[] stats = new int[5];

    for (int score : scores) {
      sum += score;

      if (score > max)
        max = score;

      if (score < min)
        min = score;

      updateStats(stats, score);

    }

    System.out.printf("\nThe maximum grade is %d\n", max);
    System.out.printf("The minimum grade is %d\n", min);
    System.out.printf("The average grade is %f\n", (double) sum / scores.length);

    printGraph(stats);
  }

  /**
   * Prints a horizontal bar graph representing grade distribution.
   * 
   * @param stats Array containing counts for each grade range
   */
  private static void printGraph(int[] stats) {
    int maxCount = 0;
    for (int count : stats) {
      if (count > maxCount)
        maxCount = count;
    }

    System.out.println("\n\nGraph:\n");
    for (int level = maxCount; level >= 1; level--) {
      System.out.printf("%2d >", level);
      for (int i = 0; i < stats.length; i++) {
        System.out.print(stats[i] >= level ? "    #######" : "           ");
      }
      System.out.println();
    }

    System.out.println("    +------------+----------+----------+----------+----------+");
    System.out.println("    I    0-20    I   21-40  I   41-60  I   61-80  I   81-100 I");
  }

  /**
   * Updates the statistics array with counts for each grade range.
   * 
   * @param stats The statistics array to update
   * @param score The current score to categorise
   */
  private static void updateStats(int[] stats, int score) {
    if (0 <= score && score <= 20) {
      stats[0]++;
    } else if (21 <= score && score <= 40) {
      stats[1]++;
    } else if (41 <= score && score <= 60) {
      stats[2]++;
    } else if (61 <= score && score <= 80) {
      stats[3]++;
    } else if (81 <= score && score <= 100) {
      stats[4]++;
    }
  }
}
