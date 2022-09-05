# Microservices
Project demonstrating microservices

## Ordering service
The ordering service allows you to place orders in the system. It will check inventory availability and send paid orders to the shipping queue.

## Inventory Service
The inventory service checks existing inventory if a product is currently in our inventory.

## Shipping Service
The shipping service takes shipping requests off the shipping queue and ships them.

## Running the project
### Locally hosted
1. In the root directory run `docker-compose up`
1. Navigate to localhost:5050 and create a new postgres server with username `dirkadin` and password `password` (You can change these in the config files of the services if you wish).
1. Inside the database created the `ordering` database
1. Start the eureka server
1. Start the ordering service with the included intellij run config.
1. Start the inventory service with a default config
1. Using postman (or any other similar program) to send a request to `localhost:8083/api/v1/order/placeorder` with the body

```json
{
    "productId": 1,
    "quantity": 1,
    "emailAddress": "foo@bar.com"
}
```

### Using docker only
1. In the root directory run `docker-compose up`
1. Navigate to localhost:5050 and create a new postgres server with username `dirkadin` and password `password`. You can change these in the config files of the services if you wish, but you will need to modify the docker config files.
1. Inside the database created the `ordering` database
1. Using postman (or any other similar program) to send a request to `localhost:8083/api/v1/order/placeorder` with the body

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

# TODO
~~1. Create small microservices~~
~~1. Connect to eureka~~
~~1. Add tracing~~
~~1. Add API Gateway~~
~~1. Containerize all services~~
1. Move services to k8s
1. Deploy somewhere?