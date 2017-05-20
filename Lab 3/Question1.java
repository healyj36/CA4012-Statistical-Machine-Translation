import java.io.Console;
import java.util.*;

public class Question1 {
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
    
    
    public static void main(String[] args) {
        try {
            Console c = System.console();
            String input = c.readLine("Enter sentence here (seperate by spaces): ");
            String[] splitSentence = input.split(" ");

            System.out.println("1-grams");
            System.out.println(ngrams(1, splitSentence).toString());

            System.out.println();
            
            System.out.println("2-grams");
            System.out.println(ngrams(2, splitSentence).toString());
            
            System.out.println();

            System.out.println("3-grams");
            System.out.println(ngrams(3, splitSentence).toString());
            
            System.out.println();

            System.out.println("4-grams");
            System.out.println(ngrams(4, splitSentence).toString());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
