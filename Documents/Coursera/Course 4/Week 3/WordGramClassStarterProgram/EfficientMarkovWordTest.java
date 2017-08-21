
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.duke.*;
import java.util.*;


/**
 * Write a description of EfficientMarkovWordTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EfficientMarkovWordTest {
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() {
    }

    @Test
    public void testBuildMap()
    {
        //FileResource fr = new FileResource();
        //String s = fr.asString();
        String s = "this is a test yes this is a test this is a test yeah this is a";
        String[] words = s.split("\\s+");
        
        EfficientMarkovWord markov = new EfficientMarkovWord(3);
        markov.setTraining(s);
        markov.buildMap();
        
        WordGram wg = new WordGram(words, 1, 3);
        System.out.println("Getting follows array for key=" + wg);
        ArrayList<String>follows = markov.getFollows(wg);
        
        System.out.println("key=" + wg + ", follows=" + follows);
    }
    
    /*@Test
    public void testHashMap()
    {
        //FileResource fr = new FileResource();
        //String s = fr.asString();
        String s = "this is a test yes this is really a test";
        String[] words = s.split("\\s+");
        
        EfficientMarkovWord markov = new EfficientMarkovWord(2);
        markov.setTraining(s);
        markov.setRandom(42);
        markov.buildMap();
        
        WordGram wg = new WordGram(words, 1, 2);
        System.out.println("Getting follows array for key=" + wg);
        ArrayList<String>follows = markov.getFollows(wg);
        
        System.out.println("key=" + wg + ", follows=" + follows);
    }*/
}

