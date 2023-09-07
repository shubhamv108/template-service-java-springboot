package code.shubham.commons.exceptions;

public class AWSS3BlobStoreException extends BlobStoreException {
    public AWSS3BlobStoreException(String region, String operation, Exception exception) {
        super("aws", region, operation, exception);
    }
}
