package com.project.final_project.gallery.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class GalleryService {

  @Value("${cloud.aws.s3.bucket}")
  private String BUCKET;
  private final AmazonS3 amazonS3;

  public void upload(MultipartFile file, String filename) {
    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(file.getContentType());
      metadata.setContentLength(file.getSize());
      amazonS3.putObject(BUCKET + "/post", filename, file.getInputStream(), metadata);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void delete(String filename) {
    DeleteObjectRequest request = new DeleteObjectRequest(BUCKET + "/post", filename);
    amazonS3.deleteObject(request);
  }

  public URL getUrl(String fileName) {
    URL url = amazonS3.getUrl(BUCKET + "/post", fileName);
    return url;
  }
}
