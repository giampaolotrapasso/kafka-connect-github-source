# Learning

This project is a companion repository to the [Apache Kafka Connect course on Udemy](https://www.udemy.com/kafka-connect/?couponCode=GITHUB10). 

https://www.udemy.com/kafka-connect/?couponCode=GITHUB10

# Kafka Connect Source GitHub

This connector allows you to get a stream of issues and pull requests from your GitHub repository, using the GitHub Api: https://developer.github.com/v3/issues/#list-issues-for-a-repository

Issues are pulled based on `updated_at` field, meaning any update to an issue or pull request will appear in the stream. 

The connector writes to topic that is great candidate to demonstrate *log compaction*. It's also a fun way to automate your GitHub workflow. 

It's finally aimed to be an educative example to demonstrate how to write a Source Connector a little less trivial than the `FileStreamSourceConnector` provided in Kafka.

# Contributing

This connector is not perfect and can be improved, please feel free to submit any PR you deem useful. 

# Configuration

```
name=GitHubSourceConnectorDemo
tasks.max=1
connector.class=com.simplesteph.kafka.GitHubSourceConnector
github.repositories=scala/scala:lang-topic,apache/kafka:kafka-topic,apache/cassandra:cassandra-topic
# I heavily recommend you set those two fields:
auth.username=your_username
auth.password=your_password
```


## Deprecated parameters
The following parameters have been deprecated:
- topic
- github.owner
- github.repo

You can still use them but they will be removed in future. 

Using them 
```
topic=github-issues
github.owner=kubernetes
github.repo=kubernetes
```

is equivalent to write
```
github.repositories=kubernetes/kubernetes:github-issues
```

# Running in development

Note: Java 8 is required for this connector. 
Make sure `config/worker.properties` is configured to wherever your Kafka cluster is

```
./build.sh
./run.sh 
```

The simplest way to run `run.sh` is to have docker installed. It will pull a Dockerfile and run the connector in standalone mode above it. 

# Deploying

Note: Java 8 is required for this connector. 
