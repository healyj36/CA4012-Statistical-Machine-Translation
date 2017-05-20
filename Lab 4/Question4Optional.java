/*
 * Error in the lab pdf.
 * For input sentence: <s> a cat sat on the mat </s>
 * Output is NOT
 *   1-gram: 2.28175851587e-08
 *   2-gram: 0.000140949604457
 *   3-gram: 0.000263061746438
 *   4-gram: 0.000423106305459 (this is correct)
 * The correct answer is
 *   1-gram: 2.28675954203341E-8
 *   2-gram: 0.00011154210373 => 1.1154210372960373E-4
 *   3-gram: 0.000252100840336 => 2.521008403361345E-4
 *   4-gram: 0.000423106305459 => 4.231063054592466E-4
*/

import java.io.*;
import java.util.*;

public class Question4Optional {
    public static HashMap<String, Integer> allCountw1w(ArrayList<String> allBiGramRef, int n) {
        HashMap<String, Integer> precursorDict = new HashMap<>();
        for(String gram: allBiGramRef) {
            String nMinus1RefGrams = "";
            String[] gramsSplit = gram.split(" ");
            for(int i=0; i<n-1; i++) {
                nMinus1RefGrams = nMinus1RefGrams + gramsSplit[i] + " ";
            }
            nMinus1RefGrams = nMinus1RefGrams.trim();
            if(!precursorDict.containsKey(nMinus1RefGrams)) {
                precursorDict.put(nMinus1RefGrams, 1);
            } else {
                precursorDict.put(nMinus1RefGrams, precursorDict.get(nMinus1RefGrams)+1);
            }
        }
        return precursorDict;
    }

    public static void nGramProb(ArrayList<String> allRefSentences, int n, String inputSentence) {
        // all n grams from all the reference sentences
        ArrayList<String> allNGramRef = new ArrayList<>();
        for(String sentence: allRefSentences) {
            allNGramRef.addAll(Question3.ngrams(n, sentence.split(" ")));
        }

        // all count(w1,w2)
        // count the number of times each n gram occurs in the reference sentences
        HashMap<String, Integer> nGramDict = Question3.allCountw1w2(allNGramRef);

        // all count(w1,w)
        // in n gram, "w1 w2"
        // count the number of times each w1 occurs in the reference sentences
        HashMap<String, Integer> precursorDict = allCountw1w(allNGramRef, n);

        ArrayList<String> nGramInput = Question3.ngrams(n, inputSentence.split(" "));
        int v = Question4.vocabSize(allRefSentences);
        double inputProb = 1.0;
        for(String gram: nGramInput) {
            String nMinus1InputGrams = "";
            String[] gramsSplit = gram.split(" ");
            for(int i=0; i<n-1; i++) {
                nMinus1InputGrams = nMinus1InputGrams + gramsSplit[i] + " ";
            }
            nMinus1InputGrams = nMinus1InputGrams.trim();

            int countw1w2 = -1;
            if(nGramDict.containsKey(gram)) {
                countw1w2 = nGramDict.get(gram) + 1;
            } else {
                countw1w2 = 1;
            }

            int countw1w = -1;
            if(precursorDict.containsKey(nMinus1InputGrams)) {
                countw1w = precursorDict.get(nMinus1InputGrams) + v;
            } else {
                countw1w = v;
            }

            double nGramRelFreq = (double) countw1w2 / countw1w;
            inputProb = inputProb * nGramRelFreq;
        }

        System.out.println(n + "-gram: " + inputProb);
    }

    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            Console c = System.console();
            String inputFileName = c.readLine("Enter name of input file here: ");

            ArrayList<String> allRefSentences = new ArrayList<>();
            br = new BufferedReader(new FileReader(inputFileName));
            String line = null;
            while((line = br.readLine()) != null) {
                allRefSentences.add(line);
            }

            System.out.println();

            String gramNumber = c.readLine("Enter number of grams (a value greater than 0): ");
            int n = Integer.parseInt(gramNumber);
            if(!(n>0)) {
                System.out.println("Gram number must be greater than 0");
                System.exit(0);
            }

            System.out.println();

            String inputSentence = c.readLine("Please calculate the probability of the sentence: ");

            System.out.println();

            System.out.println("--- N-gram Probability ---");
            for(int i=1; i<=n; i++) {
                nGramProb(allRefSentences, i, inputSentence);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null) br.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
