curl -d '{"id":null,"dateTime":"2020-01-31T20:00:01","description":"Кефирчик","calories":150}' -H "Content-Type: application/json" -X POST http://localhost:8082/topjava/rest/meals/

curl http://localhost:8082/topjava/rest/meals

curl http://localhost:8082/topjava/rest/meals/100006

curl -X GET 'http://localhost:8082/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-30&startTime=13:00&endTime=13:01'

curl -d '{"id":100012,"dateTime":"2020-01-31T20:00:01","description":"Кефирчик2","calories":250}' -H "Content-Type: application/json" -X PUT http://localhost:8082/topjava/rest/meals/100012

curl -X DELETE http://localhost:8082/topjava/rest/meals/100008
