
public class WordGram {
    private String[] myWords;
    private int myHash;

    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }

    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt "+index);
        }
        return myWords[index];
    }
    
    public void setWordAt(int index, String word) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt "+index);
        }
        myWords[index] = word;
    }

    public int length(){
        return myWords.length;
    }

    public String toString(){
        String ret = "";
        for (String word : myWords) {
            ret += word + " ";
        }

        return ret.trim();
    }

    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        if(other.length() != length()) {
            return false;
        }
        
        for (int k=0; k < length(); k++) {
            if (!other.wordAt(k).equals(wordAt(k))) {
                return false;
            }
        }
        
        return true;

    }
    
    public int hashCode() {
        return toString().hashCode();
    }

    public WordGram shiftAdd(String word) {	
        WordGram out = new WordGram(myWords, 0, myWords.length);
        // shift all words one towards 0 and add word at the end. 
        // you lose the first word
        for(int k=1; k < out.length(); k++) {
            out.setWordAt(k-1,out.wordAt(k));
        }
        
        out.setWordAt(out.length()-1,word);
        return out;
    }

}