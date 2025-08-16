import java.io.*;
import java.util.*;

public class EvolutionaryTree {
    private Map<Integer, TreeNode> nodeTable;
    private TreeNode root;

    public EvolutionaryTree() {
        nodeTable = new HashMap<>();
    }
    public void loadNodes(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            // Read header line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {

                    String[] parts = line.split(",");

                    // Ensure there are exactly 8 columns
                    if (parts.length != 8) {
                        System.out.println("Skipping line " + lineNumber + ": Incorrect number of columns.");
                        continue;
                    }


                    int nodeId = Integer.parseInt(parts[0].trim());
                    String nodeName = parts[1].trim().equalsIgnoreCase("none") ? null : parts[1].trim();
                    int childCount = Integer.parseInt(parts[2].trim());
                    boolean isLeaf = parts[3].trim().equals("1");
                    String tolOrgLinkRaw = parts[4].trim();
                    boolean isExtinct = parts[5].trim().equals("1");
                    int confidence = Integer.parseInt(parts[6].trim());
                    int phylesis = Integer.parseInt(parts[7].trim());

                    // Check link value: If "1", generate a custom URL
                    String tolOrgLink;
                    if ("1".equals(tolOrgLinkRaw)) {
                        tolOrgLink = "http://tolweb.org/" + (nodeName != null ? nodeName.replace(" ", "_") : "Unknown") + "/" + nodeId;
                    } else {
                        tolOrgLink = tolOrgLinkRaw;
                    }


                    TreeNode node = new TreeNode(nodeId, nodeName, childCount, isLeaf, tolOrgLink, isExtinct, confidence, phylesis);
                    nodeTable.put(nodeId, node);

                } catch (NumberFormatException e) {
                    System.out.println("Skipping line " + lineNumber + ": Invalid number format - " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Skipping line " + lineNumber + ": Unexpected error - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void loadLinks(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int parentId = Integer.parseInt(parts[0]);
            int childId = Integer.parseInt(parts[1]);

            TreeNode parent = nodeTable.get(parentId);
            TreeNode child = nodeTable.get(childId);

            if (parent != null && child != null) {
                parent.children.add(child);
                child.parent = parent;
            }
        }
        reader.close();
        this.root = findRoot();
    }

    private TreeNode findRoot() {
        for (TreeNode node : nodeTable.values()) {
            if (node.parent == null) {
                return node;
            }
        }
        return null;
    }

    public void searchSpecies(int nodeId) {
        TreeNode node = nodeTable.get(nodeId);
        if (node != null) {
            System.out.println("Id: " + node.nodeId);
            System.out.println("Name: " + (node.nodeName != null ? node.nodeName : "N/A"));
            System.out.println("Child count: " + node.childCount);
            System.out.println("Leaf node: " + (node.isLeaf ? "yes" : "no"));
            System.out.println("Link: " + node.tolOrgLink);
            System.out.println("Extinct: " + (node.isExtinct ? "yes" : "no"));
            System.out.println("Confidence: " + getConfidenceDescription(node.confidence));
            System.out.println("Phylesis: " + getPhylesisDescription(node.phylesis));
        } else {
            System.out.println("Species not found.");
        }
    }

    private String getConfidenceDescription(int confidence) {
        return switch (confidence) {
            case 0 -> "confident position";
            case 1 -> "problematic position";
            default -> "unspecified position";
        };
    }

    private String getPhylesisDescription(int phylesis) {
        return switch (phylesis) {
            case 0 -> "monophyletic";
            case 1 -> "uncertain monophyly";
            default -> "not monophyletic";
        };
    }

    public void traversePreOrder(TreeNode node, BufferedWriter writer) throws IOException {
        if (node == null) return;

        writer.write(node.nodeId + "-" + (node.nodeName != null ? node.nodeName : "N/A") + "\n");
        for (TreeNode child : node.children) {
            traversePreOrder(child, writer);
        }
    }

    public void traverseTree() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("pre-order.txt"));
            traversePreOrder(root, writer);
            writer.close();
            System.out.println("Tree traversal saved to pre-order.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void printSubtreePreOrder(TreeNode node, int depth) {
        if (node == null) return;

        String indent = "-".repeat(depth * 2);
        System.out.println(indent + node.nodeId + "-" + (node.nodeName != null ? node.nodeName : "N/A") + " (" + (node.isExtinct ? "-" : "+") + ")");
        for (TreeNode child : node.children) {
            printSubtreePreOrder(child, depth + 1);
        }
    }

