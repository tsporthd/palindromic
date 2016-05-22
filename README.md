# palindromic
Test Web Service which will allow us to pull palindrones back using Nasa Web Service
for invetors

This program requires

*1. java 1.8 - Tested on 1.8_025 
*2. maven 3.2.3 or greater version should work

This is a spring boot application so all you should neet to do is run mvn clean install
Now it can be run in one of 2 ways

*A.  mvn spring-boot:run
*B.  change to target directory and run java -jar  palindrome-0.0.1-SNAPSHOT.jar

There are a number of changes if this were to ever go in prod

*A. Call to any remote webservice should be behind a circuit breaker and perhaps async with Actor or 
   ExecutorService
*B. I would assume some sort of error message in the response besides just http 
*C. Finding palindromes is slow could be improved
*D. There are few tests
*E. Error handling
...
   
   
