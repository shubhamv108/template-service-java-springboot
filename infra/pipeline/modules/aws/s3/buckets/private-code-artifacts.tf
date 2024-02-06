resource "aws_s3_bucket" "private-code-artifacts" {
  bucket = "private-code-artifacts"

  tags = {
    Name        = "private-code-artifacts"
    Owner       = "${var.project_name}"
    Environment = "${var.environment}"
    Region      = "${var.region}"
  }
}