    public void printSubtree(int nodeId) {
        TreeNode node = nodeTable.get(nodeId);
        if (node != null) {
            printSubtreePreOrder(node, 0);
        } else {
            System.out.println("Species not found.");
        }
    }
    public void printAncestorPath(TreeNode node) {
        if (node == null) return;

        List<String> path = new ArrayList<>();
        while (node != null) {
            path.add(node.nodeId + "-" + (node.nodeName != null ? node.nodeName : "N/A"));
            node = node.parent;
        }

        Collections.reverse(path);
        for (String s : path) {
            System.out.println(s);
        }
    }

    public void printAncestorPath(int nodeId) {
        TreeNode node = nodeTable.get(nodeId);
        if (node != null) {
            printAncestorPath(node);
        } else {
            System.out.println("Species not found.");
        }
    }
    public TreeNode findCommonAncestor(TreeNode node1, TreeNode node2) {
        Set<TreeNode> ancestors1 = new HashSet<>();
        while (node1 != null) {
            ancestors1.add(node1);
            node1 = node1.parent;
        }

        while (node2 != null) {
            if (ancestors1.contains(node2)) {
                return node2;
            }
            node2 = node2.parent;
        }

        return null;
    }

    public void findCommonAncestorID(int nodeId1, int nodeId2) {
        TreeNode node1 = nodeTable.get(nodeId1);
        TreeNode node2 = nodeTable.get(nodeId2);
        if (node1 != null && node2 != null) {
            TreeNode ancestor = findCommonAncestor(node1, node2);
            if (ancestor != null) {
                System.out.println("The most recent common ancestor of " + nodeId1 + node1.nodeName + " and " + nodeId2 + node2.nodeName + " is " + ancestor.nodeId + "-" + (ancestor.nodeName != null ? ancestor.nodeName : "N/A"));
            } else {
                System.out.println("No common ancestor found.");
            }
        } else {
            System.out.println("One or both species not found.");
        }
    }
    public int calculateHeight(TreeNode node) {
        if (node == null) return 0;
        int height = 0;
        for (TreeNode child : node.children) {
            height = Math.max(height, calculateHeight(child));
        }
        return height + 1;
    }

    public int calculateDegree(TreeNode node) {
        return node.children.size();
    }

    public int calculateBreadth() {
        int count = 0;
        for (TreeNode node : nodeTable.values()) {
            if (node.children.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public void calculateTreeMetrics() {
        int height = calculateHeight(root);
        int degree = 0;
        int breadth = calculateBreadth();

        for (TreeNode node : nodeTable.values()) {
            degree = Math.max(degree, calculateDegree(node));
        }

        System.out.println("Height: " + height);
        System.out.println("Degree: " + degree);
        System.out.println("Breadth: " + breadth);
    }
    public List<TreeNode> findLongestPath(TreeNode node) {
        if (node == null) return new ArrayList<>();

        List<TreeNode> longestPath = new ArrayList<>();
        for (TreeNode child : node.children) {
            List<TreeNode> path = findLongestPath(child);
            if (path.size() > longestPath.size()) {
                longestPath = path;
            }
        }

        longestPath.add(0, node);
        return longestPath;
    }

    public void printLongestPath() {
        List<TreeNode> longestPath = findLongestPath(root);
        for (TreeNode node : longestPath) {
            System.out.println(node.nodeId + "-" + (node.nodeName != null ? node.nodeName : "N/A"));
        }
    }
}
