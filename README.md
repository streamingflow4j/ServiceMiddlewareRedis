# ServiceMiddlewareRedis

## Open a new terminal: 
redis-cli
SUBSCRIBE si.ceprule.queue

## Open a new terminal: 
SUBSCRIBE si.cep.queue


## Open a new terminal: 
redis-cli

PUBLISH si.ceprule.queue "{\"type\" : \"ADD_EVENT\", \"id\" : \"Termometer\", \"attributes\" : [{ \"name\"  : \"id\", \"type\"  : \"String\", \"value\" : \"0\" },{\"name\" : \"temperature\", \"type\"  : \"Double\", \"value\" : \"0\" }]}"

PUBLISH si.ceprule.queue "{\"type\" : \"RULECEP\", \"id\" : \"Rule7\", \"attributes\" : [{ \"name\"  : \"RULE\", \"type\"  : \"String\", \"value\" : \"select temperature from Termometer.win:time(5 sec)\" },{ \"name\"  : \"QUEUE_1\", \"type\"  : \"QUEUE\", \"value\" : \"si.cep.queue\" }]}"

PUBLISH si.test.queue "{\"type\" : \"Termometer\",\"id\" : \"1\", \"attributes\" : [{ \"name\" : \"temperature\", \"type\" : \"Double\", \"value\" : \"2\"}]}"
