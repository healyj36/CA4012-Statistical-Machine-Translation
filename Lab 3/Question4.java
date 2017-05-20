import java.io.*;
import java.io.BufferedWriter;
import java.text.DecimalFormat;
import java.util.*;
import java.util.ArrayList;

public class Question4 {
    private static final String BASE_DIR = "/home/jordan/ca4012/lab-3/";
    private static final String OUTPUT_FILEPATH = BASE_DIR + "output.txt";
    private static final String REFERENCE_FILEPATH = BASE_DIR + "reference.txt";
    private static final String ANSWER_FILEPATH = BASE_DIR + "answer.txt";

    public static int calcMatch(ArrayList<String> outputGrams, ArrayList<String> referenceGram) {
        int count = 0;
        for(int i = 0; i < outputGrams.size(); i++) {
            if(referenceGram.contains(outputGrams.get(i))) {
                referenceGram.remove(outputGrams.get(i));
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {

        BufferedReader outBr = null;
        BufferedReader refBr = null;
        BufferedWriter ansBw = null;
        try {
            outBr = new BufferedReader(new FileReader(OUTPUT_FILEPATH));
            refBr = new BufferedReader(new FileReader(REFERENCE_FILEPATH));
            ansBw = new BufferedWriter(new FileWriter(ANSWER_FILEPATH));

            ArrayList<String> allOutputs = new ArrayList<>();
            String outLine;
			while ((outLine = outBr.readLine()) != null) {
                allOutputs.add(outLine);
			}
            ArrayList<String> allReferences = new ArrayList<>();
            String refLine;
			while ((refLine = refBr.readLine()) != null) {
                allReferences.add(refLine);
			}

            System.out.println("Output sentence = " + allOutputs.get(0));
            for(int k = 1; k <= allReferences.size(); k++) {
                System.out.println("Reference sentence " + k + " = " + allReferences.get(k-1));
            }

            System.out.println();
            System.out.println("----- PRECISION -----");

            ArrayList<Double> allPrecisions = new ArrayList<>();
            for(int i = 1; i <= 4; i++) {
                ArrayList<String> outputiGrams = Question1.ngrams(i, allOutputs.get(0).split(" "));
                System.out.println("output " + i + "-grams " + outputiGrams.toString());
                ArrayList<Integer> correctListi = new ArrayList<>();
                for(int l = 0; l < allReferences.size(); l++) {
                    String reference = allReferences.get(l);
                    ArrayList<String> referenceGram = Question1.ngrams(i, reference.split(" "));
                    System.out.println("reference " + (l+1) + "-grams = " + referenceGram.toString());
                    correctListi.add(calcMatch(outputiGrams, referenceGram));
                }
                double precisioni = (double) Collections.max(correctListi) / outputiGrams.size();
                System.out.println("Precision " + i + " = " + Collections.max(correctListi) + " / " + outputiGrams.size() + " = " + precisioni);
                allPrecisions.add(precisioni);
                System.out.println();
            }

            double totalPrecision = 1;
            System.out.print("Total Precision = ");
            for(int j = 0; j < allPrecisions.size(); j++) {
                double p = allPrecisions.get(j);
                totalPrecision = totalPrecision * p;
                if(j==3) {
                    System.out.print(p);
                } else {
                    System.out.print(p + " * ");
                }
            }
            System.out.print(" = " + totalPrecision);

            System.out.println();
            System.out.println();
            System.out.println("----- BREVITY PENALTY -----");

            ArrayList<Integer> allReferenceLengths = new ArrayList<>();
            for (String reference: allReferences) {
                allReferenceLengths.add(reference.split(" ").length);
            }

            // select the length of one reference, whose length is most close to the output length
            ArrayList<Integer> allReferenceDiffs = new ArrayList<>();
            int outputLength = allOutputs.get(0).split(" ").length;
            for(int referenceLength: allReferenceLengths) {
                int diff = Math.abs(outputLength - referenceLength);
                allReferenceDiffs.add(diff);
            }
            int referenceLength = allReferenceLengths.get(allReferenceDiffs.indexOf(Collections.min(allReferenceDiffs)));

            double brevity = (double)allOutputs.get(0).split(" ").length / referenceLength;
            if(brevity > 1) {
                brevity = 1;
            }
            System.out.println("Brevity Penalty = " + allOutputs.get(0).split(" ").length + " / " + referenceLength + " = " + brevity);

            System.out.println();
            System.out.println("----- BLEU SCORE -----");
            System.out.println("BLEU Score = Brevity Penalty * N-gram overlap");
            System.out.println("N-gram overlap = (" + totalPrecision + ")^0.25" );
            double bleu = brevity * Math.pow(totalPrecision, 0.25);

            DecimalFormat bleuFormat = new DecimalFormat("00.00");
            DecimalFormat percentFormat = new DecimalFormat("000.0");
            DecimalFormat ratioFormat = new DecimalFormat("0.000");
            System.out.println("BLEU Score = " + bleu + " (" + Double.valueOf(bleuFormat.format(bleu*100)) + "%)");

            System.out.println();
            String ans = "BLEU = " + Double.valueOf(bleuFormat.format(bleu*100)) + ", "
                               + Double.valueOf(percentFormat.format(allPrecisions.get(0)*100)) + "/"
                               + Double.valueOf(percentFormat.format(allPrecisions.get(1)*100)) + "/"
                               + Double.valueOf(percentFormat.format(allPrecisions.get(2)*100)) + "/"
                               + Double.valueOf(percentFormat.format(allPrecisions.get(3)*100))
                               + " (BP=" + "????"
                               + ", ratio=" + Double.valueOf(ratioFormat.format(brevity))
                               + ", hyp_len=" + allOutputs.get(0).split(" ").length
                               + ", ref_len=" + "?" + ")";
            System.out.println(ans);
            ansBw.write(ans);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
			try {
				if (outBr != null) outBr.close();
				if (refBr != null) refBr.close();
                if (ansBw != null) ansBw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    }
}
