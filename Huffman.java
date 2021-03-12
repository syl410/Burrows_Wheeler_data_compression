// This Huffman program can compress 0 ~ 2GB files. 

import java.util.PriorityQueue;

public class Huffman {
    private static final int RADIX = 256;
    private static final int BITS = 8;

    private static class Node implements Comparable<Node> {
        private final char value;
        private final int freq; 
        private final Node left;
        private final Node right;

        // Node constructor
        public Node(char value, int freq, Node left, Node right) {
            this.value = value;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        // check if a Node is leaf
        private boolean isLeaf() {
            return left == null && right == null;
        }

        // compare frequency of two Nodes
        public int compareTo(Node otherN) {
            if (this.freq > otherN.freq) return 1;
            else if (this.freq == otherN.freq) return 0;
            else return -1;
        }
    }

    // compression
    public static void compress() {
        // read string from input
        String inputStr = BinaryStdIn.readString();
        
        // get frequency of each radix
        int[] freqArr = new int[RADIX];
        getFreq(freqArr, inputStr);

        // write length
        BinaryStdOut.write(inputStr.length());

        // construct trie from freArr
        Node root = constructTrie(freqArr);

        // write trie
        writeTrie(root);

        // build symbol table from trie
        String[] trieST = new String[RADIX];
        String initCode = ""; // initCode for a char: "01010"
        buildST(root, trieST, initCode);
        
        // encode inputStr
        for (char c : inputStr.toCharArray()) {
            String code = trieST[c];
            writeBinaryCode(code);
        }
        BinaryStdOut.close();
    }

    // decompression
    public static void expand() {
        int inputLen = BinaryStdIn.readInt();
        
        // read flat trie and and re-build trie
        Node root = rebuildTrie();
        
        // decode inputStr using trie
        for (int i = 0; i < inputLen; i++) {
            Node n = root;
            while (!n.isLeaf()) {
                if (BinaryStdIn.readBoolean()) {
                    n = n.right;
                } else {
                    n = n.left;
                }
            }
            BinaryStdOut.write(n.value, BITS);
        }
        BinaryStdOut.close();
    }

    // get count frequency of each RADIX
    private static void getFreq(int[] freqArr, String inputStr) {
        for (char c : inputStr.toCharArray()) {
            freqArr[c]++;
        }
    }

    // construct trie from freArr
    private static Node constructTrie(int[] freqArr) {
        PriorityQueue<Node> freqPQ = new PriorityQueue<>();
        // create a Node for each element in freqArr and add to PQ
        for (int i = 0; i < RADIX; i++) {
            if (freqArr[i] > 0) {
                Node n = new Node((char)i, freqArr[i], null, null);
                freqPQ.add(n);
            }
        }
        
        // remove two min-freq Nodes and merge one Node
        // repeat until one Node left
        while(freqPQ.size() > 1) {
            Node n1 = freqPQ.poll();
            Node n2 = freqPQ.poll();
            Node n = new Node('\0', n1.freq + n2.freq, n1, n2);
            freqPQ.add(n);
        }

        return freqPQ.poll();
    }

    // write trie
    // internal Node is 0, leaf is 1 + char
    private static void writeTrie(Node n) {
        // System.out.println("DEBUG!");
        if (n.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(n.value, BITS);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(n.left);
        writeTrie(n.right);
    }

    // build symbol table from trie
    private static void buildST(Node n, String[] trieST, String code) {
        if (n.isLeaf()) {
            trieST[n.value] = code;
            return;
        }
        buildST(n.left, trieST, code + '0');
        buildST(n.right, trieST, code + '1');
    }

    // write binary code: "01010"
    private static void writeBinaryCode(String code) {
        for (char bit : code.toCharArray()) {
            if (bit == '0') {
                BinaryStdOut.write(false);
            } else {
                BinaryStdOut.write(true);
            }
        }
    }

    private static Node rebuildTrie() {
        if (BinaryStdIn.readBoolean()) { // leaf node
            return new Node(BinaryStdIn.readChar(), 0, null, null);
        } else {
            Node left = rebuildTrie();
            Node right = rebuildTrie();
            return new Node('\0', 0, left, right);
        }
    }

    // if args[0] is "-", apply compression
    // if args[0] is "+", apply decompression
    public static void main(String[] args) {
        if (args == null || args.length == 0) 
            throw new IllegalArgumentException();
        if (args[0].equals("-")) 
            compress();
        else if (args[0].equals("+")) 
            expand();
        else 
            throw new IllegalArgumentException();

    }
}
