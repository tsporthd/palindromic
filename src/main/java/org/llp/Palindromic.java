package org.llp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    private static final String QRY_SEP = "?";
    private static final String EQUALS = "=";
    private static final String ARG_DELIM = "&";

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
        List<String> inventors = getInnovatorsFromPatentsNasa(search, searchLimit);

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

    private List<String> getInnovatorsFromPatentsNasa(String query, int limit) {
        final String rootUri = "https://api.nasa.gov/patents/content";
        //https://api.nasa.gov/patents/content?query=temperature&limit=5&api_key=DEMO_KEY
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder fullUri = new StringBuilder(rootUri.length() + 255);
        fullUri.append(rootUri).append(QRY_SEP).append("query").append(EQUALS)
            .append(query).append(ARG_DELIM).append("limit").append(EQUALS)
            .append(limit).append(ARG_DELIM).append("api_key=DEMO_KEY");

        String uri = fullUri.toString();
        Log.info("Calling with URI = {}", uri);
        Patents patents = restTemplate.getForObject(uri,
            Patents.class);
        if (patents == null) {
            return Collections.emptyList();
        } else {
            return patents.getInventorsFirstLastNames();
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
