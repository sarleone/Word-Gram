import java.util.ArrayList;
import java.util.Random;

/**
 * Write a description of MarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    
    public MarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text){
        myText = text.split("\\s+");
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

    protected ArrayList<String> getFollows(WordGram key) {
        ArrayList<String> follows = new ArrayList<>();
        int pos = 0;
        while(pos < myText.length) {
            //System.out.println("MarkovWord.getFollows:pos=" + pos);
            int start = indexOf(myText, key, pos);
            //System.out.println("MarkovWord.getFollows:start=" + start);
            //System.out.println("MarkovWord.getFollows:myText.length=" + myText.length);
            if (start == -1) {
                break;
            }
            if (start+1 >= myText.length) {
                break;
            }
            String next = myText[start+key.length()];
            follows.add(next);
            pos = start+1;
            //System.out.println("MarkovWord.getFollows:pos=" + pos);
        }

        //System.out.println("MarkovWord.getFollows: key=" + key + ", follows=" + follows);
        return follows;
    }

}
