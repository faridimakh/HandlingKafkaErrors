# Handling Kafka Errors

## discription :
the project contains two independent modules:
1. the kafkaProducer Service module takes care of creating topics and writing it in the input topic (send the wikiChange.json file attached to the project)
2. the second streamfilterservice module takes care of ingesting in real time the data introduced in the first module and creates four output topics .
<br>
<br>

![garphe1](images/gr1.png)
## needed tools:
1. Docker (and docker-compose)
2. Java
3. Kafka
## how to use :
### 1. build services locally:
1. build services:zookeeper kafka brocker : _docker-compose up -d_
3. starts services(run main classes: _HandlingKafkaErrorsApplication)
<br>
### 2. send data:
1. send normal message or message with not conform values-> use integrated producer <br>
  
![normal_message](images/normalmessage.png)
![not_conform_value](images/not_conform_value.png)
<br>
2. send mal formed message (deserialization problem) -> use kafka-console-producer:
   -  kafka-console-producer --broker-list localhost:9092 --topic test_messsages --property "parse.key=true" --property "key.separator=:"
   -  deserializ_prob:{"uuid":"deserializ_prob","firstName":"iuiui","lastName":"ggtr","age":badage32,"loc":{"lat":48.856613,"lgt":2.6547}}


   in postmen: copy past, post method, url: http://localhost:8080/publish, send.
<br>
<br>

![garphe2](images/gr2.png)

## Examples of messages types:

* good message:<br>
{"uuid":"farim","firstName":"farid","lastName":"imakh","age":35,"loc":{"lat":48.856613,"lgt":2.6547}}

* bad messages(must saved on another topic named bad_messsages_toDLQ):<br>
.  {"uuid":"bad_age","firstName":"iuiui","lastName":"ggtr","age":-32,"loc":{"lat":48.856613,"lgt":2.6547}}<br>
.  {"uuid":"bad_lat","firstName":"iuiui","lastName":"ggtr","age":32,"loc":{"lat":-48.856613,"lgt":2.6547}}<br>
.  {"uuid":"bad_lgt","firstName":"iuiui","lastName":"ggtr","age":32,"loc":{"lat":48.856613,"lgt":-2.6547}}<br>

* mal formed message (deserialization problem)->use kafka-console-producer to push data:<br>
. bad_age:{"uuid":"bad_lgt","firstName":"iuiui","lastName":"ggtr","age":xyzt32,"loc":{"lat":48.856613,"lgt":2.6547}}<br>
. bad_lat:{"uuid":"bad_lat","firstName":"iuiui","lastName":"ggtr","age":32,"loc":{"lat":xyz48.856613,"lgt":2.6547}}<br>
. bad_lat:{"uuid":"bad_lat","firstName":"iuiui","lastName":"ggtr","age":32,"loc":{"lat":48.856613,"lgt":xyz2.6547}}<br>
