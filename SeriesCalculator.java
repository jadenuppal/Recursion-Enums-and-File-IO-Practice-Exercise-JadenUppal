import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

enum SeriesCategory {
    ARITHMETIC,
    GEOMETRIC,
    FIBONACCI,
    CUSTOM
}

public class SeriesCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose a series category: ARITHMETIC, GEOMETRIC, FIBONACCI, CUSTOM");
        SeriesCategory category = SeriesCategory.valueOf(scanner.next().toUpperCase());

        System.out.println("Enter the starting term:");
        int startTerm = scanner.nextInt();

        int ratioOrDifference = 0;
        if (category == SeriesCategory.ARITHMETIC || category == SeriesCategory.GEOMETRIC
                || category == SeriesCategory.CUSTOM) {
            System.out.println("Enter the common ratio/difference:");
            ratioOrDifference = scanner.nextInt();
        }

        System.out.println("Enter the number of terms:");
        int numTerms = scanner.nextInt();

        int sum = 0;
        switch (category) {
            case ARITHMETIC:
                sum = sumArithmetic(startTerm, ratioOrDifference, numTerms);
                break;
            case GEOMETRIC:
                sum = sumGeometric(startTerm, ratioOrDifference, numTerms);
                break;
            case FIBONACCI:
                sum = sumFibonacci(numTerms);
                break;
            case CUSTOM:
                sum = sumCustom(startTerm, ratioOrDifference, numTerms);
                break;
        }

        saveResultsToFile(category, startTerm, ratioOrDifference, numTerms, sum);

        System.out.println("Results saved to results.txt");
    }

    public static int sumArithmetic(int start, int difference, int terms) {
        if (terms == 1)
            return start;
        return start + sumArithmetic(start + difference, difference, terms - 1);
    }

    public static int sumGeometric(int start, int ratio, int terms) {
        if (terms == 1)
            return start;
        return start + sumGeometric(start * ratio, ratio, terms - 1);
    }

    public static int sumFibonacci(int terms) {
        if (terms <= 0)
            return 0;
        if (terms == 1)
            return 1;
        return fib(terms);
    }

    private static int fib(int n) {
        if (n <= 1)
            return n;
        return fib(n - 1) + fib(n - 2);
    }

    public static int sumCustom(int start, int modifier, int terms) {
        if (terms == 1)
            return start;
        return start + sumCustom(start + modifier * 2, modifier, terms - 1);
    }

    public static void saveResultsToFile(SeriesCategory category, int start, int ratioOrDiff, int terms, int sum) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt", true))) {
            writer.write("Series Category: " + category + "\n");
            writer.write("Starting Term: " + start + "\n");
            if (category != SeriesCategory.FIBONACCI) {
                writer.write("Common Ratio/Difference: " + ratioOrDiff + "\n");
            }
            writer.write("Number of Terms: " + terms + "\n");
            writer.write("Sum of Terms: " + sum + "\n\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}