package phanes.replay.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "s3")
public class S3Properties {

    private String bucket;
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
