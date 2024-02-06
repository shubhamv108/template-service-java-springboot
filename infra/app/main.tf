locals {
  tags = {
    Project     = var.project_name
    CreatedBy   = var.createdBy
    CreatedOn   = timestamp()
    Environment = terraform.workspace
  }
}

module "app_aws_s3_buckets" {
  source = "./modules/aws/s3/buckets"
}

#module "project_aws_cloudfront_distribution" {
#  source = "./modules/aws/cloudfront/distribution"
#}
