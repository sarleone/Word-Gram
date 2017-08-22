import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder slice = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            slice.append(message.charAt(i));
        }

        return slice.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);
        for (int i = 0; i < key.length; i++) {
            key[i] = cc.getKey(sliceString(encrypted, i, klength));
        }

        return key;
    }

    public void breakVigenere () {
        HashMap<String, HashSet<String>> languages = new HashMap<>();
        FileResource fr = new FileResource();
        String message = fr.asString();
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()) { 
            FileResource dictionaryFr = new FileResource(f);
            HashSet<String> dictionary = readDictionary(dictionaryFr);
            languages.put(f.getName(), dictionary);
        }
        breakForAllLanguages(message, languages);
        
        /*String decrypted = breakForLanguage(message, dictionary);
        System.out.println(decrypted.substring(0,160));

        /*int [] key = tryKeyLength(message, 4, 'e');

        VigenereCipher vc = new VigenereCipher(key);
        System.out.println(vc.decrypt(message));*/
    }

    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> dictionary = new HashSet<>();
        for (String word : fr.words()){
            dictionary.add(word.toLowerCase());
        }
        return dictionary;
    }

    public int countWords (String message, HashSet<String> dictionary) {
        int count = 0;
        for (String word : message.split("\\W")) {
            if (dictionary.contains(word.toLowerCase())){
                count++;
            }
        }
        return count;
    }

    public String breakForLanguage (String encrypted, HashSet<String> dictionary){
        int [] bestKey = new int[1];
        int bestWordCount = 0;
        char commonChar = mostCommonCharIn(dictionary);
        for (int i = 1; i<=100 ; i++) {
            int [] key = tryKeyLength(encrypted, i, commonChar);
            VigenereCipher vc = new VigenereCipher(key);
            int wordCount = countWords(vc.decrypt(encrypted), dictionary);
            if (wordCount > bestWordCount){
                bestWordCount = wordCount;
                bestKey = key;
            }
        }

        System.out.println("breakForLanguage: best key is ");
        for (int i = 0; i < bestKey.length; i++) {
            System.out.print(bestKey[i] + " ");
        }
        System.out.println("with key length" + bestKey.length);
        System.out.println("with " + bestWordCount + " words");
        VigenereCipher vc = new VigenereCipher(bestKey);
        return vc.decrypt(encrypted);
    }

    public void wordCountForKeyLength (String encrypted, int keyLength, HashSet<String> dictionary){
        int [] key = tryKeyLength(encrypted, keyLength, 'e');
        VigenereCipher vc = new VigenereCipher(key);
        int wordCount = countWords(vc.decrypt(encrypted), dictionary);

        System.out.println("breakForLanguageByKeyLength: word count for key length " + keyLength);

        System.out.println(" is " + wordCount);
    }

    public void wordCountQuestion(){
        FileResource fr = new FileResource();
        String message = fr.asString();
        FileResource dictionaryFr = new FileResource("dictionaries/English");
        HashSet<String> dictionary = readDictionary(dictionaryFr);
        wordCountForKeyLength(message, 38, dictionary);
    }

    public char mostCommonCharIn(HashSet<String> dictionary){
        HashMap<Character, Integer> alphaMap = new HashMap<>();
        for (String s : dictionary) {
            for (char c : s.toLowerCase().toCharArray()){
                if(!alphaMap.containsKey(c)){
                    alphaMap.put(c,1);
                } else {
                    alphaMap.put(c, alphaMap.get(c)+1);
                }
            }
        }
        int maxCount = 0;
        char mostFrequentChar = ' ';
        for (Character c : alphaMap.keySet()) {
            if (alphaMap.get(c) > maxCount){
                maxCount = alphaMap.get(c);
                mostFrequentChar = c;
            }
        }
        return mostFrequentChar;
    }

    public void breakForAllLanguages(String encrypted, HashMap<String, HashSet<String>> languages) {
        String bestLanguage = null;
        String bestDecryption = "";
        int bestWordCount = 0;
        for (String language : languages.keySet()){
            
            System.out.println("Trying: " + language);
            String decrypted = breakForLanguage(encrypted, languages.get(language));
            int wordCount = countWords(decrypted, languages.get(language));
            if (wordCount > bestWordCount){
                bestWordCount = wordCount;
                bestLanguage = language;
                bestDecryption = decrypted;
            }
        }
        System.out.println("Best Language is " + bestLanguage);
        System.out.println("Best Decryption is " + bestDecryption.substring(0,160));
    }
}

