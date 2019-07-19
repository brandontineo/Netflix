## Future Work

The scope of this project was constrained to roughly 4 hours but I could continue working on it if I wanted to. The initial set up to meet the requirements took a little over 4 hours and I continued working on it for several hours since then. However even with all the time spent there is always room for imrovement. Below is a list of all the future work items I could work on

- Handle more response codes
- Provide more granular information in error responses
- Refactor batch jobs to re-use more code then they already re-use
- API responses by convention should not return camelCase fields. Look into this by @JSONProperty annotation was being ignored
- Implement more logging. I added a lot of logs to the APIs but in experience I've found that when something goes wrong the logs are truly what's there to save you. Also the logs should also contain the correlationId to allow for future searches of the logs by correlationId sent in error response.
- Consider using ElasticSearch
- Break out the logic for reading and storing the TSV files into DB to its own service/project
