
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Write a description of WordGramTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WordGramTest {
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
    public void testShiftAdd()
    {
        String s = "this is a test";
        String[] words = s.split("\\s+");
        WordGram wg = new WordGram(words, 0, words.length);
        assertEquals("is a test yes", wg.shiftAdd("yes").toString());
    }
}

