import java.io.IOException;
import java.util.Scanner;

public class LZ78Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LZ78 lz78 = new LZ78();

        while (true) {
            System.out.println("Enter your choice:");
            System.out.println("1. Compress");
            System.out.println("2. Decompress");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.println("Enter the input text file path:");
                String inputFilePath = scanner.nextLine();
                System.out.println("Enter the output binary file path:");
                String outputFilePath = scanner.nextLine();
                try {
                    lz78.compress(inputFilePath, outputFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (choice == 2) {
                System.out.println("Enter the input binary file path:");
                String inputFilePath = scanner.nextLine();
                System.out.println("Enter the output text file path:");
                String outputFilePath = scanner.nextLine();
                try {
                    lz78.decompress(inputFilePath, outputFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}