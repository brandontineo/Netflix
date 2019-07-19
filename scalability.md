### Scalability

In an effort to improve the performance of my APIs I did the following
- Batch job processing of TSV files
- Multithreading for each batch chunk
- Multithreading for each batch job itself 
- Parallel processing within APIs for DB fetches of data
- Index the relevasnt columns in SQL tables 

Even with all of these changes the entire data set provided poor execution times for both the reading in of the files and the response within the APIs themselves. 

The scalability of this project truly depends on:

  1. Having the data alreadty stored in a DB. If this were a real application there would be some other service or deamon running to insert the data in the DB and update records as needed. At the time my application starts, the APIs should be primarily concerned with how to retrieve, process, and deliver the data...and not so much with how the data got stored in the first place 
  
  2. Using a better and faster performing database. Fetching data through SQL is ok, but in future this type of big data would be a great candidate to handle using Druid queries and a elasticsearch indexes.

More notes and information on optimization are sprinkled throughout the rest of the documentation.
