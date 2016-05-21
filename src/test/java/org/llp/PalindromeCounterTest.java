package org.llp;


import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User: lpresswood
 * Date: 5/21/16
 * Time: 5:03 PM
 */
public class PalindromeCounterTest {
    PalindromeCounter palindromeCounter = new PalindromeCounter();


    @Test
    public void enumeratePalidrones() throws Exception {
        List<String> palindrones = palindromeCounter.findAllPalindrones("Graham Bell");
        assertNotNull(palindrones);
        assertTrue(palindrones.size() == 32768);


        palindrones = palindromeCounter.findAllPalindrones("Nicola Tesla");
        assertNotNull(palindrones);
        assertTrue(palindrones.size() == 531441);


        palindrones = palindromeCounter.findAllPalindrones("Thomas Edison");
        assertNotNull(palindrones);
        assertTrue(palindrones.size() == 1_000_000);


    }

    @Test
    public void testStringFix() {
        String test1 = "Graham Bell";
        String fixString = palindromeCounter.preProcessString(test1);
        assertTrue(fixString.equals("grahambell"));

        test1 = "Nicholas Presswood";
        fixString = palindromeCounter.preProcessString(test1);
        assertTrue(fixString.equals("nicholaspresswood"));
    }


}