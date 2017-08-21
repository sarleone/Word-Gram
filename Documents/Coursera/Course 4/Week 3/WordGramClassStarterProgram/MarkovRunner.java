
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class MarkovRunner {
    public void runModel(IMarkovModel markov, String text, int size){ 
        markov.setTraining(text); 
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runModel(IMarkovModel markov, String text, int size, int seed){ 
        markov.setTraining(text); 
        markov.setRandom(seed);
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runMarkov() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        EfficientMarkovWord markovWord = new EfficientMarkovWord(2); 
        runModel(markovWord, st, 500, 65); 
        markovWord.printHashMapInfo();
    } 
    
    public void testHashMap() { 
        String st = "this is a test yes this is really a test";
        st = st.replace('\n', ' '); 
        EfficientMarkovWord markovWord = new EfficientMarkovWord(2);
        runModel(markovWord, st, 50, 42); 
        //markovWord.printHashMapInfo();
    } 

    public void testHashMap2() { 
        String st = "this is a test yes this is really a test yes a test this is wow";
        st = st.replace('\n', ' '); 
        EfficientMarkovWord markovWord = new EfficientMarkovWord(2);
        runModel(markovWord, st, 50, 42); 
        //markovWord.printHashMapInfo();
    } 
    
    public void compareMethods(){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 100;
        int seed = 42;
        
        long startTime = System.nanoTime();
        MarkovWord markov = new MarkovWord(2);
        runModel(markov,st, size, seed);
        long endTime= System.nanoTime();
        System.out.println("Run time: "+ (endTime-startTime) /1000000000L);
        
        long startTimeE = System.nanoTime();
        EfficientMarkovWord efficient = new EfficientMarkovWord(2);
        runModel(efficient, st, size, seed);
        long endTimeE= System.nanoTime();
        System.out.println("Run time: " + (endTimeE-startTimeE) /1000000000L);
        
    }
    
    private void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println(); 
                psize = 0;
            } 
        } 
        System.out.println("\n----------------------------------");
    } 

}
