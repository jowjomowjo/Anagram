import java.util.*;

/**
 * Represents an Anagram
 */
public class Anagrams {
    private Set<String> dictionary; // the full dictionary
    private int max; // max number of words

    /**\
     * This is the constructor method that initialize a new anagram solver over the given dictionary of words.
     * @param dictionary the full dictionary
     */
    public Anagrams(Set<String>dictionary){
        if (dictionary== null){
            throw new IllegalArgumentException("The dictionary set cannot be null!");
        }
        this.dictionary = dictionary;
    }

    /**
     * This method return a set containing all words from the dictionary that can be made
     * using some or all of the letters in the given phrase, in alphabetical order.
     * @param phrase the given phrase
     * @return a set of words that can be an anagram for the phrase
     */
    public Set<String> getWords(String phrase) {
        if (phrase == null) {
            throw new IllegalArgumentException("The phrase cannot be null!");
        }

        // the set of words that could be an anagram of the phrase
        Set <String> wordSet = new TreeSet<>();

        for (String word : dictionary) {
            if (checkWord(phrase, word)) {
                wordSet.add(word);
            }
        }
        return wordSet;
    }

    /**
     * This method find and print all anagrams that can be formed using all of the letters of the given phrase
     * @param phrase the given phrase
     */
    public void print(String phrase){
        if(phrase == null){
            throw new IllegalArgumentException("The phrase cannot be null!");
        }

        String phrase1 = phrase.replaceAll("\\s+", "");
        Set<String> words = getWords(phrase);
        String[] arr = words.toArray(new String[words.size()]);
        ArrayList<String> chosen = new ArrayList<>();

        printHelper(phrase1, arr, chosen);
    }

    /**
     * This method find and print all anagrams that can be formed using all of the letters of the given phrase
     * and that include at most max words total
     * @param phrase the given phrase
     * @param max max total number of words
     */

    public void print(String phrase, int max){
        this.max = max;
        print(phrase);
    }

    // HELPER METHODS

    /**
     * This method checks if characters in the word contains the characters in the phrase,
     * it will return true if the word does contains characters in the phrase and false otherwise.
     * @param phrase the phrase to check with
     * @param word the word to be checked
     * @return boolean value of true or false
     */
    private boolean checkWord(String phrase, String word){

        for (int i = 0; i < word.length(); i++) {
            if (phrase.contains(Character.toString((word.charAt(i))))){
                char c = word.charAt(i);
                word = word.substring(0, i) + word.substring(i + 1, word.length());
                i--;
                for (int j = 0; j < phrase.length(); j++) {
                    if (c == phrase.charAt(j)) {
                        phrase = phrase.substring(0, j) + phrase.substring(j + 1, phrase.length());
                        break;
                    }
                }
            }
        }
        if (word.length() == 0) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This method helps select which words to be output
     * @param phrase the given phrase
     * @param words the array of words that could be the anagram of the phrase
     * @param chosen a list of chosen words
     */
    private void printHelper(String phrase, String[] words, ArrayList<String> chosen){
        String keepPhrase = phrase;
        if (phrase.length() == 0){
            if (this.max == 0 || chosen.size() == this.max) {
                String[] chosenArray = new String[chosen.size()];
                chosenArray = chosen.toArray(chosenArray);
                System.out.println(Arrays.toString(chosenArray));
            }

        }
        else {
            for (int i = 0; i < words.length; i++) {
                if (checkWord(phrase, words[i])) {
                    phrase = restPhrase(phrase, words[i]);
                    if (!chosen.contains(words[i])) {
                        chosen.add(words[i]);

                        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(words));
                        for (int k = 0; k < arrayList.size(); k++) {
                            if (arrayList.get(k) == words[i]) {
                                arrayList.remove(k);
                            }
                        }
                        String[] arr = new String[arrayList.size()];
                        arr = arrayList.toArray(arr);

                        // recursive call
                        printHelper(phrase, arr, chosen);

                        phrase = keepPhrase;
                        chosen.remove(words[i]);
                    }
                }
            }
        }
    }

    /**
     * This method is to subtract existing characters on word from the phrase and return a
     * modified (already deducted) phrase
     * @param phrase the given phrase
     * @param word the chosen word
     * @return the remaining phrase
     */
    private String restPhrase(String phrase, String word){
        for (int i = 0; i < word.length(); i++){
            char c = word.charAt(i);

            for (int j =0; j < phrase.length(); j++){
                if (c==phrase.charAt(j)){
                    phrase = phrase.substring(0, j) + phrase.substring(j+1, phrase.length());
                    break;
                }
            }
        }
        return phrase;
    }
