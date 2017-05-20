import java.io.Console;
import java.util.*;

public class Question1 {
    public static double wordFreq(String word, ArrayList<String> splitSentence) {
        double occurences = (double) Collections.frequency(splitSentence, word);
        return occurences / splitSentence.size();
    }

    public static void main(String[] args) {
        try {
            Console c = System.console();
            String input = c.readLine("Enter sentence here (seperate by spaces): ");
            ArrayList<String> splitSentence = new ArrayList<>(Arrays.asList(input.split(" ")));

            ArrayList<String> splitSentenceNoDup = new ArrayList<>(Arrays.asList(input.split(" ")));
            Set<String> temp = new HashSet<>();
            temp.addAll(splitSentenceNoDup);
            splitSentenceNoDup.clear();
            splitSentenceNoDup.addAll(temp);

            for(int i=0; i<splitSentenceNoDup.size(); i++) {
                System.out.println("The word " + splitSentenceNoDup.get(i) + " frequency is: " + wordFreq(splitSentenceNoDup.get(i), splitSentence));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
