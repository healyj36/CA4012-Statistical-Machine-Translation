import java.io.*;
import java.util.*;

public class Question4 {
    public static ArrayList<String> ngrams(int n, String[] splitSentence) {
        if(n > 0) {
            ArrayList<String> oneGrams = new ArrayList<>(Arrays.asList(splitSentence));
            if(n == 1) {
                return oneGrams;
            } else {
                ArrayList<String> nGrams = new ArrayList<>();
                for(int i=0; i<oneGrams.size()-(n-1); i++) {
                    String ithGram = oneGrams.get(i);
                    for(int j=1; j<n; j++) {
                        ithGram = ithGram + " " + oneGrams.get(i+j);
                    }
                    nGrams.add(ithGram);
                }
                return nGrams;
            }
        } else {
            System.out.println("n must be greater than zero");
            return null;
        }
    }

    public static ArrayList<String> readFile(String fileName) {
        BufferedReader br = null;
        ArrayList<String> allLines = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;

            while((line = br.readLine()) != null) {
                allLines.add(line);
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
        return allLines;
    }

    public static ArrayList<String> unique(ArrayList<String> al) {
        ArrayList<String> unique = new ArrayList<>(al);
        Set<String> temp = new HashSet<>();
        temp.addAll(unique);
        unique.clear();
        unique.addAll(temp);
        return unique;
    }

    public static void main(String[] args) {
        try {
            java.io.Console c = System.console();
            String inputSentence = c.readLine("Input sentence: ");
            String fileName = c.readLine("Reference file name: ");
            
            ArrayList<String> allRefWords = new ArrayList<>();
            ArrayList<String> allRefSentences = readFile(fileName);
            ArrayList<String> allRefBiGrams = new ArrayList<>();
            for(String sentence: allRefSentences) {
                allRefWords.addAll(ngrams(1, sentence.split(" ")));
                allRefBiGrams.addAll(ngrams(2, sentence.split(" ")));
            }
            
            HashMap<String, Integer> biGramDict = new HashMap<>();
            HashMap<String, Integer> precursorDict = new HashMap<>();
            for(String gram: allRefBiGrams) {
                biGramDict.put(gram, Collections.frequency(allRefBiGrams, gram));

                String firstGram = gram.split(" ")[0];
                if(precursorDict.containsKey(firstGram)) {
                    precursorDict.put(firstGram, precursorDict.get(firstGram)+1);
                } else {
                    precursorDict.put(firstGram, 1);
                }
            }

            int v = unique(allRefWords).size();
            double totalProb = 1;
            ArrayList<String> allInputBiGrams = ngrams(2, inputSentence.split(" "));
            for(String gram: allInputBiGrams) {
                int countw1w2 = -1;
                if(biGramDict.containsKey(gram)) {
                    countw1w2 = biGramDict.get(gram) + 1;
                } else {
                    countw1w2 = 1;
                }

                String firstGram = gram.split(" ")[0];
                int countw1w = -1;
                if(precursorDict.containsKey(firstGram)) {
                    countw1w = precursorDict.get(firstGram) + v;
                } else {
                    countw1w = v;
                }

                double relFreq = (double) countw1w2 / countw1w;
                totalProb = totalProb * relFreq;
            }

            System.out.println(totalProb);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
