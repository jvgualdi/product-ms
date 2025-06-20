package tec.jvgualdi.product_ms.ports;

public interface StoragePort {

    String uploadFile(byte[] fileData, String fileName, String contentType);
}
