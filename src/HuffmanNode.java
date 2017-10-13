
public class HuffmanNode implements Comparable<HuffmanNode> {
    private float probability;
    private char symbol;
    private HuffmanNode right;
    private HuffmanNode left;
    
    public HuffmanNode (char sym, float prob) {
        this.symbol = sym;
        this.probability = prob;
        this.right = null;
        this.left = null;
    }
    
    public HuffmanNode (float prob, HuffmanNode r, HuffmanNode l) {
        this.probability = prob;
        this.right = r;
        this.left = l;
    }
    
    public float getProbability() {
        return this.probability;
    }
    
    public char getSymbol() {
        return this.symbol;
    }
    
    public HuffmanNode getRight() {
        return this.right;
    }
    
    public HuffmanNode getLeft() {
        return this.left;
    }
    
    @Override
    public int compareTo(HuffmanNode node) {
        if (this.probability == node.getProbability()) {
            return 0;
        } else if (this.probability > node.getProbability()) {
            return 1;
        } else {
            return -1;
        }
    }
    
    @Override
    public String toString() {
        return "Symbol: " + this.symbol + " Frequency: " + this.probability; 
    }
    
}
