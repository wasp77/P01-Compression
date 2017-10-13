
public class ArithmeticSymbol {
    private char symbol;
    private float high;
    private float low;
    
    public ArithmeticSymbol (char symbol) {
        this.symbol = symbol;
    }
   
    public void setHigh (float high) {
       this.high = high;
    }
   
    public void setLow (float low) {
       this.low = low;
    }
    
    public char getSymbol () {
        return this.symbol;
    }
    
    public float getHigh () {
        return this.high;
    }
    
    public float getLow () {
        return this.low;
    }
}
