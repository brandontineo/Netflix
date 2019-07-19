DROP TABLE titles IF EXISTS;
CREATE TABLE titles  (
    tconst VARCHAR(400),
    primaryTitle VARCHAR(400),
    titleType VARCHAR(400),
    originalTitle VARCHAR(400),
    isAdult VARCHAR(400),
    startYear VARCHAR(400),
    endYear VARCHAR(400),
    runtimeMinutes VARCHAR(400),
    genres VARCHAR(400)
);
CREATE INDEX ix_titles
ON titles(tconst);


DROP TABLE episodes IF EXISTS;
CREATE TABLE episodes  (
    tconst VARCHAR(400),
    parentTconst VARCHAR(400),
    seasonNumber VARCHAR(400),
    episodeNumber VARCHAR(400)
);
CREATE INDEX ix_episodes
ON episodes(parentTconst, seasonNumber);


DROP TABLE ratings IF EXISTS;
CREATE TABLE ratings  (
    tconst VARCHAR(400),
    averageRating FLOAT(52),
    numVotes VARCHAR(400)
);
CREATE INDEX ix_ratings
ON ratings(tconst);


DROP TABLE principal IF EXISTS;
CREATE TABLE principal  (
    tconst VARCHAR(400),
    ordering VARCHAR(400),
    nconst VARCHAR(400),
    category VARCHAR(400),
    job VARCHAR(400),
    characters VARCHAR(800)
);
CREATE INDEX ix_principal
ON principal(tconst);