## Building the APIs

After my initial designing I had a good idea of how I wanted to build my APIs.

### Fetching Data from DB
Building out the APIs themselves was the most exciting part of this project. For each API I first decided which tables I needed in order to surface the response desired, but then I had to take a step back and try to figure out **what type of response was desired**. 

I thought about 3 different types of APIs.
  1. APIs that return a lot of information about all entries matching certain criteria in the data set (for example all movies in a data set or all crew members in a data set).
  2. APIs that return granular level information about one entry matching certain criteria
  3. A combination of 1 and 2
  
For this project I decided to target type 1 and type 2. The key reason I did this was because I think from an API caller's point of view they may be most interested in these two types of APIs. The first type of API gives high level information about all the available id's that they can then use to make a call to the second type of API which provides the granular level information.

For the third type of API I gave it a try and found that the performance of joining tables to achieve the results was very bad. I could optimize this as future work but decided to focus on type 1 and type 2.

### Parallelizing Calls
Most of the APIs I built retrieve data from various tables. For each of these APIs I first built the APIs by making each call to retrieve data from the DB in sequence. Then after I had it working this way I parallelized all of these DB calls (when they were not dependent on each other) leveraging Spring's @Async annotation. I confirmed that the DB calls were indeed running in parallel by adding logs that printed the name of the thread they were running in.


### Calculating Season Rating Algorithm
For part 2 of the assignment I was asked to calculate the season rating for a tvSeries. To keep the API's performance, I built an api that given a tv show id and season number would fetch the rating (instead of an api that returns the season ratings for all of the season given a show id).

The algorithm given was to simply add the individual episode ratings for a season and return that as the season rating. However I decided to push back on this algorithm because the algorithm seems to imply that, for example, show A's season rating will be higher than show B's season rating if show A simply has more episodes than show B for the seasons being compared (assuming the episode ratings themselves between the two are relatively the same).

Furthermore, if we want to compare how season 1 of show A did in comparison to season 2 of show A we can't assume that both seasons have the same number of episodes. 

To this end I decided to instead take the average of the episode ratings and assign it as the season rating. The average was calcualted by a single SQL query command that took the average of all rows with a column value matching the season id.

The expected response here provides a granular level view of 1 season.

### Updating the Episode Rating
For part 3 of the assignment I was asked to update the episode ratings periodicially using randomized logic as desired and to ensure the season ratings calculated used the new episode ratings. To achieve this I wrote a cron job that executed every 15000ms and multiplies the values for episode table data ratings by a value between 0.8 and 1.1. As the season ratings are calculated on the fly by directly accessing the DB which has the episode ratings, the season ratings would always be in sync and accurate with whatever the most up to date data is. In a more scaled application we might consider storing the season ratings calculations in yet another table and then making this API call directly against the season ratings table. If we scaled the project this way then when the episode data is updated through the cron job it would deliver a successful job notification and then we would have another job which triggers to recalculate and update all the values in our season ratings table.

For the scope of this project I did not maintain this separate seasons ratings table so I did not have to build this logic out to keep two DBs in sync but if needed it could easily be maintained as described above.


### Error Handling
To handle errors that could occur in my APIs I create a [GlobalException](https://github.com/brandontineo/Netflix/blob/master/src/main/java/com/btineo/netflixTakehome/exceptions/GlobalException.java) class which all my custom exceptions extended. 

To present the error in a nice way to the API caller I created an ApiError class which surfaced useful information to the API caller. An example of this response is found below:

```
{
    "status": "NOT_FOUND",
    "timestamp": "19-07-2019 09:55:35",
    "message": "No movies found.",
    "suggestion": "Please try a different movie id",
    "correlationId": "6c42707c-3773-4228-81ad-7b5fbcfd9928"
}
```

The above body would come back with a 404 response to `http://localhost:8080/title?id=tt004103` since tt004103 is not a movie that has any entries in the IMBD data.

Within the body of the error I provide the error message but also a suggestion to the API caller to enable the API caller to resolve their issue instead of feeling stuck.

Finally the corrrelationId field is a unique identifier for this transaction. In a real system all of the logs related to this API call would be logged with this correlationId. The API caller (if internal) could the do a search in the logs for all transactions tagged with this correlationId or if they are interacting with professional services team they would be able to share this correlation ID and in turn PS team could look into the logs to see what happened.


#### Response Codes

For this project, given the time constraints, I decided to handle three response codes
- **200 OK** for when the API successfully delivered the data as expected
- **404 NOT FOUND** for when the API was unable to find a key resource needed to deliver the response. In most cases this was the tconst ID passed as a query param was not found in our data.
- **500 INTERNAL SERVER ERROR** for when an unexpected runtime exception was thrown such as NullPointerException or SQLException. Althought these should never be thrown, I wanted to make sure the response was as clean as possible if they did get thrown.

With more time I have liked to handle for more scenarios or provided a more granular view exactly what the issue was.
