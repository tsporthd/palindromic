package org.llp;


import org.junit.Test;

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
        PalindromeCounter.PalindromeCount counter = palindromeCounter.countAllPalindrones("Graham Bell");
        assertNotNull(counter);
        assertTrue(counter.getCount() == 32768);


        counter = palindromeCounter.countAllPalindrones("Nicola Tesla");
        assertNotNull(counter);
        assertTrue(counter.getCount() == 531441);


        counter = palindromeCounter.countAllPalindrones("Thomas Edison");
        assertNotNull(counter);
        assertTrue(counter.getCount() == 1_000_000);


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