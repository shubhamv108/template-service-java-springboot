resource "aws_s3_bucket" "document" {
  bucket = "documentssstore"

  tags = {
    Name        = "documentssstore"
    Owner       = "${var.project_name}"
    Environment = "${var.environment}"
    Region      = "${var.region}"
  }
}

resource "aws_s3_bucket_policy" "documentssstore_allow_access_from_cloudfront" {
  bucket = aws_s3_bucket.document.id
  policy = data.aws_iam_policy_document.documentssstore_allow_access_from_cloudfront.json
}

data "aws_iam_policy_document" "documentssstore_allow_access_from_cloudfront" {
  statement {
    principals {
      type        = "Service"
      identifiers = ["cloudfront.amazonaws.com"]
    }

    actions = [
      "s3:GetObject",
    ]

    resources = [
      aws_s3_bucket.document.arn,
      "${aws_s3_bucket.document.arn}/*",
    ]

    condition {
      test     = "StringEquals"
      values   = ["arn:aws:cloudfront::430689894701:distribution/E2ZI5IWQWTVER"]
      variable = "AWS:SourceArn"
    }
  }
}