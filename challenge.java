import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.*;

class Solution {


    public static void analyze(List<String> lines) {
        
        int totalWords = 0;
        List<String[]> sentences = new ArrayList<>();
        Map<String, Integer> wordStore = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> topWords = new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());

        for(int i = 0; i < lines.size(); i++) {
            sentences.add(lines.get(i).split("\\."));
            String[] curr = lines.get(i).split("\\.|\"|:|,|\\?| ");
            //this counts the total words also stores the word counts in hashmap
            for(int j = 0; j < curr.length; j++) {
                if(curr[j] != "") {
                    totalWords += 1;
                    wordStore.put(curr[j], wordStore.getOrDefault(curr[j], 0)+1);
                }
            }
        }
        //give a total word count
        System.out.println("**************");
        System.out.println("total Words: " + totalWords);
        System.out.println("**************");
        String maxUsedWord = "";
        int count = 0;
        //identify the top 10 words and display them in the sorted order

        //dump all the words into a priority queue and make sure the size doesn't cross 10(since we only need top 10 words) -- making the space O(1) for priority queue
        for(Map.Entry<String, Integer> entry: wordStore.entrySet()) {
            maxUsedWord = (int) entry.getValue() > count ? (String) entry.getKey():maxUsedWord;
            count = (int) entry.getValue() > count ? (int) entry.getValue() : count;
            topWords.offer(entry);
            if(topWords.size() > 10) topWords.poll();
        }
        count = 1;
        System.out.println("Top Words (in ascending order)");
        while(!topWords.isEmpty()) {
            System.out.println("word " + count + ": " + topWords.poll().getKey());
            count += 1;
        }
        System.out.println("**************");

        
        //Find and display the last sentence on the file that contains the most used word
        String lastSentence = "";
        //check each sentence for the max used word and store it in lastSentence. 
        for(int i = 0; i < sentences.size(); i++) {
            String[] currSentence = sentences.get(i);
            for(String s: currSentence) {
                if(s.contains(maxUsedWord)){
                    lastSentence = s;
                }
            }
            
        }
        System.out.println("last sentence with max repeated word: " + lastSentence);
        System.out.println("**************");
        
    }
    public static void main(String[] args) {
        try {
            //while running please pass the file name as the first argument! If that doesn't work for any reason, you can comment out the below line and pass in the file name manually!
            // File currFile = new File("passage.txt");
            File currFile = new File(args[0]);
            Scanner fileReader = new Scanner(currFile);
            List<String> lines = new ArrayList<>();
            while(fileReader.hasNextLine()) {
                String currLine = fileReader.nextLine();                
                lines.add(currLine);
            }
            analyze(lines);
            fileReader.close();
        }
        catch (FileNotFoundException exception) {
            System.out.print("There was an error parsing the file");
            exception.printStackTrace();
        }
    }
}