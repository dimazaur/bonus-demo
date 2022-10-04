# Spring-demo

##Prerequisites
- JDK 17
- Maven
- MySQL database 

## MySQL configuration
File src/main/resources/application.properties

## Local start
mvnw spring-boot:run

## Default http port
8080

## 

## Add one transaction for the user AAA

`
curl --location --request POST 'http://localhost:8080/transaction' \
--header 'Content-Type: application/json' \
--data-raw '{
"userId": "AAA",
"amount": 75.00,
"timestamp": "2022-10-01 01:01:01"
}'
`

## Add list of transactions for the user AAA

`curl --location --request POST 'http://localhost:8080/transaction/list' \
--header 'Content-Type: application/json' \
--data-raw '[
{
"userId": "AAA",
"amount": 50.00,
"timestamp": "2022-08-01 01:01:01"
},
{
"userId": "AAA",
"amount": 75.00,
"timestamp": "2022-08-02 02:02:02"
},
{
"userId": "AAA",
"amount": 150.00,
"timestamp": "2022-08-03 03:03:03"
}
]'`


## List all transactions for the user AAA

`curl --location --request GET 'http://localhost:8080/transaction/list/AAA'`

## List all transactions for the user AAA and the time period

`curl --location --request GET 'http://localhost:8080/transaction/date/AAA?dateFrom=2022-08-01T00:00:00&dateTo=2022-08-30T00:00:00'`

## Calculate total points for the user AAA

`curl --location --request GET 'http://localhost:8080/points/total/AAA'`

## Calculate points for the user AAA and period of time

`curl --location --request GET 'http://localhost:8080/points/AAA?dateFrom=2022-08-01T00:00:00&dateTo=2022-10-30T00:00:00'`


## Docker image build
