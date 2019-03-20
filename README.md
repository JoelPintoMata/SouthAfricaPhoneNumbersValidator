[![Maintainability](https://api.codeclimate.com/v1/badges/9d26cb4f5ad75618da98/maintainability)](https://codeclimate.com/github/JoelPintoMata/JavaSparkJSONReader/maintainability)  [![Test Coverage](https://api.codeclimate.com/v1/badges/9d26cb4f5ad75618da98/test_coverage)](https://codeclimate.com/github/JoelPintoMata/JavaSparkJSONReader/test_coverage)    [![codebeat badge](https://codebeat.co/badges/beb261c0-817a-49b3-a9ce-b13e9c5d7c06)](https://codebeat.co/projects/github-com-joelpintomata-javasparkjsonreader-master)  [![Codacy Badge](https://api.codacy.com/project/badge/Grade/8d42efb4c3ad428795222f8adaca7c47)](https://www.codacy.com/app/joelmatacv/JavaSparkJSONReader?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=JoelPintoMata/JavaSparkJSONReader&amp;utm_campaign=Badge_Grade)
# Java Spark JSON reader

## What is
A small application that simulates 7 different actors interested in receiving results according to one of the following rules
0. only user "efteling"
1. only socials containing: "disney"
2. only socials not containing "disney"
3. only socials with video content
4. only socials created before 1st Feb 2017
5. only socials created after 1st Feb 2017
6. only Facebook socials

## Source structure
Array of:
{"socialType":"","socialId":"","timestamp":"","username":"","userId":"","content":"","latitude":N,"longitude":N}

## Running application
```
mvn spring-boot:run
```

## Running application tests
```
mvn test
```

## Usage

### Endpoints

| Definition | URI |
| --- | --- |
| Uploads a file | /upload |

## CURL

curl -X POST -d "@./src/test/resources/south_african_mobile_numbers.txt" http://localhost:8080/upload
