kubectl create namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
kubectl apply -f k8s/mysql.yaml
kubectl apply -f k8s/kafka.yaml
kubectl apply -f k8s/kafdrop.yaml
kubectl create -f https://download.elastic.co/downloads/eck/2.9.0/crds.yaml
kubectl apply -f https://download.elastic.co/downloads/eck/2.9.0/operator.yaml
kubectl apply -f k8s/es.yaml
kubectl create namespace prometheus
helm install prometheus bitnami/kube-prometheus
kubectl apply -f k8s/app.yaml