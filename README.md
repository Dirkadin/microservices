# Microservices
Project demonstrating microservices

## Ordering service
The ordering service allows you to place an order with just with a post request.

## Inventory Service
The inventory service checks existing inventory if a product is currently in our inventory.

## Running the project
1. In the root directory run `docker-compose up`
2. Navigate to localhost:5050 and setup a new postgres database with username `dirkadin` and password `password` (You can change these in the config files of the services if you wish).
3. Inside the database created the `ordering` table
4. Start the eureka server
5. Start the ordering service with the included intellij run config.
6. Start the inventory service with a default config
7. Using postman (or any other similar program) to send a request to `localhost:8080/api/v1/order/placeorder` with the body

```json
{
    "productId": 1,
    "quantity": 1,
    "emailAddress": "foo@bar.com"
}
```

You should receive a 201 created back with the original payload.

### Notes:
- Currently, the default behaviour is to drop all tables on exit and recreated them on startup. Do not expect data to persist.
- The inventory service randomly returns a quantity between 1 and 10.