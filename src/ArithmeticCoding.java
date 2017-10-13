
public class ArithmeticCoding {
    private int A = 31;
    private int B = 32;
    private final int HIGH = 0x7fffffff;
    private final int HALF = 0x40000000;
    private final int QUARTER = 0x20000000;
    private final int THREE_QUARTERS = 0x60000000;
    private StringBuilder encoded = new StringBuilder();
    
    
    public int getBit() {
        while(A + 1 > encoded.length()) {
            encoded.append("0");
        }
        char currentChar = encoded.charAt(A++);
        int bit = Character.getNumericValue(currentChar);
        return bit;
    }
    
    public ArithmeticSymbol[] wrapSymbols(String sourceSymbols, float[] probabilities) {
        int numSymbol = sourceSymbols.length();
        ArithmeticSymbol[] sourceSyms = new ArithmeticSymbol[numSymbol];
        for (int i = 0; i < numSymbol; i++) {
            if (i == 0) {
                ArithmeticSymbol holder = new ArithmeticSymbol(sourceSymbols.charAt(i));
                holder.setLow(0);
                holder.setHigh(probabilities[i]);
                System.out.println(holder.getSymbol() + " " + holder.getLow() + " " + holder.getHigh());
                sourceSyms[i] = holder;
            } else {
                ArithmeticSymbol holder = new ArithmeticSymbol(sourceSymbols.charAt(i));
                holder.setLow(sourceSyms[i-1].getHigh());
                holder.setHigh(holder.getLow() + probabilities[i]);
                System.out.println(holder.getSymbol() + " " + holder.getLow() + " " + holder.getHigh());
                sourceSyms[i] = holder;
            }
        }
        return sourceSyms;
    }
    
//    public ArithmeticSymbol[] wrapSymbols(String sourceSymbols, float[] probabilities) {
//        int numSymbol = sourceSymbols.length();
//        float[] lowRange = new float[numSymbol];
//        float[] highRange = new float[numSymbol];
//        ArithmeticSymbol[] sourceSyms = new ArithmeticSymbol[numSymbol];
//        lowRange[0] = 0;
//        for (int j = 0; j < numSymbol - 1; j++) {
//            if (j == 0) {
//                lowRange[j + 1] = probabilities[j];
//            } else {
//                lowRange[j + 1] = probabilities[j-1] + probabilities[j];
//            }
//        }
//        
//        for (int k = 0; k < numSymbol; k++) {
//            highRange[k] = lowRange[k] + probabilities[k];
//        }
//        
//        for (int i = 0; i < numSymbol; i++) {
//            ArithmeticSymbol holder = new ArithmeticSymbol(sourceSymbols.charAt(i));
//            holder.setLow(lowRange[i]);
//            holder.setHigh(highRange[i]);
//            System.out.println(holder.getSymbol() + " " + holder.getLow() + " " + holder.getHigh());
//            sourceSyms[i] = holder;
//        }
//        return sourceSyms;
//    }
        
    public StringBuilder generateArithmeticCoding(ArithmeticSymbol[] sourceSyms, String sourceMessage, char eod) {
        //int high = HIGH - 1;
        int high = HIGH - 1;
        int low = 0;
        int underCount = 0;
        long range;
        int pointer = 0;
                
        while (true) {
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
            //while (true) {
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
                high &= HIGH;
                low &= HIGH;                
            }
            
            while ((low >= QUARTER) && (high < THREE_QUARTERS)) {
                underCount += 1;
                low -= QUARTER;
                high -= QUARTER;
                high <<= 1;
                high |= 1;
                low <<= 1;
                high &= HIGH;
                low &= HIGH;
            }
//              if (high < HALF) {
//                  encoded.append('0');
//                  while(underCount > 0) {
//                      encoded.append('1');
//                      underCount -= 1;
//                  }
//              } else if (low >= HALF) {
//                  encoded.append('1');
//                  while(underCount > 0) {
//                      encoded.append('0');
//                      underCount -= 1;
//                  }
//              } else if ((low >= QUARTER) && (high < THREE_QUARTERS)) {
//                  underCount += 1;
//                  low -= QUARTER;
//                  high -= QUARTER;
//              } else {
//                  break;
//              }
//              high <<= 1;
//              high |= 1;
//              low <<= 1;
//              high &= HIGH;
//              low &= HIGH;
            //}
            if (sym.getSymbol() == eod) {
                break;
            }
            pointer++;
        }
        
        //underCount ++;
        if (low < QUARTER) {
            encoded.append('0');
            underCount ++;
            while(underCount > 0) {
                encoded.append('1');
                underCount -= 1;
            }
        } else {
            encoded.append('1');
//            while(underCount > 0) {
//                encoded.append('0');
//                underCount -= 1;
//            }
        }
        
        encoded.append(Integer.toBinaryString(high));
        return encoded;
    }
    
    public StringBuilder arithmeticDecoder(StringBuilder encodedMessage, ArithmeticSymbol[] sourceSyms, char eod) {
        System.out.println(encodedMessage);
        int first = 0;
        int Second = 31;
        int buf = Integer.parseInt(encodedMessage.substring(first, Second), 2);
        //int high = HIGH - 1;
        int high = HIGH - 1;
        int low = 0;
        long range;
        StringBuilder decodedMessage = new StringBuilder();
        
        while (true) {
            ArithmeticSymbol sym  = null;
            range = high - low + 1;
            for (ArithmeticSymbol sourceSym : sourceSyms) {
                int tempHigh = low + (int) (range * sourceSym.getHigh()) - 1;
                int tempLow = low + (int) (range * sourceSym.getLow());
                if (tempLow <= buf && buf <= tempHigh) {
                    sym = sourceSym;
                }                       
            }
            
            System.out.println(sym.getSymbol());
            decodedMessage.append(sym.getSymbol());
            
            if (sym.getSymbol() == eod) {
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
                high &= HIGH;
                low &= HIGH;
                buf &= HIGH;
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
                high &= HIGH;
                low &= HIGH;
                buf &= HIGH;                
            }            
            
//            while (true) {
//              if (low >= HALF || high < HALF) {
//                  high <<= 1;
//                  high |= 1;
//                  low <<= 1;
//                  buf = Integer.parseInt(encodedMessage.substring(++first, ++Second), 2);
//              } else if ((low >= QUARTER) && (high < THREE_QUARTERS)) {
//                  low -= QUARTER;
//                  high -= QUARTER;
//                  high <<= 1;
//                  high |= 1;
//                  low <<= 1;
//                  if (encodedMessage.charAt(++first) == '0') {
//                      encodedMessage.replace(first, first + 1, "1");
//                  } else {
//                      encodedMessage.replace(first, first + 1, "0");
//                  }
//                  buf = Integer.parseInt(encodedMessage.substring(first, ++Second), 2);
//                  
//              } else {
//                  break;
//              }
//              high &= HIGH;
//              low &= HIGH;
//            }
        }

        return decodedMessage;
   }
}
