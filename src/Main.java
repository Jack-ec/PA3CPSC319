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

import java.io.*;  // Import for file handling
import java.util.*; // Import for utility classes like Scanner

// TODO #1
// Represents a node in the Binary Search Tree (BST)
class TreeNode {
    // (1) The word stored in this node
    String data;
    // (2) Count of occurrences of the word
    int Occurences;
    // (3) Pointers to left and right children
    TreeNode left;
    TreeNode right;

    // Constructor initializes a new node with the given word
    public TreeNode(String word) {
        // (1) Assigns the input word to the node
        this.data = word;
        // (2) New word starts with frequency of 1
        this.Occurences = 1;
        // (3) Initially, no children
        this.left = null;
        this.right = null;
    }
}

// TODO #2
// Binary Search Tree implementation for storing and analyzing words
class BinarySearchTree {
    // (1) Root of the BST
    TreeNode root;
    // (2) Total words inserted (including duplicates)
    int totalWords;
    // (3) Count of unique words
    int uniqueWords;
    // (4) Stores the node with the highest frequency
    TreeNode mostFrequentNode;

    // Inserts a word into the Binary Search Tree (BST)
    public void insert(String word) {
        // (1) Calls the recursive function to insert the word, starting from the root node
        insertRecursive(root, word);
    }

    // TODO #3
    // Helper function to recursively insert a word into the BST
    private TreeNode insertRecursive(TreeNode node, String word) {
        // **BST Property**: Smaller words go to the left subtree, larger words go to the right.

        // (1) If the current node is null, insert a new node with the word
        if (node == null) {
            node = new TreeNode(word);
            // (2) Increase the unique word count
            uniqueWords++;
            // (3) Return the updated node
            return node;
        }
        // (4) If word is smaller, insert into left subtree
        if (word.length() < node.data.length()) {
            node.left = insertRecursive(node.left, word);
        }
        // (5) Else if word is larger, insert into right subtree
        else if (word.length() > node.data.length()) {
            node.right = insertRecursive(node.right, word);
        }
        // (6) Else the word already exists, so increase its frequency
        else {
            node.Occurences++;
            // (7) Update most frequent word tracking
            if (mostFrequentNode.Occurences < node.Occurences) {
                mostFrequentNode = node;
            }
        }
        // (8) Return the updated node
        return node;
    }

    // TODO #4
    // Returns the traversal output as a string based on the selected traversal type
    public String getTraversalOutput(int type) {
        // (1) Creates a StringBuilder to store the traversal result
            StringBuilder sb = new StringBuilder();
        // (2) Checks the value of 'type' to determine which traversal to perform
        // (3) If type is 1, perform in-order traversal
        if (type == 1) {
                traverseInOrder(root, sb);
        }
        // (4) If type is 2, perform pre-order traversal
        if (type == 2) {
            traversePreOrder(root, sb);
        }
        // (5) If type is 3, perform post-order traversal
        if (type == 3) {
            traversePostOrder(root, sb);
        }
        // (6) If input is invalid, return an error message
        else {
            System.err.println("Error: getTraversalOutput");
        }
        return sb.toString();
    }

    // TODO #5
    // Performs in-order traversal (Left, Root, Right)
    private void traverseInOrder(TreeNode node, StringBuilder result) {
        // (1) Check if the current node is not null before processing
        if (node != null) {
            // (2) Recursively traverse the left subtree (Left)
            traverseInOrder(node.left, result);
            // (3) Append the current node's word to the result (Root)
            result.append(node.data);
            // (4) Recursively traverse the right subtree (Right)
            traverseInOrder(node.right, result);
        }
    }

    // TODO #6
    // Performs pre-order traversal (Root, Left, Right)
    private void traversePreOrder(TreeNode node, StringBuilder result) {
        // (1) Check if the current node is not null before processing
        if (node != null) {
            // (2) Append the current node's word to the result (Root)
            result.append(node.data);
            // (3) Recursively traverse the left subtree (Left)
            traversePreOrder(node.left, result);
            // (4) Recursively traverse the right subtree (Right)
            traversePreOrder(node.right, result);
        }
    }

    // TODO #7
    // Performs post-order traversal (Left, Right, Root)
    private void traversePostOrder(TreeNode node, StringBuilder result) {
        // (1) Check if the current node is not null before processing
        if (node != null) {
            // (2) Recursively traverse the left subtree (Left)
            traversePostOrder(node.left, result);
            // (3) Recursively traverse the right subtree (Right)
            traversePostOrder(node.right, result);
            // (4) Append the current node's word to the result (Root)
            result.append(node.data);
        }
    }

    // TODO #8
    // Computes and returns the total number of words in the BST, including duplicates
    public int getTotalWords() {
        // (1) Reset the total word count to 0 before recalculating
        totalWords = 0;
        // (2) Call the helper function to count word occurrences
        countWords(root);
        // (3) Return the computed total number of words
        return totalWords;
    }

    // TODO #9
    // Helper function to count words recursively in the BST
    private void countWords(TreeNode node) {
        // (1) Check if the current node is not null before processing
        if (node != null) {
            // (2) Add the frequency of the current node's word to the total count
            totalWords = totalWords + node.Occurences;
            // (3) Recursively traverse left subtree
            countWords(node.left);
            // (4) Recursively traverse right subtree
            countWords(node.right);
        }
    }

    // TODO #10
    // Returns the count of unique words stored in the BST
    public int getUniqueWords() {
        // (1) Return the total number of unique words
        return uniqueWords;
    }

    // TODO #11
    // Returns the most frequent word along with its occurrence count
    public String getMostFrequentWord() {
        // (1) Retrieve the word stored in mostFrequentNode
        String word = mostFrequentNode.data;
        // (2) Append the frequency count in the format "word (X times)"
        return word + "(" + mostFrequentNode.Occurences + "times)";
    }

    // TODO #12
    // Computes and returns the height of the BST
    public int getTreeHeight() {
        // (1) Calls the helper function getHeight() starting from the root
        return getHeight(root);
    }

    // TODO #13
    // Helper function to calculate the height of the BST recursively
    private int getHeight(TreeNode node) {
        // **Clarification**: Height is measured in edges, not nodes.
        // ** As per lecture slides: 'Trees - (3) Binary (Part 1) - Levels & Heights'
        // This means:
        // - An empty tree has height -1 (since there are no edges).
        // - A tree with a single node has height 0.

        // (1) Base case: If the tree is empty (null node), return height -1.
        if (node == null) {
            return -1;
        }
        // (2) Get the height of the left subtree.
        int leftHeight = getHeight(node.left);
        // (3) Get the height of the right subtree.
        int rightHeight = getHeight(node.right);

        // (4) Return the maximum height between left and right subtrees + 1.
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // TODO #14
    // Searches for a word in the BST and returns its frequency
    public int searchWord(String word) {
        // (1) Calls the recursive helper function, starting from the root
        return searchWordRecursive(root, word);
    }

    // TODO #15
    // Helper function to recursively search for a word in the BST
    private int searchWordRecursive(TreeNode node, String word) {
        // (1) Base case: If node is null, the word is not in the BST
        if (node == null) {
            return 0;
        }
        // (2) If word matches current node, return its frequency
        if (word.equals(node.data)) {
            return node.Occurences;
        }
        // (3) If word is smaller, search left subtree
        else if (word.compareTo(node.data) < 0) {
            return searchWordRecursive(node.left, word);
        }
        // (4) If word is larger, search right subtree
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
                // Convert to lowercase, remove non-letter characters, and split into words
                String[] words = line.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
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
