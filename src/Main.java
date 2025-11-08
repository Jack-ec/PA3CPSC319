// ====================================================================
// CPSC 319, F25, PA-3: Binary Search trees (BST)
//
// Student Information:
//
// ** Full Name: Jack Chidlaw
//
// ** UCID: 30187692
//
// ** Tutorial Section: B10
//
// ====================================================================
//AI tools disclaimer: Generative ai tools from open ai were used to compare output when testing the different traversal types output
// All code was written by hand with the help of research tools such as stack overflow, oracle manuals, GeeksforGeeks, etc.

import java.io.*;  // Import for file handling
import java.util.*; // Import for utility classes like Scanner

// Represents a node in the Binary Search Tree (BST)
class TreeNode {
    String data;
    int Occurences;
    TreeNode left;
    TreeNode right;

    // Constructor initializes a new node with the given word
    public TreeNode(String word) {
        this.data = word;
        this.Occurences = 1;
        this.left = null;
        this.right = null;
    }
}

// Binary Search Tree implementation for storing and analyzing words
class BinarySearchTree {
    TreeNode root;
    int totalWords;
    int uniqueWords;
    TreeNode mostFrequentNode;

    // Inserts a word into the Binary Search Tree (BST)
    public void insert(String word) {
        root = insertRecursive(root, word);
    }

    // Helper function to recursively insert a word into the BST
    private TreeNode insertRecursive(TreeNode node, String word) {
        if (node == null) {
            node = new TreeNode(word);
            if (mostFrequentNode == null) {
                mostFrequentNode = node;
            }
            uniqueWords++;
            return node;
        }
        if (word.compareToIgnoreCase(node.data) < 0) {
            node.left = insertRecursive(node.left, word);
        }
        else if (word.compareToIgnoreCase(node.data) > 0) {
            node.right = insertRecursive(node.right, word);
        }
        else {
            node.Occurences++;
            if (mostFrequentNode == null || mostFrequentNode.Occurences < node.Occurences) {
                mostFrequentNode = node;
            }
        }
        return node;
    }

    // Returns the traversal output as a string based on the selected traversal type
    public String getTraversalOutput(int type) {
        StringBuilder sb = new StringBuilder();
        if (type == 1) {
                traverseInOrder(root, sb);
        }
        else if (type == 2) {
            traversePreOrder(root, sb);
        }
        else if (type == 3) {
            traversePostOrder(root, sb);
        }
        else {
            System.err.println("Error: Please choose a valid traversal type");
        }
        return sb.toString();
    }
    // TRAVERSAL TYPES ==============================================================================

    // Performs in-order traversal (Left, Root, Right)
    private void traverseInOrder(TreeNode node, StringBuilder result) {
        if (node != null) {
            traverseInOrder(node.left, result);
            result.append(node.data).append(" ");
            traverseInOrder(node.right, result);
        }
    }

    // Performs pre-order traversal (Root, Left, Right)
    private void traversePreOrder(TreeNode node, StringBuilder result) {
        if (node != null) {
            result.append(node.data).append(" ");
            traversePreOrder(node.left, result);
            traversePreOrder(node.right, result);
        }
    }

    // Performs post-order traversal (Left, Right, Root)
    private void traversePostOrder(TreeNode node, StringBuilder result) {
        if (node != null) {
            traversePostOrder(node.left, result);
            traversePostOrder(node.right, result);
            result.append(node.data).append(" ");
        }
    }

    // ===========================================================================================================

    // Computes and returns the total number of words in the BST, including duplicates
    public int getTotalWords() {
        totalWords = 0;
        countWords(root);
        return totalWords;
    }

    // Helper function to count words recursively in the BST
    private void countWords(TreeNode node) {
        if (node != null) {
            totalWords = totalWords + node.Occurences;
            countWords(node.left);
            countWords(node.right);
        }
    }

    // Returns the count of unique words stored in the BST
    public int getUniqueWords() {
        return uniqueWords;
    }

    // Returns the most frequent word along with its occurrence count
    public String getMostFrequentWord() {
        String word = mostFrequentNode.data;
        return word + " (" + mostFrequentNode.Occurences + " times)";
    }

    // Computes and returns the height of the BST
    public int getTreeHeight() {
        return getHeight(root);
    }

    // Helper function to calculate the height of the BST recursively
    private int getHeight(TreeNode node) {
        if (node == null) {
            return -1;
        }
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    // Searches for a word in the BST and returns its frequency
    public int searchWord(String word) {
        return searchWordRecursive(root, word);
    }

    // Helper function to recursively search for a word in the BST
    private int searchWordRecursive(TreeNode node, String word) {
        if (node == null) {
            return 0;
        }
        if (word.equals(node.data)) {
            return node.Occurences;
        }
        else if (word.compareTo(node.data) < 0) {
            return searchWordRecursive(node.left, word);
        }
        else {
            return searchWordRecursive(node.right, word);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter input file name: ");
        String filename = scanner.nextLine(); // Read file name from user input

        BinarySearchTree bst = new BinarySearchTree(); // Create BST instance
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) { // Read file line by line
                // Jack Chidlaw Convert to lowercase, change punctuation into space chars as to not accidentally combine 2 words into one, and split into words
                String[] words = line.toLowerCase().replaceAll("[^a-zA-Z ]", " ").split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) { // Ensure word is not empty
                        bst.insert(word); // Insert word into BST
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage()); // Handle file errors
            return;
        }

        // Display statistics
        System.out.println("Total words: " + bst.getTotalWords());
        System.out.println("Unique words: " + bst.getUniqueWords());
        System.out.println("Most frequent word: " + bst.getMostFrequentWord());
        System.out.println("Tree height: " + bst.getTreeHeight());

        while (true) {
            System.out.println("Enter a word to search (or type 'traverse' to display tree, 'exit' to quit): ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("exit")) break;
            if (input.equals("traverse")) {
                System.out.println("Choose BST traversal method: 1 = IN-ORDER, 2 = PRE-ORDER, 3 = POST-ORDER");
                String choice = scanner.nextLine();
                try {
                    int traversalType = Integer.parseInt(choice);
                    System.out.println(bst.getTraversalOutput(traversalType));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter 1, 2, or 3.");
                }
                continue;
            }

            int frequency = bst.searchWord(input);
            System.out.println("Word '" + input + "' appears " + frequency + " time(s) in the text.");
        }
    }
}
