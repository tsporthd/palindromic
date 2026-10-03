package org.llp;

import java.math.BigInteger;

/**
 *
 * User: lpresswood
 * Date: 5/21/16
 * Time: 8:29 AM
 */
public class ResponseData {
    private final String name;
    private final BigInteger count;

    public ResponseData(String name, PalindromeCounter.PalindromeCount palindromeCount){
        this.name = name;
        if ( palindromeCount != null ){
            count = palindromeCount.getCount();
        }
        else {
            count = BigInteger.ZERO;
        }
    }


    public ResponseData(String name, BigInteger count) {
        this.name = name;
        this.count = count;
    }


    public String getName() {
        return name;
    }

    public BigInteger getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
            "name='" + name + '\'' +
            ", count=" + count +
            '}';
    }
}
