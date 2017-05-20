import java.io.Console;
import java.util.*;
import java.lang.*;
import java.text.DecimalFormat;

public class Question2 {
    public static int count(ArrayList<String> tgrams, ArrayList<String> rgrams) {
        System.out.println("output 1-grams = " + tgrams.toString());
        System.out.println("reference 1-grams = " + rgrams.toString());
        int count = 0;
        for(int a=0; a<tgrams.size(); a++) {
            if(rgrams.contains(tgrams.get(a))) {
                rgrams.remove(tgrams.get(a));
                count++;
            }
        }
        return count;
    }
    
    public static double precision(int count, ArrayList<String> tgrams) {
        return (double)count / tgrams.size();
    }
    
    public static void main(String[] args) {
        String translationOutput = "The gunman was shot dead by police .";
        String[] translationOutputSplit = translationOutput.split(" ");
        System.out.println("Output sentence = " + translationOutput);
        ArrayList<String> oneGramsOutput = new ArrayList<>(Arrays.asList(translationOutputSplit));
        
        String reference = "The gunman was shot dead by the police .";
        String[] referenceSplit = reference.split(" ");
        System.out.println("Reference sentence = " + reference);
        ArrayList<String> oneGramsReference = new ArrayList<>(Arrays.asList(referenceSplit));
        
        System.out.println();
        System.out.println("----- PRECISION -----");

        ArrayList<String> t1grams = new ArrayList<>(oneGramsOutput);
        ArrayList<String> r1grams = new ArrayList<>(oneGramsReference);
        int count1 = count(t1grams, r1grams);
        double precision1 = precision(count1, t1grams);
        System.out.println("Precision 1 = " + count1 + " / " + t1grams.size() + " = " + precision1);

        System.out.println();

        ArrayList<String> t2grams = Question1.ngrams(2, translationOutputSplit);
        ArrayList<String> r2grams = Question1.ngrams(2, referenceSplit);
        int count2 = count(t2grams, r2grams);
        double precision2 = precision(count2, t2grams);
        System.out.println("Precision 2 = " + count2 + " / " + t2grams.size() + " = " + precision2);

        System.out.println();

        ArrayList<String> t3grams = Question1.ngrams(3, translationOutputSplit);
        ArrayList<String> r3grams = Question1.ngrams(3, referenceSplit);
        int count3 = count(t3grams, r3grams);
        double precision3 = precision(count3, t3grams);
        System.out.println("Precision 3 = " + count3 + " / " + t3grams.size() + " = " + precision3);

        System.out.println();

        ArrayList<String> t4grams = Question1.ngrams(4, translationOutputSplit);
        ArrayList<String> r4grams = Question1.ngrams(4, referenceSplit);
        int count4 = count(t4grams, r4grams);
        double precision4 = precision(count4, t4grams);
        System.out.println("Precision 4 = " + count4 + " / " + t4grams.size() + " = " + precision4);

        System.out.println();
        System.out.println("----- BREVITY PENALTY -----");

        double brevity = (double)oneGramsOutput.size() / oneGramsReference.size();
        if(brevity > 1) {
            brevity = 1;
        }
        System.out.println("Brevity Penalty = " + oneGramsOutput.size() + " / " + oneGramsReference.size() + " = " + brevity);

        System.out.println();
        System.out.println("----- BLEU SCORE -----");
        System.out.println("BLEU Score = Brevity Penalty * N-gram overlap");
        System.out.println("N-gram overlap = (" + precision1 + " * " + precision2 + " * " + precision3 + " * " + precision4 + ")^0.25" );
        double bleu = brevity * Math.pow((precision1 * precision2 * precision3 * precision4), 0.25);
        
        DecimalFormat bleuFormat = new DecimalFormat("00.00");
        DecimalFormat percentFormat = new DecimalFormat("000.0");
        DecimalFormat ratioFormat = new DecimalFormat("0.000");
        System.out.println("BLEU Score = " + bleu + " (" + Double.valueOf(bleuFormat.format(bleu*100)) + "%)");
        // BLEU = 67.53, 100.0/85.7/66.7/60.0 (BP=0.882, ratio=0.889, hyp_len=8, ref_len=9)
        System.out.println();
        System.out.println("BLEU = " + Double.valueOf(bleuFormat.format(bleu*100)) + ", "
                           + Double.valueOf(percentFormat.format(precision1*100)) + "/"
                           + Double.valueOf(percentFormat.format(precision2*100)) + "/"
                           + Double.valueOf(percentFormat.format(precision3*100)) + "/"
                           + Double.valueOf(percentFormat.format(precision4*100))
                           + " (BP=" + "????"
                           + ", ratio=" + Double.valueOf(ratioFormat.format(brevity))
                           + ", hyp_len=" + oneGramsOutput.size()
                           + ", ref_len=" + oneGramsReference.size() + ")");
    }
}
