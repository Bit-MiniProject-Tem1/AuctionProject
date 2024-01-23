package com.bit.auction.common;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bit.auction.configuration.NaverConfiguration;
import com.bit.auction.goods.dto.DescriptionImgDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class CkEditorImageUtils {
    private final AmazonS3 s3;

    public CkEditorImageUtils(NaverConfiguration naverConfiguration) {
        s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        naverConfiguration.getEndPoint(), naverConfiguration.getRegionName()
                ))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(
                                naverConfiguration.getAccessKey(), naverConfiguration.getSecretKey()
                        )
                ))
                .build();
    }

    public DescriptionImgDTO parseFileInfo(MultipartFile multipartFile, String directory) {
        String bucketName = "bitcamp-bucket-07";

        DescriptionImgDTO descriptionImgDTO = new DescriptionImgDTO();

        String auctionImgOrigin = multipartFile.getOriginalFilename();

        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmsss");
        Date nowDate = new Date();

        String nowDateStr = formater.format(nowDate);

        UUID uuid = UUID.randomUUID();

        String imgName = nowDateStr + "_" + uuid.toString() + "_" + auctionImgOrigin;

        String imgPath = directory + imgName;

        try (InputStream fileIputStream = multipartFile.getInputStream()) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    directory + imgName,
                    fileIputStream,
                    objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            s3.putObject(putObjectRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        descriptionImgDTO.setFileName(imgName);
        descriptionImgDTO.setFileUrl("https://kr.object.ncloudstorage.com/bitcamp-bucket-122/" + imgPath);

        return descriptionImgDTO;
    }

    public String parseFileInto(MultipartFile uploadFile, String realPath) throws IOException {
        String originalFileName = uploadFile.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.indexOf("."));
        String newFileName = UUID.randomUUID() + ext;
        realPath = System.getProperty("user.dir") + "\\src\\";

        String savePath = realPath + "/main/resources/static/upload/" + newFileName;
        String uploadPath = realPath + "\\main\\resources\\static\\upload\\" + newFileName;

        Path path = Paths.get(savePath).toAbsolutePath();
        uploadFile.transferTo(path.toFile());

        return uploadPath;
    }
}
