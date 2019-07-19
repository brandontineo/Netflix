# Netflix

A spring boot rest applicatio serving data from IMBD.

## Assignment
Design: Movie API
### Part-1
Build an application in Java that pulls movies, their ratings, and cast lists from IMDB. To limit the
scope, please pull only movies released in 2018.
Additional Details:
- IMDB provides details on how to download the data: http://www.imdb.com/interfaces
- You may persist the data however you choose
- APIs is sufficient
### Part-2
For TV series, the ratings on episodes were pretty accurate compared to the season ratings.
Calculate the rating for a season based on its episodes. The way to calculate is simply add
ratings from all episodes within a season, and the number becomes the season’s rating.
Please implement method to compute ratings using above algorithm.

- Nice to have: If you think there is a better way to compute the ratings, go ahead and implement
it.

### Part-3
The ratings for episodes are changing constantly, and we want to reflect those changes in our
data. Upgrade you design to synchronize and update the ratings, including re-calculating the
season-level ratings.

- Bonus: Identify areas where you can make the APIs performant and make suggestions/implement on
how you can improve them.

### Delivery
Upload your work to GitHub with a README. We would love to see how you think through the
implementation. Do frequent commits as you progress along with comments. If you have
something drawn on a piece of paper, take a photo and add that as well.
