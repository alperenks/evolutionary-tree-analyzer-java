import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EvolutionaryTree tree = new EvolutionaryTree();
        boolean isDatasetLoaded = false;

        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;

            while (!exit) {
                System.out.println("\nMenu:");
                System.out.println("1. Load Dataset");
                System.out.println("2. Search for species");
                System.out.println("3. Traverse Tree");
                System.out.println("4. Print Subtree");
                System.out.println("5. Print Ancestor Path");
                System.out.println("6. Find Common Ancestor");
                System.out.println("7. Calculate Tree Metrics");
                System.out.println("8. Print Longest Path");
                System.out.println("9. Exit");
                System.out.print("Enter your choice: ");

                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }

                switch (choice) {
                    case 1 -> {

                        try {
                            tree.loadNodes("treeoflife_nodes.csv");
                            tree.loadLinks("treeoflife_links.csv");
                            isDatasetLoaded = true;
                            System.out.println("Dataset loaded successfully.");
                        } catch (Exception e) {
                            System.out.println("Error loading dataset: " + e.getMessage());
                        }
                    }
                    case 2 -> {
                        if (!isDatasetLoaded) {
                            System.out.println("Please load the dataset first (Option 1).");
                            continue;
                        }
                        System.out.print("Enter species ID: ");
                        Integer nodeId = getValidIntegerInput(scanner);
                        if (nodeId != null) {
                            tree.searchSpecies(nodeId);
                        }
                    }
                    case 3 -> {
                        if (!isDatasetLoaded) {
                            System.out.println("Please load the dataset first (Option 1).");
                            continue;
                        }
                        tree.traverseTree();
                    }
                    case 4 -> {
                        if (!isDatasetLoaded) {
                            System.out.println("Please load the dataset first (Option 1).");
                            continue;
                        }
                        System.out.print("Enter species ID: ");
                        Integer nodeId = getValidIntegerInput(scanner);
                        if (nodeId != null) {
                            tree.printSubtree(nodeId);
                        }
                    }
                    case 5 -> {
                        if (!isDatasetLoaded) {
                            System.out.println("Please load the dataset first (Option 1).");
                            continue;
                        }
                        System.out.print("Enter species ID: ");
                        Integer nodeId = getValidIntegerInput(scanner);
                        if (nodeId != null) {
                           tree.printAncestorPath(nodeId);
                        }
                    }
                    case 6 -> {
                        if (!isDatasetLoaded) {
                            System.out.println("Please load the dataset first (Option 1).");
                            continue;
                        }
                        System.out.print("Enter first species ID: ");
                        Integer nodeId1 = getValidIntegerInput(scanner);
                        System.out.print("Enter second species ID: ");
                        Integer nodeId2 = getValidIntegerInput(scanner);
                        if (nodeId1 != null && nodeId2 != null) {
                            tree.findCommonAncestorID(nodeId1, nodeId2);
                        }
                    }
                    case 7 -> {
                        if (!isDatasetLoaded) {
                            System.out.println("Please load the dataset first (Option 1).");
                            continue;
                        }
                        tree.calculateTreeMetrics();
                    }
                    case 8 -> {
                        if (!isDatasetLoaded) {
                            System.out.println("Please load the dataset first (Option 1).");
                            continue;
                        }
                        tree.printLongestPath();
                    }
                    case 9 -> {
                        exit = true;
                        System.out.println("Exiting the program.");
                    }
                    default -> System.out.println("Invalid choice. Please select a valid option.");
                }
            }
        }
    }


      //Validates integer input from the user.

    private static Integer getValidIntegerInput(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            return null;
        }
    }
}
