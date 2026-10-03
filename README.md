# palindromic
Test Web Service which will allow us to pull palindrones back using Nasa Web Service
for invetors

## Data sources

NASA's old patent API (`api.nasa.gov/patents/content`) is gone, and its replacement does not return
inventor names, so `/palindromes?search=<term>&limit=<n>` now builds the inventor list in three steps:

1. NASA Technology Transfer API - `https://technology.nasa.gov/api/api/patent/<term>` returns NASA case ids
   (e.g. `MFS-TOPS-93`); the first `limit` cases are used.
2. NASA case page - `https://technology.nasa.gov/patent/<caseId>` lists the case's US patent numbers.
3. Google Patents - `https://patents.google.com/patent/US<number>/en` lists each patent's inventors.

Steps 2 and 3 read HTML pages (no API key needed) and may break if those pages change. A failed page is
logged and skipped. Each distinct inventor gets one `{name, count}` entry, where
`count = (distinct letters) ^ ceil(name length / 2)`, returned as an exact big integer.

This program requires

1. Java 17 or later (Gradle toolchain targets Java 17)
2. Nothing else - the Gradle wrapper (`./gradlew`, Gradle 9.8.0) downloads Gradle itself

This is a Spring Boot 4.1.1 application. Build it with `./gradlew build`.
Now it can be run in one of 2 ways

1.  ./gradlew bootRun
2.  java -jar build/libs/palindrome-0.0.1-SNAPSHOT.jar

There are a number of changes mainly the algorithm ie all palindromes even with parallelized can take
a very very long time.  Since its basically a subset of all combinations of the characters of a given size
if you run with temperature with a size of 3 or electricity with a size of 5 it will run and run.
It could take a good bit more work to try to come up with a more optimal algorithm than i have done thus far.


1. Call to any remote webservice should be behind a circuit breaker.  I added parallelStreams but algo still even if
   made parallel it is too slow for request response.
2. I would assume some sort of error message in the response besides just http 
3. Finding palindromes is slow could be improved
4. There are few tests
5. Error handling

Number of patents to be considered
1. default limit = 1
2. max limit is 5


To run
http://localhost:8080/palindromes?search=electricity&limit=3

NOTE:
running with limit of 
   
   
