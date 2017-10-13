
public class ArithmeticCoding {
    private int A = 31;
    private final int HIGH = 0x7fffffff;
    private final int HALF = 0x40000000;
    private final int QUARTER = 0x20000000;
    private final int THREE_QUARTERS = 0x60000000;
    private StringBuilder encoded = new StringBuilder();
    private char EOD;
    
    public ArithmeticCoding(char eod) {
        this.EOD = eod;
    }
    
    //Returns the next bit from the encoded message if there are none left appends zeros
    public int getBit() {
        while(A + 1 > encoded.length()) {
            encoded.append("0");
        }
        char currentChar = encoded.charAt(A++);
        int bit = Character.getNumericValue(currentChar);
        return bit;
    }
    
    //Wraps the source symbols into arithmetic symbols by calculating the high and low range
    public ArithmeticSymbol[] wrapSymbols(String sourceSymbols, float[] probabilities) {
        int numSymbol = sourceSymbols.length();
        ArithmeticSymbol[] sourceSyms = new ArithmeticSymbol[numSymbol];
        for (int i = 0; i < numSymbol; i++) {
            if (i == 0) {       //starts at zero
                ArithmeticSymbol holder = new ArithmeticSymbol(sourceSymbols.charAt(i));
                holder.setLow(0);
                holder.setHigh(probabilities[i]);
                sourceSyms[i] = holder;
            } else {        //After that the low equals the high of the previous symbol
                ArithmeticSymbol holder = new ArithmeticSymbol(sourceSymbols.charAt(i));
                holder.setLow(sourceSyms[i-1].getHigh());
                holder.setHigh(holder.getLow() + probabilities[i]);
                sourceSyms[i] = holder;
            }
        }
        return sourceSyms;
    }
        
    public StringBuilder generateArithmeticCoding(ArithmeticSymbol[] sourceSyms, String sourceMessage) {
        int high = HIGH - 1;
        int low = 0;
        int underCount = 0;
        long range;
        int pointer = 0;
                
        while (true) {      //Match the symbol is the message to an arithmetic symbol
            char current = sourceMessage.charAt(pointer);
            ArithmeticSymbol sym  = null;
            for (ArithmeticSymbol sourceSym : sourceSyms) {
                if (current == sourceSym.getSymbol()) {
                    sym = sourceSym;
                }
            }
            range = high - low + 1;
            high = low + (int)(range * sym.getHigh()) - 1;
            low = low + (int)(range * sym.getLow());
            while(high < HALF || low >= HALF) {
                if (high < HALF) {
                  encoded.append('0');
                  while(underCount > 0) {
                      encoded.append('1');
                      underCount -= 1;
                  }
                } else if (low >= HALF) {
                  encoded.append('1');
                  low -= HALF;
                  high -= HALF;
                  while(underCount > 0) {
                      encoded.append('0');
                      underCount -= 1;
                  }
                }
                high <<= 1;
                high |= 1;
                low <<= 1;                
            }
            
            while ((low >= QUARTER) && (high < THREE_QUARTERS)) {
                underCount += 1;
                low -= QUARTER;
                high -= QUARTER;
                high <<= 1;
                high |= 1;
                low <<= 1;
            }
            if (sym.getSymbol() == EOD) {
                break;
            }
            pointer++;
        }
        
        if (low < QUARTER) {
            encoded.append('0');
            underCount ++;
            while(underCount > 0) {
                encoded.append('1');
                underCount -= 1;
            }
        } else {        //Don't have to append zeros because out getBit function does
            encoded.append('1');
        }
        
        return encoded;
    }
    
    public StringBuilder arithmeticDecoder(ArithmeticSymbol[] sourceSyms) {
        int first = 0;
        int second = 31;
        while (second > encoded.length()) {
            encoded.append("0");
        }
        int buf = Integer.parseInt(encoded.substring(first, second), 2);
        int high = HIGH - 1;
        int low = 0;
        long range;
        StringBuilder decodedMessage = new StringBuilder();
        
        while (true) {
            ArithmeticSymbol sym  = null;
            range = high - low + 1;
            for (ArithmeticSymbol sourceSym : sourceSyms) {     //Check which range the buffer fits in
                int tempHigh = low + (int) (range * sourceSym.getHigh()) - 1;
                int tempLow = low + (int) (range * sourceSym.getLow());
                if (tempLow <= buf && buf <= tempHigh) {
                    sym = sourceSym;
                }                       
            } 
            
            decodedMessage.append(sym.getSymbol());           
            if (sym.getSymbol() == EOD) {
                break;
            }
            
            high = low + (int)(range * sym.getHigh()) - 1;
            low = low + (int)(range * sym.getLow());            
            while(high < HALF || low >= HALF) {
                if (high < HALF) {
                    high <<= 1;
                    high |= 1;
                    low <<= 1;
                    buf <<= 1;
                    buf += getBit();
                } else if (low >= HALF) {
                    low -= HALF;
                    high -= HALF;
                    buf -= HALF;
                    high <<= 1;
                    high |= 1;
                    low <<= 1;  
                    buf <<= 1;
                    buf += getBit();
                }
            }            
            while ((low >= QUARTER) && (high < THREE_QUARTERS)) {
                low -= QUARTER;
                high -= QUARTER;
                buf -= QUARTER;
                    
                high <<= 1;
                high |= 1;
                low <<= 1;  
                buf <<= 1;
                buf += getBit();                
            }            
        }
        return decodedMessage;
   }
}
