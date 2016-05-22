package org.llp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 *
 * User: lpresswood
 * Date: 5/21/16
 * Time: 4:58 PM
 */
class PalindromeCounter {

    private static final Logger Log = LoggerFactory.getLogger(PalindromeCounter.class);

    public static boolean isItPalindrom(char[] word){
        int i1 = 0;
        int i2 = word.length - 1;
        while (i2 > i1) {
            if (word[i1] != word[i2]) {
                return false;
            }
            ++i1;
            --i2;
        }
        return true;
    }

    private PalindromeCount enumeratePalidrones(ArrayList<Character> charSet, int size) {
        Boolean odd = false;
        if ((size & 1) > 0) {/* odd */
            odd = true;
        }
        return findNPalindromes("", charSet, size, odd);
    }

    private PalindromeCount findNPalindromes(String string,
                                               List<Character> charSet,
                                               int size, Boolean odd) {
        if (string == null) {
            return null;
        }

        if (string.length() == size) {

            if ( !isItPalindrom(string.toCharArray())){
                Log.error("[PalinString = {}, isItPalindrom = {}]", string, false);
            }

            //ArrayList<String> currPalindrome = new ArrayList<>();
            //currPalindrome.add(string);
            return  new PalindromeCount(1);
        }

        //ArrayList<String> allPalindromeStrings = new ArrayList<>();
        PalindromeCount palindromeCount = new PalindromeCount();
        for (int i = 0; i < charSet.size(); i++) {
            String tempString;
            if (odd && string.isEmpty()) {
                tempString = string + charSet.get(i);
            } else {
                tempString = charSet.get(i) + string + charSet.get(i);
            }

            PalindromeCount tempPalindromes = findNPalindromes(tempString, charSet, size, odd);
            palindromeCount.addAllCount(tempPalindromes.getCount());
            //allPalindromeStrings.addAll(tempPalindromes);
        }


        return palindromeCount;

    }

    String preProcessString(String input){
        StringBuilder builder = new StringBuilder(input.length());
        for (char c : input.toCharArray()) {
            if ( !Character.isWhitespace(c)) {
                builder.append(Character.toLowerCase(c));
            }
        }
        return builder.toString();
    }


    private Set<Character> createSetOfCharsFromString(String ourString) {
        Set<Character> characters = new TreeSet<>();
        for (char c : ourString.toCharArray()) {
            if ( !Character.isWhitespace(c)) {
                characters.add(Character.toLowerCase(c));
            }
        }
        return characters;
    }


    PalindromeCount countAllPalindrones(String input){
        String preprocessString = preProcessString(input);
        Set<Character> characters = createSetOfCharsFromString(input);
        ArrayList<Character> characterList = characters.stream().collect(Collectors.toCollection(ArrayList<Character>::new));
        return enumeratePalidrones(characterList,preprocessString.length());
    }

    ResponseData enumerateResults(String input){
        ResponseData responseData = new ResponseData(input,countAllPalindrones(input));
        Log.info("PalindromeCount for {ResponseData = {}}",responseData.toString());

        return responseData;
    }


    class PalindromeCount{
        Integer count = 0;

        PalindromeCount(){
        }

        PalindromeCount(int count){
            this.count = count;
        }


        void addAllCount(int inCount){
            count += inCount;
        }

        Integer getCount(){
            return count;
        }


    }

}
