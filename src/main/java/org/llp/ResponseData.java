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
}
