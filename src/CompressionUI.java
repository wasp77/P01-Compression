
import java.util.HashMap;
import java.util.Random;


public class CompressionUI extends javax.swing.JFrame {
    private static final int SOURCE_LENGTH = 4;

    public CompressionUI() {
        initComponents();
        outputText.setEditable(false);      //Don't let user type in area
    }
    
    public float calculateEntropy (String source, float[] probabilities) {
        float entropy = 0;
        for (int i = 0; i < SOURCE_LENGTH; i++) {
            entropy += probabilities[i] * (Math.log(probabilities[i]) / Math.log(2));
        }
        entropy = Math.abs(entropy);        //Get the positive value same as multiplying by -1
        return entropy;
    }
    
    //Generates a random word based on the source symbols
    public String generateCodeWord(String source) {
        Random rn = new Random();
        int length = rn.nextInt(200) + 4;       //minumum of 4 max of 200
        String codeWord = "";
        for (int i = 0;  i < length - 1; i++) {
            int random = rn.nextInt(source.length());
            if (random == 0) {
                random++;
            }
            codeWord += source.charAt(random);
        }
        codeWord += source.charAt(0);
        return codeWord;
    }
    
    public void runHuffman(String sourceSymbols, float[] probabilities, String codeWord) {
        String encodedWord;
        String decodedWord;
        double averageLength;
        StringBuilder source = new StringBuilder();
        HashMap<Character, String> code = new HashMap<>();
        HuffmanCoding obj = new HuffmanCoding();
        
        HuffmanNode tree = obj.buildTree(sourceSymbols, probabilities);
        code = obj.buildEncoding(tree, source, code);
        encodedWord = obj.encode(code, codeWord);
        decodedWord = obj.decode(tree, encodedWord);
        averageLength = (double)encodedWord.length() / (double)codeWord.length();       //Average length = number of bits/number of symbols
        outputText.append("Huffman Coding:" + "\n");
        outputText.append("Encoded Message: " + encodedWord + "\n");                
        outputText.append("Decoded Message: " + decodedWord + "\n");
        outputText.append("Average Length: " + averageLength + "\n");
        //outputText.append("Execution Time (seconds): " + programTime + "\n");
    }
    
    public void runArithmetic(String sourceSymbols, float[] probabilities, String codeWord) {
        StringBuilder encodedMessage;
        StringBuilder decodedMessage;
        double averageLength;
        char endData = sourceSymbols.charAt(0);
        ArithmeticCoding obj = new ArithmeticCoding(endData);
        
        ArithmeticSymbol[] wrappedSymbols = obj.wrapSymbols(sourceSymbols, probabilities);
        encodedMessage = obj.generateArithmeticCoding(wrappedSymbols, codeWord);       
        decodedMessage = obj.arithmeticDecoder(wrappedSymbols);
        averageLength = (double)encodedMessage.length() / (double)codeWord.length();
        
        outputText.append("\n" + "Arithmetic Coding:" + "\n");
        outputText.append("Encoded Message: " + encodedMessage + "\n");
        outputText.append("Decoded Message: " + decodedMessage + "\n");
        outputText.append("Average Length: " + averageLength);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        firstSource = new javax.swing.JTextField();
        secondSource = new javax.swing.JTextField();
        thirdSource = new javax.swing.JTextField();
        firstProb = new javax.swing.JTextField();
        secondProb = new javax.swing.JTextField();
        thirdProb = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputText = new javax.swing.JTextArea();
        fourthProb = new javax.swing.JTextField();
        fourthSource = new javax.swing.JTextField();
        compressBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Source Symbols:");

        jLabel2.setText("Prababilities:");

        jLabel3.setText("Output:");

        outputText.setColumns(20);
        outputText.setRows(5);
        jScrollPane1.setViewportView(outputText);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(firstSource, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(firstProb, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(secondProb, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(secondSource, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(thirdProb, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(thirdSource, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fourthProb, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fourthSource, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 263, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {firstSource, secondSource});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(firstSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(secondSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(thirdSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fourthSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(firstProb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(secondProb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(thirdProb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fourthProb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)))
        );

        compressBtn.setText("Compress");
        compressBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compressBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(compressBtn)
                .addGap(244, 244, 244))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(compressBtn))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void compressBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compressBtnActionPerformed
        outputText.setText("");
        String codeWord;
        float probCheck = 0;        //Used to check that the probabilities equal 1
        float[] probabilities = new float[SOURCE_LENGTH];
        float entropy;
        String sourceSymbols = firstSource.getText() + secondSource.getText() + thirdSource.getText()
                + fourthSource.getText();
        for (int x = 0; x < SOURCE_LENGTH; x++) {       //Check that no two symbols are the same
            for (int j = SOURCE_LENGTH - 1; j > x; j--) {
                if(sourceSymbols.charAt(j) == sourceSymbols.charAt(x)) {
                    outputText.append("Two of the same symbol hava been entered!");
                    return;
                }
            }
        }
        try {
            probabilities[0] = Float.parseFloat(firstProb.getText());
            probCheck += probabilities[0];
            probabilities[1] = Float.parseFloat(secondProb.getText());
            probCheck += probabilities[1];
            probabilities[2] = Float.parseFloat(thirdProb.getText());
            probCheck += probabilities[2];
            probabilities[3] = Float.parseFloat(fourthProb.getText());
            probCheck += probabilities[3];
            for (int i = 0; i < SOURCE_LENGTH; i++) {       //Check that no probability is 0
                if (probabilities[i] == 0) {
                    outputText.append("Probability can't be 0!");
                    return;
                }
            }
        } catch(NumberFormatException e) {
            outputText.append("Make sure that all input has been entered correctly!");
            return;
        }
        if (probCheck > 1) {
            outputText.append("Probabilities over 1!");
        } else if (firstSource.getText().length() > 1 || secondSource.getText().length() > 1 || 
                thirdSource.getText().length() > 1 || fourthSource.getText().length() > 1) {
            outputText.append("Enter only one symbol in each box!");
        } else {
            entropy = calculateEntropy(sourceSymbols, probabilities);
            outputText.append("Entropy: " + entropy + "\n");
            codeWord = generateCodeWord(sourceSymbols);
            outputText.append("Source Message: " + codeWord + "\n\n");
            runHuffman(sourceSymbols, probabilities, codeWord);
            runArithmetic(sourceSymbols, probabilities, codeWord);         
        }
    }//GEN-LAST:event_compressBtnActionPerformed
 
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new CompressionUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton compressBtn;
    private javax.swing.JTextField firstProb;
    private javax.swing.JTextField firstSource;
    private javax.swing.JTextField fourthProb;
    private javax.swing.JTextField fourthSource;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea outputText;
    private javax.swing.JTextField secondProb;
    private javax.swing.JTextField secondSource;
    private javax.swing.JTextField thirdProb;
    private javax.swing.JTextField thirdSource;
    // End of variables declaration//GEN-END:variables
}
