FROM newrelic/infrastructure:latest
LABEL authors="Shubham Varshney"
LABEL maintainer="Shubham Varshney"

ADD newrelic-infra/newrelic-infra.yml /etc/newrelic-infra.yml
ADD newrelic-infra/execute.sh /tmp/execute.sh

RUN chmod 777 /tmp/execute.sh

ENTRYPOINT "/bin/bash /tmp/execute.sh"