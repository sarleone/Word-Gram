
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Write a description of MarkovWordTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MarkovWordTest {
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
    public void testIndexOf()
    {
        MarkovWord markov = new MarkovWord(3);
        String s = "this is a test yes this is a test this is a test yeah this is a";
        String[] words = s.split("\\s+");
        WordGram wg = new WordGram(words, 0, 3);
        //System.out.println("wg=" + wg);
        assertEquals(0, markov.indexOf(words, wg, 0));
        assertEquals(5, markov.indexOf(words, wg, 2));
        assertEquals(9, markov.indexOf(words, wg, 7));
        assertEquals(14, markov.indexOf(words, wg, 11));
        s = "this is a test yes this is a test this is a test yeah this is a test";
        words = s.split("\\s+");
        assertEquals(5, markov.indexOf(words, wg, 2));
        assertEquals(14, markov.indexOf(words, wg, 11));
        s = "this is a test yes this is a test this is a test yeah this";
        words = s.split("\\s+");
        assertEquals(5, markov.indexOf(words, wg, 2));
        assertEquals(-1, markov.indexOf(words, wg, 11));
        
    }
}

