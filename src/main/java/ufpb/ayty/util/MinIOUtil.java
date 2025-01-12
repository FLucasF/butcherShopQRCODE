package ufpb.ayty.util;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import ufpb.ayty.config.MinIOConfig;

import java.io.ByteArrayInputStream;

public class MinIOUtil {

    private static final String BUCKET_NAME = "qrcodes";

    public static String uploadQRCode(String fileName, byte[] qrCodeData) throws Exception {
        MinioClient minioClient = MinIOConfig.getMinioClient();

        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
        }

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(fileName)
                        .stream(new ByteArrayInputStream(qrCodeData), qrCodeData.length, -1)
                        .contentType("image/png")
                        .build()
        );

        return String.format("%s/%s/%s", "http://localhost:9000", BUCKET_NAME, fileName);
    }
}
