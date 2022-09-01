package com.example.TimeclockAWS.S3Services;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class S3Utils {
  private static final String BUCKET="timeclockapplication";
  private static S3Client s3Client = S3Client.builder().region(Region.US_EAST_1).build();

  public static String getURL(String keyName, String userFolder) {
    keyName = userFolder+"/"+keyName;
    System.out.println("keyname and folder from geturl method"+keyName);
    try {
      GetUrlRequest request = GetUrlRequest.builder()
        .bucket(BUCKET)
        .key(keyName)
        .build();

      URL url = s3Client.utilities().getUrl(request);
      System.out.println("The URL for  "+keyName +" is "+ url);
      return url.toString();

    } catch (S3Exception e) {
      System.err.println(e.awsErrorDetails().errorMessage());
      System.exit(1);
    }
    return null;
  }


  public static void uploadFile(String fileName, InputStream inputStream) throws IOException {

    PutObjectRequest request = PutObjectRequest.builder()
      .bucket(BUCKET)
      .key(fileName)
    //  .acl("public-read")
      .build();

    s3Client.putObject(request, RequestBody.fromInputStream(inputStream,inputStream.available()));
  }
}
