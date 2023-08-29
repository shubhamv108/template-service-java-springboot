PASSWORD=$(kubectl get secret elasticsearch-es-elastic-user -o go-template='{{.data.elastic | base64decode}}' -n es)
curl -u "elastic:$PASSWORD" -k "https://localhost:9200"