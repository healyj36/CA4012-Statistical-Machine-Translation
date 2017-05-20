/*
 * Error in the lab pdf.
 * For input sentence: <s> a cat sat on the mat </s>
 * Output is NOT 0.000140949604457
 * The correct answer is 0.00011154210373 => 1.1154210372960373E-4
 *
 * For input sentence: <s> a cat sat on the car </s>​
 * Output is NOT ​3.00170453936e-05
 * The correct answer is 2.4787134162134163E-5
*/

import java.io.*;
import java.util.*;

public class Question4 {
    public static int vocabSize(ArrayList<String> allSentences) {
        ArrayList<String> allWords = new ArrayList<>();
        for(String sentence: allSentences) {
            allWords.addAll(Question3.ngrams(1, sentence.split(" ")));
        }
        Set<String> temp = new HashSet<>();
        temp.addAll(allWords);
        allWords.clear();
        allWords.addAll(temp);
        return allWords.size();
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
                allBiGramRef.addAll(Question3.ngrams(2, sentence.split(" ")));
            }

            // all count(w1,w2)
            // count the number of times each bi gram occurs in the reference sentences
            HashMap<String, Integer> biGramDict = Question3.allCountw1w2(allBiGramRef);
            System.out.println("all count(w1,w2)");
            System.out.println(biGramDict.toString());

            System.out.println();

            // all count(w1,w)
            // in bi gram, "w1 w2"
            // count the number of times each w1 occurs in the reference sentences
            HashMap<String, Integer> precursorDict = Question3.allCountw1w(allBiGramRef);
            System.out.println("all count(w1,w)");
            System.out.println(precursorDict.toString());

            System.out.println();

            String inputSentence = c.readLine("Please calculate the probability of the sentence: ");

            System.out.println();

            System.out.println("--- Bigram Relative Frequency ---");
            ArrayList<String> biGramInput = Question3.ngrams(2, inputSentence.split(" "));
            int v = vocabSize(allRefSentences);
            double inputProb = 1.0;
            for(String gram: biGramInput) {
                String firstInputGram = gram.split(" ")[0];
                int countw1w2 = -1;
                if(biGramDict.containsKey(gram)) {
                    countw1w2 = biGramDict.get(gram) + 1;
                } else {
                    countw1w2 = 1;
                }

                int countw1w = -1;
                if(precursorDict.containsKey(firstInputGram)) {
                    countw1w = precursorDict.get(firstInputGram) + v;
                } else {
                    countw1w = v;
                }

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
