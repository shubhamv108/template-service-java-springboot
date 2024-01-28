[SERVICE]
    flush 1
    log_level info

[INPUT]
    Name forward
    Listen 0.0.0.0
    port 24224

[FILTER]
	name         	parser
	match        	*
	key_name     	log
	parser       	java_multi

[OUTPUT]
    Name stdout
    Match *

[OUTPUT]
    Name es
    Match **
    Host 127.0.0.1
    Port 9243
    # When Logstash_Format is enabled, the Index name is composed using a prefix and the date
    Logstash_Format True
    # HTTP_User <account>
    # HTTP_Passwd <pw>
    # Alternative time key, useful if your log entries contain an @timestamp field that is used by Elasticsearch
    # Time_Key es_time
    # If your Elasticsearch is using TLS, configure this
    # tls On
    # tls.verify Off