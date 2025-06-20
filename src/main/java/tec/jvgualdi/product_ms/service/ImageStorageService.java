package tec.jvgualdi.product_ms.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tec.jvgualdi.product_ms.exceptions.StorageException;
import tec.jvgualdi.product_ms.ports.StoragePort;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ImageStorageService {

    private final StoragePort storagePort;

    public ImageStorageService(StoragePort storagePort) {
        this.storagePort = storagePort;
    }

    public List<String> uploadAll(List<MultipartFile> images) {
        return images.stream()
                .map(this::uploadImage)
                .toList();
    }

    private String uploadImage(MultipartFile image) {
        if (image == null ){
            throw new StorageException("Image file cannot be null");
        }
        try {
            return storagePort.uploadFile(
                    image.getBytes(),
                    UUID.randomUUID() + "-" + image.getOriginalFilename(),
                    image.getContentType()
            );
        } catch(IOException e){
            throw new RuntimeException("Falha ao ler imagem", e);
        }
    }

}
