package com.project.final_project.gallery.controller;

import static com.project.final_project.common.global.HttpResponseEntity.success;

import com.project.final_project.common.global.HttpResponseEntity.ResponseResult;
import com.project.final_project.gallery.service.GalleryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gallery")
public class GalleryController {

  private final GalleryService galleryService;

  /**
   * 이미지 업로드
   * @param file 업로드할 이미지 파일
   * @param fileName 이미지의 파일명
   * @return 성공 메시지와 HTTP 상태 코드
   */
  @PostMapping(
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<String> uploadImage(
      @RequestPart("file") @Parameter(description = "업로드할 파일") MultipartFile file,
      @RequestParam("fileName") String fileName) {

    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body("파일이 비어있습니다.");
    }

    try {
      galleryService.upload(file, fileName);
      return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully.");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to upload image: " + e.getMessage());
    }
  }

  @GetMapping("/{fileName}")
  public URL getUrl(@PathVariable("fileName") String fileName) {
    return galleryService.getUrl(fileName);
  }

  /**
   * 이미지 삭제
   * @param filename 삭제할 이미지의 파일명
   * @return 성공 메시지와 HTTP 상태 코드
   */
  @DeleteMapping
  public ResponseEntity<String> deleteImage(@RequestParam("filename") String filename) {
    try {
      galleryService.delete(filename);
      return ResponseEntity.status(HttpStatus.OK).body("Image deleted successfully.");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to delete image: " + e.getMessage());
    }
  }
}
