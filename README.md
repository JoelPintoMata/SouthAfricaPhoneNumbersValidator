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
| Definition | Method | URI | Parameters |
| --- | --- | --- | --- |
| Uploads a file | POST | /upload |  |
| Retrieves a validation result | GET | /get | id |
| Validates a single phone number | POST | /get | phoneNumber |

__Postman__ check `SouthAfricanPhoneNumbers.postman_collection.json` for working examples.

## Bussiness logic

### Automatic phone number format
The validation implementation performs some fixes automaticaly, namely:
1. Adding a traling `0` whenever a number has 9 digits
2. Validating and fixing phone numbers in the form <number_1>_DELETED_<number_2>. In this case the validation/fix is applied on <number_2>.