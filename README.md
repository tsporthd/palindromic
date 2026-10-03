# palindromic

A small Spring Boot REST service that looks up NASA patents for a search term, finds the
inventors' names, and reports how many palindromes can be built from each name's letters.

## What it does

`GET /palindromes?search=<term>&limit=<n>`

1. **Validates input** (`Palindromic`)
   - `search` is required.
   - `limit` is optional, defaults to `1`, and must be between `1` and `5`; anything else
     returns HTTP `400 Bad Request`.
2. **Finds inventors** (`NasaPatentInventorClient.findInventorNames`). NASA's old patent API
   (`api.nasa.gov/patents/content`) is gone and its replacement does not return inventor names,
   so the list is built in three steps:
   1. NASA Technology Transfer API - `https://technology.nasa.gov/api/api/patent/<term>` returns
      NASA case ids (e.g. `MFS-TOPS-93`); the first `limit` cases are used.
   2. NASA case page - `https://technology.nasa.gov/patent/<caseId>` lists the case's US patent
      numbers.
   3. Google Patents - `https://patents.google.com/patent/US<number>/en` lists each patent's
      inventors.

   Inventors are deduplicated across patents. Cases without a granted US patent contribute no
   inventors.
3. **Counts palindromes per inventor** (`PalindromeCounter.enumerateResults`)
   - The name is lower-cased and whitespace is removed, e.g. `"Graham Bell"` -> `"grahambell"`
     (length 10).
   - The distinct letters of the name form the alphabet, e.g. `{a, b, e, g, h, l, m, r}` (8 letters).
   - The count is the number of palindromes **of the same length as the name** that can be
     written using that alphabet, with letters allowed to repeat.
4. **Returns JSON**, one entry per inventor:

   ```json
   [
     { "name": "Paul R. Gradl", "count": 262144 }
   ]
   ```

### What the count means

A palindrome of length `n` is fully determined by its first `ceil(n/2)` characters, so the
count is:

```
count = k ^ ceil(n / 2)
```

where `k` is the number of distinct letters in the name and `n` is the name's length with
whitespace removed. `PalindromeCounter` computes this directly as a `BigInteger`, so long names
are instant and never overflow. The values in `PalindromeCounterTest`:

| Name            | Pre-processed    | n  | k  | count                |
|-----------------|------------------|----|----|----------------------|
| `Graham Bell`   | `grahambell`     | 10 | 8  | 8^5  = 32,768        |
| `Nicola Tesla`  | `nicolatesla`    | 11 | 9  | 9^6  = 531,441       |
| `Thomas Edison` | `thomasedison`   | 12 | 10 | 10^6 = 1,000,000     |

## Project layout

| File | Purpose |
|------|---------|
| `src/main/java/org/llp/Palindromic.java` | Spring Boot entry point (`main`) and the `/palindromes` REST controller |
| `src/main/java/org/llp/NasaPatentInventorClient.java` | NASA search -> NASA case page -> Google Patents lookup |
| `src/main/java/org/llp/PalindromeCounter.java` | Name pre-processing and palindrome counting |
| `src/main/java/org/llp/ResponseData.java` | Response item: `name` and `count` |
| `src/test/java/org/llp/PalindromeCounterTest.java` | Unit tests for counting and pre-processing |
| `src/test/java/org/llp/NasaPatentInventorClientTest.java` | Lookup chain and HTML parsing tests (mocked HTTP) |

## Requirements

- Java 17 or later (Gradle toolchain targets Java 17)
- Nothing else - the Gradle wrapper (`./gradlew`, Gradle 9.8.0) downloads Gradle itself

## Build and run

This is a Spring Boot 4.1.1 application.

```bash
./gradlew build

# either
./gradlew bootRun
# or
java -jar build/libs/palindrome-0.0.1-SNAPSHOT.jar
```

Then call:

```
http://localhost:8080/palindromes?search=electricity&limit=3
```

## Known limitations

- **Google Patents is unofficial.** Steps 2 and 3 read HTML pages (no API key needed) and may
  break if those pages change or if Google rate-limits requests. A page that fails to load is
  logged and skipped. The official alternative, the USPTO Open Data Portal API, needs an API key.
- **Sequential remote calls.** Each case and patent page is fetched one after another
  (10s connect / 30s read timeout each), so larger `limit` values take several seconds.
  There is no circuit breaker or caching.
- **Pre-processing only removes whitespace.** Punctuation in names (e.g. `"Paul R. Gradl"`) is
  kept and counted as a letter.
- **Error responses** are Spring's default error bodies; there are no custom error messages.
- **No controller tests**: tests cover `PalindromeCounter` and `NasaPatentInventorClient` only.
