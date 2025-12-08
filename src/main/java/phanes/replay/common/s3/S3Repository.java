package phanes.replay.common.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.config.properties.S3Properties;
import phanes.replay.exception.UploadFailException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Slf4j
@Repository
@RequiredArgsConstructor
public class S3Repository {

    private final S3Client s3Client;
    private final S3Properties s3Properties;

    public String uploadImage(String key, MultipartFile image) {
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(s3Properties.getBucket())
                            .key(key)
                            .contentType(image.getContentType())
                            .build(),
                    RequestBody.fromInputStream(image.getInputStream(), image.getSize())
            );
        } catch (IOException e) {
            throw new UploadFailException("image upload failed", e);
        }
        addCallbackWhenRollback(key);
        return String.format("%s/%s/%s", s3Properties.getEndpoint(), s3Properties.getBucket(), key);
    }

    private void addCallbackWhenRollback(String key) {
        if(TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCompletion(int status) {
                    if(status == STATUS_ROLLED_BACK) {
                        try {
                            deleteImage(key);
                        } catch (Exception e) {
                            log.error("롤백 중 이미지 삭제 실패. key={}", key, e);
                        }
                    }
                }
            });
        }
    }

    public void deleteImage(String key) {
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(s3Properties.getBucket()).key(key).build());
    }
}