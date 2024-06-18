
import java.util.Scanner;

public class StatisticsOfGrades {
    public static void main(String args[]) {
        int[] scores = userInput();
        printAverages(scores);
        printGraph(scores);
        scanner.close();
    }

    private static final Scanner scanner = new Scanner(System.in);

    private static int[] userInput() {

        System.out.println("Enter student grades seperated by space: ");

        String[] scores = scanner.nextLine().split(" ");

        int[] stats = new int[scores.length];

        for (int i = 0; i < stats.length; i++) {
            try {
                stats[i] = Integer.parseInt(scores[i]);
            } catch (Exception e) {
                System.out.println("Invalid figure entered among the grades, Check grades  input and try again");
                System.exit(0);
            }

        }
        return stats;
    }

    private static void printAverages(int[] stats) {
        int sum = 0;
        int min = stats[0];
        int max = stats[0];

        for (int i : stats) {
            if (i < min) {
                min = i;
            }
            if (i > max) {
                max = i;
            }
            sum = sum + i;
        }
        double average = sum / (double) stats.length;

        System.out.println("");
        System.out.println("The maximum score is " + max);
        System.out.println("The minumum is " + min);
        System.out.println("The average is " + average);

    }

    private static void printGraph(int[] scores) {
        System.out.println("\nGraph:\n");

        // This array to hold the counts of scores in each range.
        int[] stats = new int[5];

        // Go through each score.
        for (int score : scores) {
            // Depending on the score, increment the count in the appropriate range.
            if (score > 80) {
                stats[4]++;
            } else if (score > 60) {
                stats[3]++;
            } else if (score > 40) {
                stats[2]++;
            } else if (score > 20) {
                stats[1]++;
            } else {
                stats[0]++;
            }
        }

        // Find the maximum count. which will be the height of the graph.
        int maxCount = 0;
        for (int i = 0; i < stats.length; i++) {
            if (stats[i] > maxCount) {
                maxCount = stats[i];
            }
        }

        // Print each row of the graph, from top to bottom.
        for (int i = maxCount; i > 0; i--) {
            // Print the y-axis label for this row.
            System.out.printf("%3d  >", i);

            // For each range, print '#####' if the count is at least as large as the row
            for (int j = 0; j < 5; j++) {
                if (stats[j] >= i) {
                    System.out.print("   #######");
                } else {
                    System.out.print("          ");
                }
            }
            // Move to the next line after printing all ranges in the current row.
            System.out.println();
        }

        System.out.println("     +-----------+---------+---------+---------+---------+");
        System.out.println("     |    0-20   |  21-40  |  41-60  |  61-80  |  81-100 |");
    }

}