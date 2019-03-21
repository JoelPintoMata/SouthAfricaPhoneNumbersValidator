# Phone number validation

## What is
A phone number validation application

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

| Definition | Method | URI | Parameters | Example |
| --- | --- | --- | --- | --- |
| Uploads a file | POST | /upload |  |
| Retrieves a validation result | GET | /get | id |
| Validates a single phone number | GET | /get | phoneNumber |

## CURL

curl -X POST --header "Content-Type: text/csv" --data "@src/test/resources/south_african_mobile_numbers.txt" http://localhost:8080/upload
