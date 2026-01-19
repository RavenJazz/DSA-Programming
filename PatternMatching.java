import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PatternMatching {

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        String choice = "";

        do {
            // 1. Get the filename
            System.out.print("Enter filename: ");
            String filename = inputScanner.nextLine();

            // 2. Read and Display TEXT
            File file = new File(filename);
            String textContent = "";

            try {
                Scanner fileReader = new Scanner(file);
                // Read the entire file content. 
                // This loop appends lines if there are multiple, simulating a single text block.
                StringBuilder sb = new StringBuilder();
                while (fileReader.hasNextLine()) {
                    sb.append(fileReader.nextLine());
                }
                fileReader.close();
                textContent = sb.toString();

                System.out.println("TEXT");
                System.out.println(textContent);

                // 3. Get the PATTERN
                System.out.print("Enter PATTERN: ");
                String pattern = inputScanner.nextLine();

                // 4. Perform the Matching
                // Logic: "Shift one position to the right in TEXT" (Brute Force)
                int matches = 0;
                int n = textContent.length();
                int m = pattern.length();

                // We only need to check up to the point where the pattern fits
                for (int i = 0; i <= n - m; i++) {
                    int j = 0;
                    boolean matchFound = true;

                    // Check characters for the current position
                    while (j < m) {
                        if (textContent.charAt(i + j) != pattern.charAt(j)) {
                            matchFound = false;
                            // Mismatch occurs: "backtrack to beginning of pattern" 
                            // (by breaking loop) and "shift one position in text" 
                            // (next iteration of outer loop 'i')
                            break; 
                        }
                        j++;
                    }

                    if (matchFound) {
                        matches++;
                    }
                }

                // 5. Output Result
                if (matches > 0) {
                    System.out.println("Total number of matches: " + matches);
                } else {
                    System.out.println("No matches found!");
                }

            } catch (FileNotFoundException e) {
                System.out.println("Error: File not found. Please create the file first.");
            }

            // 6. Ask to continue
            System.out.print("\nAgain (y/n): ");
            choice = inputScanner.nextLine();
            System.out.println(); // Just for spacing

        } while (choice.equalsIgnoreCase("y"));
        
        inputScanner.close();
    }
}