package ufpb.ayty.config;

import io.minio.MinioClient;

public class MinIOConfig {

    private static final String ENDPOINT = "http://localhost:9000"; // URL do MinIO
    private static final String ACCESS_KEY = "minioadmin";          // Chave de acesso
    private static final String SECRET_KEY = "minioadmin";          // Chave secreta

    private static MinioClient minioClient;

    public static MinioClient getMinioClient() {
        if (minioClient == null) {
            minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();
        }
        return minioClient;
    }
}
