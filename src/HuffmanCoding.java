import java.util.*;

public class HuffmanCoding {
    
    public HuffmanNode buildTree(String text, float[] probabilities) {
        PriorityQueue<HuffmanNode> huffmanTree = new PriorityQueue<>();     //Origanizes based on probability
        
        for (int x = 0; x < probabilities.length; x++) {        //At first all are leafs
           huffmanTree.add(new HuffmanNode(text.charAt(x),probabilities[x]));
       }
        
        while (huffmanTree.size() > 1) {        //Keep extracting the lowest two nodes and combining until one is left
            HuffmanNode first = huffmanTree.poll();
            HuffmanNode second = huffmanTree.poll();            
            HuffmanNode combined = new HuffmanNode(first.getProbability() + second.getProbability(), first, second);
            huffmanTree.add(combined);
        }
        return huffmanTree.poll();
    }
    
    //Builds the encoding for each symbol by traversing the tree until a leaf is reached 
    public HashMap buildEncoding(HuffmanNode tree, StringBuilder sourceSymbols, HashMap code) {
        if (tree.getLeft() != null) {
            sourceSymbols.append("0");
            buildEncoding(tree.getLeft(), sourceSymbols, code);
            sourceSymbols.deleteCharAt(sourceSymbols.length() - 1);     //Going up the tree so delete
        }
        
        if (tree.getRight() != null) {
            sourceSymbols.append("1");
            buildEncoding(tree.getRight(), sourceSymbols, code);
            sourceSymbols.deleteCharAt(sourceSymbols.length() - 1);
        }
        
        if (tree.getRight() == null && tree.getLeft() == null) {
            code.put(tree.getSymbol(), sourceSymbols.toString());
        }
        
        return code;        //Symbol with encoding stored in hashmap
    }
    
    //Just a matter of getting the encoding of each symbol from the hash map
    public String encode(HashMap sourceSymbols, String sourceMessage) {
        String encodedWord = "";
        for (int i = 0; i < sourceMessage.length(); i++) {
            encodedWord += sourceSymbols.get(sourceMessage.charAt(i));
        }
        return encodedWord;
    }
    
    //Traverse the tree based on the encoded message when a leaf is reached add this to decoded message
    public String decode(HuffmanNode tree, String encodedMessage) {
        HuffmanNode root = tree;
        String decodedMessage = "";
        for (int i = 0; i < encodedMessage.length(); i++) {
            if (encodedMessage.charAt(i) == '0') {
                tree = tree.getLeft();
            } else {
                tree = tree.getRight();
            } 
            
            if (tree.getLeft() == null && tree.getRight() == null) {
                decodedMessage += tree.getSymbol();
                tree = root;
            }
        }
        
        return decodedMessage;
    }
}
