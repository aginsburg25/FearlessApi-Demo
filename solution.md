#Fearless API Take Home Test
###Background
This API consists of different operations to add, delete, and search the Fearlerss-inventory Database

###Rest Endpoints
`GET /items/getCurrentItems` will return all current Items in DB <br />
`GET /items/getItem/{id}` will accept id in path variable and return that item <br />
`POST /items/setItems` will accept a List of Items to set as current items in DB <br />
`POST /items/saveItem` will accept an Item and save it in the DB <br />
`DELETE /items/removeAll` will delete all items in the DB <br />
`DELETE /items/deleteItem/{id}` will take id in path variable and delete that item <br />


###Build and Run Code
run following commands: <br />
mvn clean install package -DskipTests <br />
docker-compose up <br />

**if seeing issue of receiving 404's from endpoints I was not able to resolve this issue in 2-hour time slot, and should stop the main container and run the spring boot application separately ( to see the API function)

- to kill container docker container kill < container name>
- to run spring boot app: mvn spring-boot:run
- leave sql container running

###Further Enhancements
- I would need to solve issue of docker-compose network issue of not exposing the endpoints
- With more time allotted would add unit tests
- Could add more endpoints for more operations
- Add front end for easier use to visualize DB and customer input instead of postman http requests
- For real-world use would add authentication for accessing endpoints

