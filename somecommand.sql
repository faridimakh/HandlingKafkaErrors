kafka-topics --bootstrap-server localhost:9092 --describe --topic db-topic

kafka-topics --bootstrap-server localhost:9092 --delete --topic db-topic

kafka-topics --bootstrap-server localhost:9092 --list



good message(postman):
{"uuid":"farim","firstName":"farid","lastName":"imakh","age":35,"loc":{"lat":48.856613,"lgt":2.6547}}

bad messages(must saved on another topic named bad_messsages_toDLQ):
{"uuid":"bad_age","firstName":"iuiui","lastName":"ggtr","age":-32,"loc":{"lat":48.856613,"lgt":2.6547}}
{"uuid":"bad_lat","firstName":"iuiui","lastName":"ggtr","age":32,"loc":{"lat":-48.856613,"lgt":2.6547}}
{"uuid":"bad_lgt","firstName":"iuiui","lastName":"ggtr","age":32,"loc":{"lat":48.856613,"lgt":-2.6547}}

mal formed message (deserialization problem)[kafka-console-producer]:
kafka-console-producer --broker-list localhost:9092 --topic test_messsages --property "parse.key=true" --property "key.separator=:"
bad_age:{"uuid":"bad_lgt","firstName":"iuiui","lastName":"ggtr","age":xyzt32,"loc":{"lat":48.856613,"lgt":2.6547}}
bad_lat:{"uuid":"bad_lat","firstName":"iuiui","lastName":"ggtr","age":32,"loc":{"lat":xyz48.856613,"lgt":2.6547}}
bad_lat:{"uuid":"bad_lat","firstName":"iuiui","lastName":"ggtr","age":32,"loc":{"lat":48.856613,"lgt":xyz2.6547}}

kafka-run-class kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic notConformvalues_data_DLQ --time -1
kafka-console-consumer --bootstrap-server localhost:9092 --topic notConformvalues_data_DLQ --from-beginning

