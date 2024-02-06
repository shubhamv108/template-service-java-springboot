locals {
  tags = {
    Project     = var.project_name
    CreatedBy   = var.createdBy
    CreatedOn   = timestamp()
    Environment = terraform.workspace
  }
}

module "pipeline_aws_s3_buckets" {
  source = "./modules/aws/s3/buckets"
}