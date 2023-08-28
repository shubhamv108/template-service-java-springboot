SHELL := /bin/bash
OS := $(shell uname)

define start-services
	@docker-compose -f compose.yaml up --force-recreate -d --remove-orphans mysql kafka
endef

define start-app
	@docker-compose -f compose.yaml up -d app
endef

define teardown
	@docker-compose -f compose.yaml rm -f -v -s
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
	@kubectl apply -f ./k8s/mysql.yaml
    @kubectl apply -f ./k8s/kafka.yaml
    @kubectl apply -f ./k8s/app.yaml
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

local-setup: teardown
	$(call local-setup)

setup: teardown build
	$(call setup, "Setting up...")

start-services:
	$(call start-services)

local-app: build-local
	$(call local-app)

teardown:
	$(call teardown, "Tearing down...")

run-test:
	@docker-compose -f compose.yaml up --build --force-recreate -d service

migrations:

format:
	./gradlew googleJavaFormat
	./gradlew verifyGoogleJavaFormat

install:


clean:
	./gradlew clean
	clear

build-local:
	./gradlew clean
	./gradlew build

build: clean build-local
	docker build -t shubham01/server-sent-events:latest .

run-local: build-local
	./gradlew bootRun

run: build
	docker run -p 8080:8080 shubham01/server-sent-events:latest --network="host"

k8s-apply:
	$(call k8s-apply)