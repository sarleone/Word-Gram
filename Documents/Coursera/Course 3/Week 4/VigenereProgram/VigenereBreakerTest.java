
import static org.junit.Assert.*;
import edu.duke.FileResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Write a description of VigenereBreakerTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VigenereBreakerTest {
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
    public void testSliceString()
    {
        VigenereBreaker vb = new VigenereBreaker();
        assertEquals("adgjm", vb.sliceString("abcdefghijklm", 0, 3));
        assertEquals("behk", vb.sliceString("abcdefghijklm", 1, 3));
        assertEquals("cfil", vb.sliceString("abcdefghijklm", 2, 3));
        assertEquals("aeim", vb.sliceString("abcdefghijklm", 0, 4));
    }

    @Test
    public void testTryKeyLength()
    {
        VigenereBreaker vb = new VigenereBreaker();
        FileResource fr = new FileResource("../VigenereTestData/athens_keyflute.txt");
        int [] expected = {5, 11, 20, 19, 4};
        int [] got = vb.tryKeyLength(fr.asString(),5, 'e');
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], got[i]);
        }
    }

    @Test
    public void question1() {
        VigenereBreaker vb = new VigenereBreaker();
        FileResource fr = new FileResource("messages/secretmessage1.txt");
        int [] key = vb.tryKeyLength(fr.asString(),4, 'e');
        for (int i = 0; i < key.length; i++) {
            System.out.println(key[i]);
        }
    }

    @Test
    public void question2() {
        VigenereBreaker vb = new VigenereBreaker();
        FileResource fr = new FileResource("messages/secretmessage1.txt");
        String message = fr.asString();
        int [] key = vb.tryKeyLength(message, 4, 'e');
        
        VigenereCipher vc = new VigenereCipher(key);
        System.out.println(vc.decrypt(message.substring(0,160)));
    }
    
    
}

