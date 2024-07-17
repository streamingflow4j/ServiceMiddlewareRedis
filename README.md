# ServiceMiddlewareRedis

## After Run the service Middleware
1. Open a new terminal: 
```
redis-cli
SUBSCRIBE si.ceprule.queue
```
2. Open a new terminal: 
```
redis-cli
SUBSCRIBE si.cep.queue
```
3. Open a new terminal:
   
- redis-cli:
  
a) Create Event
```
PUBLISH si.ceprule.queue "{\"type\" : \"EVENT_CREATE\", \"id\" : \"Termometer\", \"attributes\" : [{ \"name\"  : \"id\", \"type\"  : \"String\", \"value\" : \"0\" },{\"name\" : \"temperature\", \"type\"  : \"Double\", \"value\" : \"0\" }]}"
```
b) Create Rule
```
PUBLISH si.ceprule.queue "{\"type\" : \"RULE_CREATE\", \"id\" : \"Rule7\", \"attributes\" : [{ \"name\"  : \"RULE\", \"type\"  : \"String\", \"value\" : \"select temperature from Termometer.win:time(5 sec)\" },{ \"name\"  : \"QUEUE_1\", \"type\"  : \"QUEUE\", \"value\" : \"si.cep.queue\" }]}"
```
c) Produce Data 
```
PUBLISH si.test.queue "{\"type\" : \"Termometer\",\"id\" : \"1\", \"attributes\" : [{ \"name\" : \"temperature\", \"type\" : \"Double\", \"value\" : \"2\"}]}"# ServiceMiddlewareRedis
```
