# StreamingFlow4J: Event Driven Framework with inference engine for Complex Event Processing

## 🤔 What is it used for? 
Add Complex Event Processing with Event-Driven, CQRS, Message Route to Java applications.

## About this project (ServiceMiddlewareRedis)
This project is a contextual middleware made with java, Spring Boot, Redis and EsperCEP.

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
PUBLISH si.ceprule.queue "{\"type\" : \"EVENT_CREATE\", \"id\" : \"Thermometer\", \"attributes\" : [{ \"name\"  : \"id\", \"type\"  : \"String\", \"value\" : \"0\" },{\"name\" : \"temperature\", \"type\"  : \"Double\", \"value\" : \"0\" }]}"
```
b) Create Rule
```
PUBLISH si.ceprule.queue "{\"type\" : \"RULE_CREATE\", \"id\" : \"Rule7\", \"attributes\" : [{ \"name\"  : \"RULE\", \"type\"  : \"String\", \"value\" : \"select temperature from Thermometer.win:time(5 sec)\" },{ \"name\"  : \"QUEUE_1\", \"type\"  : \"QUEUE\", \"value\" : \"si.cep.queue\" }]}"
```
c) Produce Data 
```
PUBLISH si.test.queue "{\"type\" : \"Thermometer\",\"id\" : \"1\", \"attributes\" : [{ \"name\" : \"temperature\", \"type\" : \"Double\", \"value\" : \"2\"}]}"# ServiceMiddlewareRedis
```

## ⭐ Give us a star!

Like what you see? Please consider giving this a star (★)!

## 🏷️ License and Citation

The code is available under Apache License.
If you find this project helpful in your research, please cite this work at

```
@misc{sf4j2019,
    title = {StreamingFlow4J: A modern Java Event Driven CEP Framework for Microservices},
    url = {https://github.com/streamingflow4j},
    author = {H Diniz},
    month = {January},
    year = {2019}
}
```
