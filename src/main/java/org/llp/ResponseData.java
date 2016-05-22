package org.llp;

/**
 *
 * User: lpresswood
 * Date: 5/21/16
 * Time: 8:29 AM
 */
public class ResponseData {
    private final String name;
    private final int count;

    public ResponseData(String name, PalindromeCounter.PalindromeCount palindromeCount){
        this.name = name;
        if ( palindromeCount != null ){
            count = palindromeCount.getCount();
        }
        else {
            count = 0;
        }
    }


    public ResponseData(String name, int count) {
        this.name = name;
        this.count = count;
    }


    public String getName() {
        return name;
    }

    public int getCount() {
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
