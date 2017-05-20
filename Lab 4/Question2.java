import java.io.Console;
import java.util.*;

public class Question2 {
    public static double unigramLangModel(ArrayList<String> splitSentence) {
        ArrayList<String> splitSentenceNoDup = new ArrayList<>(splitSentence);
        Set<String> temp = new HashSet<>();
        temp.addAll(splitSentenceNoDup);
        splitSentenceNoDup.clear();
        splitSentenceNoDup.addAll(temp);

        double result = 1;
        for(int i=0; i<splitSentenceNoDup.size(); i++) {
            result = result * Question1.wordFreq(splitSentenceNoDup.get(i), splitSentence);
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            Console c = System.console();
            String input = c.readLine("Enter sentence here (seperate by spaces): ");
            ArrayList<String> splitSentence = new ArrayList<>(Arrays.asList(input.split(" ")));

            System.out.println(unigramLangModel(splitSentence));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
