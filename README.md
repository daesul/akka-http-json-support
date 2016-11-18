# akka-http-json-support

##circe-json
Contains sources using circe.

##spray-json
Constains sources using spray json.

##Examples
Examples using CURL with GET and POST

###GET

```
curl http://localhost:8080/customer/5919d228-9abf-11e6-9f33-a24fc0d9649c
```

###POST
```
curl -H 'Content-Type: application/json' 'localhost:8080/customer' -d '{
  "name": "test1",
  "customerType": "VIP",
  "id": "3958b7ce-a280-11e6-80f5-76304dec7eb7",
  "registrationDate": "2010-01-11",
  "gender": "FEMALE"
}'
```
