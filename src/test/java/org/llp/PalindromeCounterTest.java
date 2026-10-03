package org.llp;


import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals(BigInteger.valueOf(32768), counter.getCount());


        counter = palindromeCounter.countAllPalindrones("Nicola Tesla");
        assertNotNull(counter);
        assertEquals(BigInteger.valueOf(531441), counter.getCount());


        counter = palindromeCounter.countAllPalindrones("Thomas Edison");
        assertNotNull(counter);
        assertEquals(BigInteger.valueOf(1_000_000), counter.getCount());


        counter = palindromeCounter.countAllPalindrones("William C. C. Brandsmeier");
        assertEquals(BigInteger.valueOf(13).pow(11), counter.getCount());


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