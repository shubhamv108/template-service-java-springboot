variable "region" {
  type        = string
  description = "AWS region for all resources."

  default = "ap-south-1"
}

variable "project_name" {
  type        = string
  description = "Template service in java, spring-boot project."

  default = "template-service-java-spring-boot"
}

variable "environment" {
  type        = string
  description = "Template service in java, spring-boot project."

  default = "test"
}

variable "s3_bucket_paste" {
  type        = string
  description = "Template service in java, spring-boot project."
  default = "private-pastesss"
}
