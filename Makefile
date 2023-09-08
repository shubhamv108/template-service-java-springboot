SHELL := /bin/bash
OS := $(shell uname)

define start-services
	@docker-compose -f compose.yaml up --force-recreate -d --remove-orphans sonar fluentbit mysql kafka kafdrop elasticsearch prometheus grafana telegraf influxdb
endef

define check
	@docker-compose -f sonar-compose.yaml up --force-recreate -d --remove-orphans sonar-db sonar
endef

define start-app
	@docker-compose -f compose.yaml up -d app
endef

define teardown
	@docker-compose -f compose.yaml rm -f -v -s
	@docker system prune -f --volumes
endef

define check-teardown
	@docker-compose -f sonar-compose.yaml rm -f -v -s
	@docker system prune -f --volumes
endef

define local-setup
	$(call start-services)
endef

define local-app
	$(call start-app)
endef

define setup
	@docker-compose -f compose.yaml up -d --build --force-recreate --remove-orphans
endef

define k8s-apply
	sudo /bin/bash scripts/k8s/apply.sh
endef

define k8s-delete-app
    @kubectl delete -f ./k8s/app.yaml
endef

define del-local-app
    @docker stop server-sent-events
    @docker rm server-sent-events
endef

.PHONY: help install setup teardown

help:
	@echo "######### Targets ##########"
	@echo "help: Display the help menu"
	@echo "install: Display the help menu"
	@echo "local-setup: Setup test resources locally"
	@echo "local-app: Setup test resources locally"
	@echo "format: Formats the java codebase"
	@echo "setup: Setup test resources"
	@echo "teardown: Destroy test resources"
	@echo "start-services: Start depdent services for testing"
	@echo "test: Run tests in local"
	@echo "run-test: Run specfic test"
	@echo "############################"

check:
	./gradlew sonar

check-teardown:
	$(call check-teardown)

local-setup: teardown
	$(call local-setup)

setup: teardown build
	$(call setup, "Setting up...")

start-services:
	$(call start-services)

teardown:
	$(call teardown, "Tearing down...")

run-test:
	@docker-compose -f compose.yaml up --build --force-recreate -d service

migrations:

format:
	./gradlew format

install: setup

clean:
	clear
	./gradlew clean

build-local: clean
	./gradlew build

rm-images: clean
	docker image rm shubham01/sse-fluentbit
	docker image rm shubham01/server-sent-events
	docker image rm server-sent-events

docker-build:
	docker build -t shubham01/server-sent-events:latest .
	docker build -t shubham01/sse-fluentbit:latest fluentbit

build: clean build-local docker-build

rebuild: rm-images build

run-local: build-local
	./gradlew bootRun

run: build
	docker run -p 8080:8080 shubham01/server-sent-events:latest --network="host"
	docker run -p 24224:24224 shubham01/ssse-fluentbit:latest --network="host"

k8s-apply:
	$(call k8s-apply)

k8s-delete-app:
	$(call k8s-delete-app)

del-local-app:
	$(call del-local-app)

local-app: build
	$(call local-app)

local-app-re: del-local-app local-app

coverage:
	./gradlew jacocoTestCoverageVerification