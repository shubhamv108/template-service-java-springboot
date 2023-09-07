package code.shubham.commons.exceptions;

public class BlobStoreException extends RuntimeException {

    private String store;
    private String region;
    private String operation;

    public BlobStoreException(String store, String region, String operation, Exception exception) {
        super(exception);
        this.store = store;
        this.region = region;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return String.format("Exception while %s in %s in Region.%s", operation, store, region);
    }
}
