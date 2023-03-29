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
2. Navigate to localhost:5050 and create a new postgres server with username `dirkadin` and password `password` (You can change these in the config files of the services if you wish).
3. Inside the database create the `ordering` database and `shipping` database.
4. Start the eureka server
5. Start the API Gateway
6. Start the ordering service with the included intellij run config.
7. Start the inventory service with a default config
8. Using postman (or any other similar program) to send a POST request to `localhost:8083/api/v1/order/placeorder` with the body

```json
{
    "productId": 1,
    "quantity": 1,
    "emailAddress": "foo@bar.com"
}
```
9. Send the request and if everything is set up correctly, you will get a 201 response back.

### Using docker only
1. In the root directory run `docker-compose up`
2. Navigate to localhost:5050 and create a new postgres server with username `dirkadin` and password `password`. You can change these in the config files of the services if you wish, but you will need to modify the docker config files.
3. Inside the database created the `ordering` database
4. Using postman (or any other similar program) to send a request to `localhost:8083/api/v1/order/placeorder` with the body

   ```json
   {
       "productId": 1,
       "quantity": 1,
       "emailAddress": "foo@bar.com"
   }
   ```
5. Send the request and if everything is set up correctly, you will get a 201 response back.

### Using Minikube/Kubernetes
1. Start Minikube`minikube start --memory=4G`
2. Move into dev folder `cd /kubernetes/dev`
3. Apply bootstrap services `kubectl apply -R -f bootstrap/`
4. From the terminal `kubectl exec -it postgres-0 -- psql -U dirkadin`
5. Now inside the postgres container:
   1. `create database ordering;`
   2. `create database shipping;`
   3. `exit`
6. Deploy services `kubectl apply -R -f services/`
7. Get ordering service URL `minikube serivce --url ordering`
8. In postman, create a post request to the url from the previous step. `<url>/api/v1/order/placeorder`
9. Add the body:
   ```json
   {
    "productId": 1,
    "quantity": 1,
    "emailAddress": "foo@bar.com"
   }
   ```
10. Send the request and if everything is set up correctly, you will get a 201 response back.

#### Using the Kubernetes Dashboard
1. Run `kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.6.1/aio/deploy/recommended.yaml`
2. Navigate to root/kubernetes/dev
3. Run `kubectl apply -R -f dashboard/` to create the admin-user
4. Once deployment is finished, run `kubectl -n kubernetes-dashboard create token admin-user`
5. Copy the token
6. Navigate to localhost:8081 and authenticate with the token
7. If something is broken, check here for help `https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/`

### Using Github Codespaces
- TODO

### Notes:
- Currently, the default behaviour is to drop all tables on exit and recreated them on startup. Do not expect data to persist.
- The inventory service randomly returns a quantity between 1 and 10.

# TODO
1. Create small microservices ✅️
1. Connect to eureka✅ ✅️
1. Add tracing ✅️
1. Add API Gateway ✅️
1. Containerize all services ✅️
1. Move services to k8s ✅️
1. Deploy somewhere?