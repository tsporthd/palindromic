package org.llp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ª
 * User: lpresswood
 * Date: 5/21/16
 * Time: 8:29 AM
 */

@RestController
@EnableAutoConfiguration
public class Palindromic {

    private static final Logger Log = LoggerFactory.getLogger(Palindromic.class);
    private final NasaPatentInventorClient nasaPatentInventorClient = new NasaPatentInventorClient();

    @SuppressWarnings("unused")
    @RequestMapping(value = "/palindromes", method = RequestMethod.GET)
    @ResponseBody
    List<ResponseData> palindrones(@RequestParam("search") String search, @RequestParam(value = "limit", required = false) Integer limit) {
        int searchLimit = 1;
        if (search == null) {
            Log.error("Requried Argument search is missing");
            throw new MissingArgumentException("search");
        }

        if (limit != null) {
            searchLimit = limit;
            if ((searchLimit > 5) || (searchLimit <= 0)) {
                String msg = String.format("Search Limit must be > 0 and < 6. Search Limit of %d was passed", searchLimit);
                Log.error(msg);
                throw new InvalidArgumentException(msg);
            }
        }

        Log.info("Palindrones called with [Search={}, limit={}]", search, searchLimit);
        List<String> inventors = nasaPatentInventorClient.findInventorNames(search, searchLimit);

        Log.info("There are [Inventors={}, List={}]",inventors.size(),inventors);
        PalindromeCounter palindromeCounter = new PalindromeCounter();

        if (inventors != null && inventors.size() > 0) {
            return inventors.parallelStream()
                .map(palindromeCounter::enumerateResults)
                .collect(Collectors.toCollection(ArrayList<ResponseData>::new));
        } else {
            return Collections.emptyList();
        }


    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Palindromic.class, args);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private class InvalidArgumentException extends IllegalStateException {
        InvalidArgumentException(String message) {
            super(message);
        }

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private class MissingArgumentException extends RuntimeException {

        MissingArgumentException(String argument) {
            super("Required Argument Missing " + argument);
        }
    }

}
