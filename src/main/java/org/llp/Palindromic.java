package org.llp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @SuppressWarnings("unused")
    @RequestMapping(value = "/palindromes" , method = RequestMethod.GET)
    @ResponseBody
    List<ResponseData> palindrones(@RequestParam("search") String search, @RequestParam(value = "limit", required = false) Integer limit ) {
        int searchLimit = 1;
        if ( search == null ){
            Log.error("Requried Argument search is missing");
            throw new MissingArgumentException("search");
        }

        if ( limit != null ){
           searchLimit = limit;
        }

        Log.info("Palindrones called with [Search={}, limit={}]",search,searchLimit);

        ArrayList<ResponseData> responses = new ArrayList<ResponseData>();
        responses.add(new ResponseData("Thomas Edison",1000000));
        responses.add(new ResponseData("Nicola Tesla",531441));
        responses.add(new ResponseData("Graham Bell",32768));



        return responses;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Palindromic.class, args);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private class MissingArgumentException extends RuntimeException {

        MissingArgumentException(String argument) {
            super("Required Argument Missing " + argument);
        }
    }

}
