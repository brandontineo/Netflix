
## APIs Supported

#### GET `http://localhost:8080/titles`
| Description   |      Path      |  Query Params |
|----------|:-------------:|------:|
| This API returns a list of all the available movies and episodes from 2018 |  /titles | N/A | 
```
[
    {
        "id": "tt0000014",
        "title": "The Sprinkler Sprinkled",
        "year": "2018",
        "type": "movie"
    },
    {
        "id": "tt0000018",
        "title": "Das boxende Känguruh",
        "year": "2018",
        "type": "movie"
    } ]
```

#### GET `http://localhost:8080/title?id=<id_of_title>`
| Description   |      Path      |  Query Params |
|----------|:-------------:|------:|
| This API returns granular view of the data for 1 movie |  /titles | id [the tconst ID of a movie] | 
```
{
    "showId": "tt0041038",
    "showName": "ParentShowLe clown et ses chiens",
    "year": "2018",
    "crewMembers": [
        {
            "crewMemberId": "nm0366359",
            "category": "actor",
            "job": "UNKNOWN",
            "characterNames": [
                "The Lone Ranger",
                "Duke",
                "Guard"
            ]
        },
        {
            "crewMemberId": "nm0872077",
            "category": "writer",
            "job": "creator",
            "characterNames": []
        },
	.....
	.....
	.....
        {
            "crewMemberId": "nm0103048",
            "category": "actor",
            "job": "UNKNOWN",
            "characterNames": [
                "Duke Wade",
                "Dusty",
                "Gat Towson"
            ]
        }
    ]
}
```
#### GET `http://localhost:8080/child-episodes-by-parent-id?id=<id_of_title>`
| Description   |      Path      |  Query Params |
|----------|:-------------:|------:|
| This API returns granular view of the data for 1 TV series including granular information about episodes |  /child-episodes-by-parent-id | id [the tconst ID of a tvSeries] | 
```
{
    "showId": "tt0041038",
    "showName": "ParentShowLe clown et ses chiens",
    "seasons": [
        {
            "episodes": [
                {
                    "episodeId": "tt0041952",
                    "episodeName": "UNKNOWN",
                    "episodeRating": -1,
                    "episodeNumber": 1
                },
                {
                    "episodeId": "tt0041953",
                    "episodeName": "Pauvre Pierrot",
                    "episodeRating": -1,
                    "episodeNumber": 2
                },
                {
                    "episodeId": "tt0041954",
                    "episodeName": "UNKNOWN",
                    "episodeRating": -1,
                    "episodeNumber": 4
                },
                {
                    "episodeId": "tt0041955",
                    "episodeName": "Blacksmith Scene",
                    "episodeRating": -1,
                    "episodeNumber": 3
                }
            ],
            "seasonRating": 6.7,
            "seasonNumber": 1
        }
    ],
    "year": 2018
}
```

#### GET `http://localhost:8080/season-rating?id=<id_of_tilte&seasonNumber=<season_number>`
| Description   |      Path      |  Query Params |
|----------|:-------------:|------:|
| This API returns the average rating for a season based on an average of the rating for the episodes in the season |  season-rating | id [the tconst ID of a tvSeries], seasonNumber [the season number of interest] | 

```
{
    "episodes": [
        {
            "episodeId": "tt0041952",
            "episodeNumber": 1
        },
        {
            "episodeId": "tt0041953",
            "episodeNumber": 2
        },
        {
            "episodeId": "tt0041954",
            "episodeNumber": 3
        },
        {
            "episodeId": "tt0041955",
            "episodeNumber": 4
        }
    ],
    "seasonRating": 5.225,
    "seasonNumber": 1
}
```
