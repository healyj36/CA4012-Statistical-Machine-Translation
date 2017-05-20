/*
 * Error in the lab pdf.
 * For input sentence: <s> a cat sat on the mat </s>
 * Output is NOT 0.00097615576843
 * The correct answer is 0.000643539467069 => 6.435394670688788E-4
*/

import java.io.*;
import java.util.*;

public class Question3 {
    public static ArrayList<String> ngrams(int noOfGrams, String[] splitSentence) {
        if(noOfGrams > 0) {
            ArrayList<String> oneGrams = new ArrayList<>(Arrays.asList(splitSentence));
            if(noOfGrams == 1) {
                return oneGrams;
            } else {
                ArrayList<String> ngrams = new ArrayList<>();
                for(int i=0; i<oneGrams.size()-(noOfGrams-1); i++) {
                    String ithGram = oneGrams.get(i);
                    int j = 1;
                    while(j < noOfGrams) {
                        ithGram = ithGram + " " + oneGrams.get(i+j);
                        j++;
                    }
                    ngrams.add(ithGram);
                }
                return ngrams;
            }
        }
        System.out.println("The number of grams must be greater than 1");
        return null;
    }

    public static HashMap<String, Integer> allCountw1w2(ArrayList<String> allBiGramRef) {
        HashMap<String, Integer> biGramDict = new HashMap<>();;
        for(String gram: allBiGramRef) {
            if(!biGramDict.containsKey(gram)) {
                biGramDict.put(gram, 1);
            } else {
                biGramDict.put(gram, biGramDict.get(gram)+1);
            }
        }
        return biGramDict;
    }

    public static HashMap<String, Integer> allCountw1w(ArrayList<String> allBiGramRef) {
        HashMap<String, Integer> precursorDict = new HashMap<>();
        for(String gram: allBiGramRef) {
            String firstRefGram = gram.split(" ")[0];
            if(!precursorDict.containsKey(firstRefGram)) {
                precursorDict.put(firstRefGram, 1);
            } else {
                precursorDict.put(firstRefGram, precursorDict.get(firstRefGram)+1);
            }
        }
        return precursorDict;
    }

    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            Console c = System.console();
            String inputFileName = c.readLine("Enter name of file here: ");

            ArrayList<String> allRefSentences = new ArrayList<>();
            br = new BufferedReader(new FileReader(inputFileName));
            String line = null;
            while((line = br.readLine()) != null) {
                allRefSentences.add(line);
            }

            System.out.println();

            // all bi grams from all the reference sentences
            ArrayList<String> allBiGramRef = new ArrayList<>();
            for(String sentence: allRefSentences) {
                allBiGramRef.addAll(ngrams(2, sentence.split(" ")));
            }

            // all count(w1,w2)
            // count the number of times each bi gram occurs in the reference sentences
            HashMap<String, Integer> biGramDict = allCountw1w2(allBiGramRef);
            System.out.println("all count(w1,w2)");
            System.out.println(biGramDict.toString());

            System.out.println();

            // all count(w1,w)
            // in bi gram, "w1 w2"
            // count the number of times each w1 occurs in the reference sentences
            HashMap<String, Integer> precursorDict = allCountw1w(allBiGramRef);
            System.out.println("all count(w1,w)");
            System.out.println(precursorDict.toString());

            System.out.println();

            String inputSentence = c.readLine("Please calculate the probability of the sentence: ");

            System.out.println();

            System.out.println("--- Bigram Relative Frequency ---");
            ArrayList<String> biGramInput = ngrams(2, inputSentence.split(" "));
            double inputProb = 1.0;
            for(String gram: biGramInput) {
                String firstInputGram = gram.split(" ")[0];
                int countw1w2 = biGramDict.get(gram);
                int countw1w = precursorDict.get(firstInputGram);
                System.out.println("\"" + gram + "\"");
                double biGramRelFreq = (double) countw1w2 / countw1w;
                System.out.println(countw1w2 + " / " + countw1w + " = " + biGramRelFreq);
                inputProb = inputProb * biGramRelFreq;
            }

            System.out.println();

            System.out.println("--- Bigram Probability ---");
            System.out.println(inputProb);
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
