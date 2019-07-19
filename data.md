### Data
#### Getting to know the Data
The data provided by IMBD is in tsv.gz files hosted on their site and refreshed every 24 hours to reflect the most up to update content. The start building my project I began by inspecting the schema of the different tsv.gz files to see which ones contained the information I would need to completed my tasks.

I decided that I would need the following tsv files: titles.basics, title.episode, title.principals, title.ratings.

Once I had reviewed the schema of the data I decided to do a visual inspection of what the data looked like to get a good idea of how much data wrangling I would need to do with the data. I found that the data files were nicely and properly formatted. However there was missing data  here and there in various columns represented with "\N". Additionally I found that a few of the rows in the tsv files did not use "\N" and instead just had one or two less tabs.

Finally as part of my data exploratory phase I wrote a small script to read in the first 100 rows of the tsv files and print out rows that had missing data or less than the expected number of tabbed values. 

#### Reading and Persisting the Data
To read in the data I decided to work with Spring batch processing. For each file to be read in I created a job that would read and commit the file to a DB in chunks. The typical job looked as follows (variable names have been updated here to reflect a generic job in reality the variable names used  accurately describe the job/steps being done)"

```
	@Bean
	public Step theStep() {
		return stepBuilderFactory.get("theStep")
				.<SomeDTO, SomeDTO>chunk(chunkValue)
				.reader(myReader())
				.processor(myProcessor())
				.writer(myWriter())
                .faultTolerant()
                .skipPolicy(new ItemSkipPolicy())
                .taskExecutor(IMBDTaskExecutor.taskExecutor()).build();
	}
	

	@Bean("Job")
	Job theJob(JobCompletionNotificationListener listener) {
		return jobBuilderFactory.get("theJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(theStep())
				.end()
				.build();
	}
```

Reading in the file was not very challenging however getting comfortable with storing the information in an memory database is where things got a little bit tricky. The tsv files were stored using in memory databases using Spring Data JPA. I had not worked with JPA before as most of the spring projects I have worked with do not use in memory database. I decided I would work with JPA because before starting the project I did some research and it seemed like something that would great to learn. I know that typically JPA is used to obviate the need to write actual SQL queries but in this project I went with a hybrid approach to allow myself enough time to finish the project in the reccommended timeframe.

##### Challenges Encountered
One challenge I ran into while reading and persisting the data is that the tsv files were/are very large and thus the operations involved in the jobs used to read in the files were talking upwards of 20 minutes to complete on start up. Initially I decided that to get going I would work with smaller versions/subsets of the files. To this end I worked with simple-titles.basics.tv, simple-titles.ratings.tsv, simple-titles.episodes.tsv, and simple-titles.principals.tsv. I thought initially this was a good workaround because in a real application of this nature the APIs I write would draw on databases that already exist and persist large amounts of data instead of me writing some logic to persist them on start up of my application. Once I successfully read in the files and persisted them I decided to revisit the processing time issue

##### Multi Threading
To enhance performance and processing time I created a task executor that would delegate each chunk being read in the job to its own thread. This meant that I could delegate the reading in of a tsv to multiple workers that each read and commited parts of the file to the DB.

Next I decided to explore the way in which I could run all of the jobs in parallel instead of reading and committing one file at a time. After doing some research online I found that Spring by defaults use a jobLauncher that launches each job within the same thread but that to launch the jobs in parallel all you had to do with inject a custom job launcher that specified SimpleAsyncTaskExecutor as the executor to use. I struggled a little bit with figuring out how to actually get the jobs to fire in parallel with this jobLauncher. By default Spring will execute the jobs on startup and even after auto wiring the custom jobLauncher the jobs were still executing in sequence. Eventually I found a way to run the jobs on a schedule and how to configure the scheduler to use the custom job launcher. This solution is not 100% ideal because I do not want to launch the jobs on a schedule but rather on start up but I have made a note of it to come back to.

##### Thread Safety
After implemeting the multithreading for the chunks and for the jobs themselves I started to worry about the thread safety of my approach. The Spring console was warning `2019-07-18 22:06:36.936  WARN 52581 --- [  spring_batch1] o.s.batch.core.step.item.ChunkMonitor    : ItemStream was opened in a different thread.  Restart data could be compromised.` which was not a good sign. After doing some research I found ways to ensure that my logic was thread safe. My findings were as follows:
  - It is safe to use a FlatFileItemReader with a TaskExecutor provided the Writer is thread-safe: https://stackoverflow.com/questions/42270806/using-flatfileitemreader-with-a-taskexecutor-thread-safety
  - The writer is thread-safe after its properties are set (normal singleton behavior), so it can be used to write in multiple concurrent transactions: https://docs.spring.io/spring-batch/trunk/apidocs/org/springframework/batch/item/database/JdbcBatchItemWriter.html
  - While the readerr is thread safe in that using it to read from multiple threads is ok, that reader will not support restart when used via multiple threads. Hence I needed to set the save state to false: https://stackoverflow.com/questions/23780587/spring-batch-reader-in-multi-threader-job

##### Final Remarks on Reading and Persisting Data
Even after all of the multithreading I found that reading in the data was taking far too long (roughly 5-6 minutes) and every some time my computer was not behaving properly likely because I needed to allocate more memory to the application I was using to run my Spring Project. Furthermore I found I could not even git commit the real tsv files themselves because they were too big. To that end I have committed the smaller tsv files and made it very easy to toggle between the smaller tsv files and the actual tsv files if desired by whoever runs this program. In [IMBDConstants.java](https://github.com/brandontineo/Netflix/blob/master/src/main/java/com/btineo/netflixTakehome/constants/IMBDConstants.java) you can simply comment out/comment in to toggle

```
	// For testing
	public static final String RATINGS_FILE = "sample-title.ratings.tsv";
	public static final String TITLES_FILE = "sample-title.basics.tsv";
	public static final String PRINCIPALS_FILE = "sample-title.principals.tsv";
	public static final String EPSIODES_FILE = "sample-title.episode.tsv";

	// The real deal
//	public static final String RATINGS_FILE = "title.ratings.tsv";
//	public static final String TITLES_FILE = "title.basics.tsv";
//	public static final String PRINCIPALS_FILE = "title.principals.tsv";
//	public static final String EPSIODES_FILE = "title.episode.tsv";
	
```
