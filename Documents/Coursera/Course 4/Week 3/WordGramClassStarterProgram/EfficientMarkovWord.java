import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Write a description of MarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EfficientMarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram, ArrayList<String>>keyMap;
    
    public EfficientMarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
        keyMap = new HashMap<>();
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text){
        myText = text.split("\\s+");
        buildMap();
    }

    public int indexOf(String[] words, WordGram target, int start) {
        //System.out.println("MarkovWord.indexOf: start=" + start);
        for (int k=start; k <= words.length-target.length(); k++) {
            WordGram wg = new WordGram(words, k, target.length());
            //System.out.println("MarkovWord.indexOf: wg=" + wg);
            //System.out.println("MarkovWord.indexOf: target=" + target);
            if (wg.equals(target)) {
                //System.out.println("returning k=" + k);
                return k;
            }
        }
        //System.out.println("returning -1");
        return -1;
    }

    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
        WordGram key = new WordGram(myText, index, myOrder);
        sb.append(key);
        sb.append(" ");
        for(int k=0; k < numWords-1; k++){
            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = key.shiftAdd(next);
            //System.out.println("getRandomText:k=" + k + ", key=" + key);
        }

        return sb.toString().trim();
    }

    public void buildMap() {
        //System.out.println("Entering buildMap with myText.length" + myText.length);
        if (myText == null){
            return;
        }
        keyMap.clear();
        WordGram key = null;
        for (int k=0; k <= myText.length-myOrder; k++) {
            //key = myText.substring(k, k+myOrder);
            key = new WordGram(myText, k, myOrder);
            //System.out.println("buildMap: checking key=" + key);
            if(!keyMap.containsKey(key)) {
                //System.out.println("buildMap: calling findFollows with key=" + key);
                keyMap.put(key, findFollows(key));
            }
            //System.out.println(key);
        }
        
        /*if (keyMap.isEmpty()) {
            System.out.println("EMPTY KEYMAP!!!!");
        }
        
        for (WordGram wg : keyMap.keySet()) {
            System.out.println("-----------------------------------------------------");
            System.out.println("key=" + wg);
            System.out.println("follows=" + keyMap.get(wg));
        }*/
    }
    
    public void printHashMapInfo() {
        if(keyMap.size() < 60) {
            for (WordGram key: keyMap.keySet()){
                //System.out.println("Key = " + key + " Values = "+ keyMap.get(key));
            }
        }
        System.out.println("Number of keys " + keyMap.size());

        int maxSize = -1;
        for(ArrayList<String> follows : keyMap.values()){
            if(follows.size()>maxSize){
                maxSize=follows.size();
            }
        }
        System.out.println("Largest value in HashMap: "+ maxSize);

        System.out.print("Keys with largest value: ");
        for (WordGram key: keyMap.keySet()){
            if(keyMap.get(key).size() == maxSize){
                System.out.println(" "+key);
            }
        }
        System.out.println();
    }


    protected ArrayList<String> getFollows(WordGram key) {
        return keyMap.get(key);
    }

    protected ArrayList<String> findFollows(WordGram key) {
        //System.out.println("Entering MarkovWord.findFollows: key=" + key);
        ArrayList<String> follows = new ArrayList<>();
        int pos = 0;
        while(pos < myText.length) {
            //System.out.println("MarkovWord.findFollows:pos=" + pos);
            int start = indexOf(myText, key, pos);
            //System.out.println("MarkovWord.findFollows:start=" + start);
            //System.out.println("MarkovWord.findFollows:myText.length=" + myText.length);
            if (start == -1) {
                break;
            }
            if (start+key.length() >= myText.length) {
                break;
            }
            String next = myText[start+key.length()];
            follows.add(next);
            pos = start+1;
            //System.out.println("MarkovWord.findFollows:pos=" + pos);
        }

        //System.out.println("MarkovWord.findFollows: key=" + key + ", follows=" + follows);
        return follows;
    }

}
