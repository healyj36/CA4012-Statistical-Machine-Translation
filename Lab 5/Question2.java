import java.io.*;
import java.util.*;

public class Question2 {

	public static ArrayList<String> unique(ArrayList<String> al) {
		ArrayList<String> unique = new ArrayList<>(al);
		Set<String> temp = new Set<>();
		temp.addAll(unique);
		unique.clear();
		unique.addAll(temp);
		return unique;
	}

	public static ArrayList<String> readFile(String fileName) {
		ArrayList<String> lines = new ArrayList<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line;
			while((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) br.close();
				return lines;
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static HashMap<String, Double> initialization(HashMap<String, Integer> sourceDict, HashMap<String, Integer> sourceTargetDict) {
		int sourceVocabSize = sourceDict.size();
		double initialValue = 1.0 / sourceVocabSize;
		for(String key: sourceTargetDict.keySet()) {
			sourceTargetDict.put(key, initialValue);
		}
		return sourceTargetDict;
	}

	public static void main(String[] args) {
		java.io.Console c = System.console();
		try {
			String sourceFileName = c.readLine("Source file name = ");
			ArrayList<String> sourceLines = readFile(sourceFileName);
			String targetFileName = c.readLine("Target file name = ");
			ArrayList<String> targetLines = readFile(targetFileName);
			//int iterationNum = Integer.parseInt(c.readLine("Iteration Number = "));
			int iterationNum = 2;

			// read data into dictionaries
			HashMap<String, Integer> sourceDict = new HashMap<>();
			HashMap<String, Integer> targetDict = new HashMap<>();
			HashMap<String, Integer> sourceTargetDict = new HashMap<>();
			for(int i=0; i<sourceLines.size(); i++) {
				String[] sourceTokens = sourceLines.get(i).trim().split(" ");
				String[] targetTokens = targetLines.get(i).trim().split(" ");
				for(String sourceToken: sourceTokens) {
					sourceDict.put(sourceToken, 1);
					for(String targetToken: targetTokens) {
						targetDict.put(targetToken, 1);
						sourceTargetDict.put(targetToken + ", " + sourceToken, 0);
					}
				}
			}
			
			HashMap<String, Double> newSourceTargetDict = initialization(sourceDict, sourceTargetDict);
			
			int j=0;
			while(j<iterationNum) {
				HashMap<String, Double> counts = new HashMap<>();
				HashMap<String, Double> total = new HashMap<>();
				
				for(int k=0; i<sourceLines.size(); k++) {
					String[] sourceTokens = sourceLines.get(k).trim().split(" ");
					String[] targetTokens = targetLines.get(k).trim().split(" ");
					HashMap<String, Double> sTotal = new HashMap<>();
					for(String targetToken: targetTokens) {
						for(String sourceToken: sourceTokens) {
							if(sTotal.containsKey(targetToken)) {
								sTotal.put(targetToken, sTotal.get(targetToken) + newSourceTargetDict.get(targetToken + ", " + sourceToken));
							} else {
								sTotal.put(targetToken, newSourceTargetDict.get(targetToken + ", " + sourceToken));
							}
						}
					}
					
					for(String targetToken: targetTokens) {
						for(String sourceToken: sourceTokens) {
							String targetSourcePair = targetToken + ", " + sourceToken;
							if(counts.containsKey(targetSourcePair)) {
								counts.put(targetSourcePair, counts.get(targetSourcePair) + (newSourceTargetDict.get(counts.get(targetSourcePair) / sTotal.get(targetWord))));
							} else {
								counts.put(targetSourcePair, (newSourceTargetDict.get(counts.get(targetSourcePair) / sTotal.get(targetWord))));
							}
							
							if(total.containsKey(sourceToken)) {
								total.put(sourceToken, counts.get(sourceToken) + (newSourceTargetDict.get(counts.get(targetSourcePair) / sTotal.get(targetWord))));
							} else {
								total.put(sourceToken, (newSourceTargetDict.get(counts.get(targetSourcePair) / sTotal.get(targetWord))));
							}
						}
					}
				}
				
				for(String sourceToken: sourceTokens) {
					for(String targetToken: targetTokens) {
						String targetSourcePair = targetToken + ", " + sourceToken;
						newSourceTargetDict.put(targetSourcePair, (counts.get(targetSourcePair) / total.get(sourceToken)))
					}
				}
				
				j++;
			}
			
			for(String key: newSourceTargetDict.keySet()) {
				System.out.println(key + ": " + newSourceTargetDict.get(key));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}