package com.bit.auction.common;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bit.auction.configuration.NaverConfiguration;
import com.bit.auction.goods.dto.AuctionImgDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class FileUtils {
    private final AmazonS3 s3;
    @Value("${ncp.accessKey}")
    private String accessKey;

    @Value("${ncp.bucket}")
    private String bucketName;

    public FileUtils(NaverConfiguration naverConfiguration) {
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

    public AuctionImgDTO parseFileInfo(MultipartFile multipartFile, String directory, String representativeImgName) {
        AuctionImgDTO auctionImgDTO = new AuctionImgDTO();

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


        auctionImgDTO.setFileName(imgName);
        auctionImgDTO.setFileUrl("https://kr.object.ncloudstorage.com/"+bucketName+"/" + imgPath);
        if (auctionImgOrigin.equals(representativeImgName)) {
            auctionImgDTO.setRepresentative(true);
        }

        return auctionImgDTO;
    }

    public void deleteObject(String url) {
        s3.deleteObject(new DeleteObjectRequest(bucketName, url));
    }
}
