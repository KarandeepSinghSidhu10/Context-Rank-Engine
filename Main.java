import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MainController controller = new MainController();
        controller.loadData();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==============================");
            System.out.println(" SEARCH ENGINE SYSTEM ");
            System.out.println("==============================");
            System.out.println("1. Search");
            System.out.println("2. Run Test Cases");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter search query: ");
                    String query = sc.nextLine();

                    List<MainController.SearchResult> results = controller.search(query);

                    if (results.isEmpty()) {
                        System.out.println("No Results Found");
                    } else {
                        System.out.println("\nRanked Results:");
                        int rank = 1;
                        for (MainController.SearchResult result : results) {
                            System.out.println("----------------------------------");
                            System.out.println("Rank: " + rank++);
                            System.out.println("Title: " + result.page.title);
                            System.out.println("URL: " + result.page.url);
                            System.out.printf("Score: %.4f%n", result.score);
                            System.out.println("Matched Keywords: " + result.matchedKeywords);
                            System.out.println("Reason: " + result.reason);
                        }
                    }
                    break;

                case "2":
                    controller.runAllTests();
                    break;

                case "3":
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}