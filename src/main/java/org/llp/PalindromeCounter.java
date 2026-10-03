package org.llp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
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

    /** A palindrome of length n is fixed by its first ceil(n/2) characters, so the count is k^ceil(n/2). */
    private PalindromeCount enumeratePalidrones(ArrayList<Character> charSet, int size) {
        return new PalindromeCount(BigInteger.valueOf(charSet.size()).pow((size + 1) / 2));
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
        private final BigInteger count;

        PalindromeCount(BigInteger count){
            this.count = count;
        }

        BigInteger getCount(){
            return count;
        }
    }

}
