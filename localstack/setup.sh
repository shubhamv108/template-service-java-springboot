#!/bin/sh
echo "Initializing localstack - SQS"
awslocal sqs create-queue --queue-name template-service-java-springboot-queue --region=ap-south-1
awslocal sqs create-queue --queue-name template-service-java-springboot-dlq --region=ap-south-1

echo "Initializing localstack - SNS"
awslocal sns create-topic --name template-service-java-springboot-topic --region=ap-south-1