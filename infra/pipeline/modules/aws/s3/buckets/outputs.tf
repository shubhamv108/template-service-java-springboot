output "aws_code_artifact_s3_bucket_id" {
  value       = aws_s3_bucket.private-code-artifacts.id
  description = "aws_code_artifact_s3_bucket_id"
}