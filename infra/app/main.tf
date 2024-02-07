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

#module "app_aws_cloudfront_distribution" {
#  source = "./modules/aws/cloudfront/distribution"
#  aws_s3_bucket_id_document = module.app_aws_s3_buckets.aws_s3_bucket_id_documents
#  aws_s3_bucket_id_paste = module.app_aws_s3_buckets.aws_s3_bucket_id_paste
#}
