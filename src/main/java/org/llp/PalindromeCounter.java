package org.llp;

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

    private List<String> enumeratePalidrones(ArrayList<Character> charSet, int size) {
        Boolean odd = false;
        if ((size & 1) > 0) {/* odd */
            odd = true;
        }
        return findNPalindromes("", charSet, size, odd);
    }

    private ArrayList<String> findNPalindromes(String string,
                                               List<Character> charSet,
                                               int size, Boolean odd) {
        if (string == null) {
            return null;
        }

        if (string.length() == size) {
            ArrayList<String> currPalindrome = new ArrayList<>();
            currPalindrome.add(string);
            return currPalindrome;
        }

        ArrayList<String> allPalindromeStrings = new ArrayList<>();
        for (int i = 0; i < charSet.size(); i++) {
            String tempString;
            if (odd && string.isEmpty()) {
                tempString = string + charSet.get(i);
            } else {
                tempString = charSet.get(i) + string + charSet.get(i);
            }

            ArrayList<String> tempPalindromes = findNPalindromes(tempString, charSet, size, odd);
            allPalindromeStrings.addAll(tempPalindromes);
        }


        return allPalindromeStrings;

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


    List<String> findAllPalindrones(String input){
        String preprocessString = preProcessString(input);
        Set<Character> characters = createSetOfCharsFromString(input);
        ArrayList<Character> characterList = characters.stream().collect(Collectors.toCollection(ArrayList<Character>::new));

        return enumeratePalidrones(characterList,preprocessString.length());

    }

}
