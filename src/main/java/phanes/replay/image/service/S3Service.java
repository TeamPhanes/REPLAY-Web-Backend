package phanes.replay.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    public String uploadImage(String bucket, String key, MultipartFile image) throws IOException {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .contentType(image.getContentType())
                        .build(),
                RequestBody.fromInputStream(image.getInputStream(), image.getSize())
        );

        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, s3Client.serviceClientConfiguration().region(), key);
    }
}
