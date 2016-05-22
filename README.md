# palindromic
Test Web Service which will allow us to pull palindrones back using Nasa Web Service
for invetors

This program requires

1. java 1.8 - Tested on 1.8_025 
2. maven 3.2.3 or greater version should work

This is a spring boot application so all you should neet to do is run mvn clean install
Now it can be run in one of 2 ways

1.  mvn spring-boot:run
2.  change to target directory and run java -jar  palindrome-0.0.1-SNAPSHOT.jar

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
http://http://localhost:8080/palindromes?search=electricity&limit=3

NOTE:
running with limit of 
   
   
