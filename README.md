# Mini Twitter API

This is the complete solution for the code challenge related to the recruiting process for HSBC. 

## Requirements

For the code challenge details and requirements please refer [Challenge details](docs/REQUIREMENTS.md)

## Description

This project provides the solution for building a simple social networking application, similar to Twitter, which is
exposed through a web API. The application supports the scenarios listed below.

## Build and run

For starting the application run following command in the console in the project root folder:
```
mvn spring-boot:run
```

## Scenarios

For testing purposes you can use one of the following methods:
 - use some add-on for Firefox like [HTTPRequester](https://addons.mozilla.org/En-us/firefox/addon/httprequester/)
 - use some add-on for Chrome like [Postman](https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en)
 - use command line tool cUrl

For the convinience examples below are in the following format:

 - URL: `target API url`
 - Content: `Content body in JSON format`
 - Method: `HTTP method used for reaching the API`
 - Status: `HTTP Response status code`
 - Response: `HTTP Response content body`
 
### Posting

A user can post a 140 character message. 

URL:
```
    http://localhost:8080/alexi/messages
```
Content:
```
{
    "text": "Some text message"
}
``` 
Method:
```
    POST
```
Status:
```
    201
```
Response:
```
{
    "id": 1,
    "text": "Some text message",
    "createdTime": {
        "dayOfMonth": 13,
        "dayOfWeek": "THURSDAY",
        "dayOfYear": 194,
        "month": "JULY",
        "monthValue": 7,
        "year": 2017,
        "hour": 10,
        "minute": 17,
        "nano": 653000000,
        "second": 35,
        "chronology": {
            "id": "ISO",
            "calendarType": "iso8601"
        }
    },
    "user": {
        "id": 1,
        "userName": "alexi"
    }
}
```
 
### Wall

A user can see a list of the messages they've posted, in reverse
chronological order.

URL:
```
    http://localhost:8080/alexi/messages
```
Content:
```
    empty
``` 
Method:
```
    GET
```
Status:
```
    200
```
Response:
```
[
    {
        "id": 1,
        "text": "Some text message",
        "createdTime": {
            "dayOfMonth": 13,
            "dayOfWeek": "THURSDAY",
            "dayOfYear": 194,
            "month": "JULY",
            "monthValue": 7,
            "year": 2017,
            "hour": 10,
            "minute": 17,
            "nano": 653000000,
            "second": 35,
            "chronology": {
                "id": "ISO",
                "calendarType": "iso8601"
            }
        },
        "user": {
            "id": 1,
            "userName": "alexi"
        }
    }
]
```
**NOTE** for this scenario could be used short URL in format `http://localhost:8080/alexi` with the same effect

### Following

A user can follow another user. 

URL:
```
    http://localhost:8080/mike/follow
```
Content:
```
{
    "userName": "alexi"
}
``` 
Method:
```
    PUT
```
Status:
```
    200
```
Response:
```
    empty
```

### Timeline

A user can see a list of the messages posted by all the people they follow, in reverse chronological order.

URL:
```
    http://localhost:8080/mike/timeline
```
Content:
```
    empty
``` 
Method:
```
    GET
```
Status:
```
    200
```
Response:
```
[
    {
        "id": 2,
        "text": "lassdsdfsdfsdft",
        "createdTime": {
            "dayOfMonth": 13,
            "dayOfWeek": "THURSDAY",
            "dayOfYear": 194,
            "month": "JULY",
            "monthValue": 7,
            "year": 2017,
            "hour": 10,
            "minute": 44,
            "nano": 582000000,
            "second": 35,
            "chronology": {
                "id": "ISO",
                "calendarType": "iso8601"
            }
        },
        "user": {
            "id": 1,
            "userName": "alexi"
        }
    },
    {
        "id": 1,
        "text": "lassdsdfsdfsdft",
        "createdTime": {
            "dayOfMonth": 13,
            "dayOfWeek": "THURSDAY",
            "dayOfYear": 194,
            "month": "JULY",
            "monthValue": 7,
            "year": 2017,
            "hour": 10,
            "minute": 44,
            "nano": 533000000,
            "second": 26,
            "chronology": {
                "id": "ISO",
                "calendarType": "iso8601"
            }
        },
        "user": {
            "id": 1,
            "userName": "alexi"
        }
    }
]
```
**NOTE** for this scenario could be used alias URL in format `http://localhost:8080/mike/follow` with the same effect


