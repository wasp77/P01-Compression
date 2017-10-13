import java.util.*;

public class HuffmanCoding {
    public HuffmanNode buildTree(String text, float[] probabilities) {
        PriorityQueue<HuffmanNode> huffmanTree = new PriorityQueue<>();
        
        for (int x = 0; x < probabilities.length; x++) {
           huffmanTree.add(new HuffmanNode(text.charAt(x),probabilities[x]));
       }
        
        while (huffmanTree.size() > 1) {
            HuffmanNode first = huffmanTree.poll();
            HuffmanNode second = huffmanTree.poll();            
            HuffmanNode combined = new HuffmanNode(first.getProbability() + second.getProbability(), first, second);
            huffmanTree.add(combined);
        }
        return huffmanTree.poll();
    }
    
    
    public HashMap buildEncoding(HuffmanNode tree, StringBuilder sourceSymbols, HashMap code) {
        if (tree.getLeft() != null) {
            sourceSymbols.append("0");
            buildEncoding(tree.getLeft(), sourceSymbols, code);
            sourceSymbols.deleteCharAt(sourceSymbols.length() - 1);
        }
        
        if (tree.getRight() != null) {
            sourceSymbols.append("1");
            buildEncoding(tree.getRight(), sourceSymbols, code);
            sourceSymbols.deleteCharAt(sourceSymbols.length() - 1);
        }
        
        if (tree.getRight() == null && tree.getLeft() == null) {
            code.put(tree.getSymbol(), sourceSymbols.toString());
        }
        
        return code;
    }
    
    public String encode(HashMap sourceSymbols, String original) {
        String encodedWord = "";
        for (int i = 0; i < original.length(); i++) {
            encodedWord += sourceSymbols.get(original.charAt(i));
        }
        return encodedWord;
    }
    
    public String decode(HuffmanNode tree, String code) {
        HuffmanNode root = tree;
        String decodedMessage = "";
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '0') {
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
