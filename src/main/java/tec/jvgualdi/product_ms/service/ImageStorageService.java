package tec.jvgualdi.product_ms.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tec.jvgualdi.product_ms.exceptions.StorageException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class ImageStorageService {

    private final AmazonS3 s3;
    private final String bucket;

    public ImageStorageService(AmazonS3 s3,
                               @Value("${aws.s3.bucket}") String bucket) {
        this.s3 = s3;
        this.bucket = bucket;
    }

    public List<String> uploadAll(List<MultipartFile> files) {
        if (files == null) return List.of();
        return files.stream()
                .map(this::uploadOne)
                .toList();
    }

    public String uploadOne(MultipartFile file) {
        String key = UUID.randomUUID() + "-" + file.getOriginalFilename();
        try (InputStream in = file.getInputStream()) {
            s3.putObject(bucket, key, in, buildMetadata(file));
        } catch (IOException e) {
            throw new StorageException("Failed to send " + file.getOriginalFilename(), e);
        }
        return s3.getUrl(bucket, key).toString();
    }

    private ObjectMetadata buildMetadata(MultipartFile file) {
        ObjectMetadata md = new ObjectMetadata();
        md.setContentLength(file.getSize());
        md.setContentType(file.getContentType());
        return md;
    }

}
