# palindromic

A small Spring Boot REST service that looks up NASA patents for a search term, pulls out the
inventors' names, and reports how many palindromes can be built from each name's letters.

## What it does

`GET /palindromes?search=<term>&limit=<n>`

1. **Validates input** (`Palindromic.palindrones`)
   - `search` is required.
   - `limit` is optional, defaults to `1`, and must be between `1` and `5`; anything else
     returns HTTP `400 Bad Request`.
2. **Queries NASA's patent API** (`Palindromic.getInnovatorsFromPatentsNasa`)
   - Calls `https://api.nasa.gov/patents/content?query=<search>&limit=<limit>&api_key=DEMO_KEY`
     with Spring's `RestTemplate`.
   - The JSON response is mapped onto `Patents` -> `Result` -> `Innovator` (plus `Contact` and
     `Concepts`), POJOs generated with jsonschema2pojo.
3. **Extracts inventor names** (`Patents.getInventorsFirstLastNames`)
   - Every innovator on every returned patent becomes a `"<first> <last>"` string.
4. **Counts palindromes per inventor** (`PalindromeCounter.enumerateResults`), in a parallel stream
   - The name is lower-cased and whitespace is removed, e.g. `"Graham Bell"` -> `"grahambell"`
     (length 10).
   - The distinct letters of the name form the alphabet, e.g. `{a, b, e, g, h, l, m, r}` (8 letters).
   - The service counts every palindrome **of the same length as the name** that can be written
     using that alphabet, with letters allowed to repeat. It builds them recursively from the
     middle outwards (`findNPalindromes`) and adds up how many it finds.
5. **Returns JSON**, one entry per inventor:

   ```json
   [
     { "name": "Graham Bell", "count": 32768 }
   ]
   ```

### What the count means

A palindrome of length `n` is fully determined by its first `ceil(n/2)` characters, so the
count is always:

```
count = k ^ ceil(n / 2)
```

where `k` is the number of distinct letters in the name and `n` is the name's length with
whitespace removed. The values in `PalindromeCounterTest` match this formula:

| Name            | Pre-processed    | n  | k  | count                |
|-----------------|------------------|----|----|----------------------|
| `Graham Bell`   | `grahambell`     | 10 | 8  | 8^5  = 32,768        |
| `Nicola Tesla`  | `nicolatesla`    | 11 | 9  | 9^6  = 531,441       |
| `Thomas Edison` | `thomasedison`   | 12 | 10 | 10^6 = 1,000,000     |

The code finds this number by generating every palindrome (exponential time), which is why long
names can take a very long time to process. The formula above gives the same result instantly.

## Project layout

| File | Purpose |
|------|---------|
| `src/main/java/org/llp/Palindromic.java` | Spring Boot entry point (`main`) and the `/palindromes` REST controller; calls NASA |
| `src/main/java/org/llp/PalindromeCounter.java` | Name pre-processing and recursive palindrome counting |
| `src/main/java/org/llp/ResponseData.java` | Response item: `name` and `count` |
| `src/main/java/org/llp/Patents.java`, `Result.java`, `Innovator.java`, `Contact.java`, `Concepts.java` | Jackson models for the NASA patent API response |
| `src/test/java/org/llp/PalindromeCounterTest.java` | Unit tests for counting and pre-processing |

## Requirements

- Java 1.8 (tested on 1.8_025)
- Maven 3.2.3 or later
- Spring Boot 1.3.5 (pulled in by `pom.xml`)

## Build and run

```bash
mvn clean install

# either
mvn spring-boot:run
# or
java -jar target/palindrome-0.0.1-SNAPSHOT.jar
```

Then call:

```
http://localhost:8080/palindromes?search=electricity&limit=3
```

## Known limitations

- **The NASA endpoint no longer works.** `https://api.nasa.gov/patents/content` now returns
  HTTP 404, so `/palindromes` fails at the NASA call until it is moved to a current NASA
  patent API. The API key is also hardcoded to `DEMO_KEY`.
- **Slow counting.** Generating every palindrome is exponential in name length; even with
  `parallelStream` across inventors, some searches (e.g. `temperature` with `limit=3`,
  `electricity` with `limit=5`) can run for a very long time. The closed-form formula above
  would fix this.
- **Pre-processing only removes whitespace.** Punctuation in names (e.g. `"J. Smith"`) is kept
  and counted as a letter.
- **No circuit breaker or timeout** around the NASA call.
- **Error responses** are bare HTTP status codes with no error body. Because `search` is a
  required `@RequestParam`, Spring returns `400` when it is missing, so the custom
  `MissingArgumentException` (`404`) is never reached.
- **Few tests**: only `PalindromeCounter` is covered; nothing tests the controller or the NASA
  integration.
