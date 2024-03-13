#!/bin/sh
echo "Initializing localstack - sqs"
awslocal sqs create-queue --queue-name template-service-java-springboot-queue --region=ap-south-1
awslocal sqs create-queue --queue-name template-service-java-springboot-dlq --region=ap-south-1