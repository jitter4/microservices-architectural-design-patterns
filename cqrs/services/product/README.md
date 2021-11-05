### Requests
#### POST
curl --request POST --header 'Content-Type: application/json' http://localhost:8008/products --data '{"name": "test", "price": 1.0, "quantity": 1}'

#### GET
curl --request GET --header 'Content-Type: application/json' http://localhost:8008/products