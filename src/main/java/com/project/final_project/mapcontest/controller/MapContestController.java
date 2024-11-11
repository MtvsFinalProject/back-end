package com.project.final_project.mapcontest.controller;

import static com.project.final_project.common.global.HttpResponseEntity.success;

import com.project.final_project.common.global.HttpResponseEntity.ResponseResult;
import com.project.final_project.furniture.dto.FurnitureDTO;
import com.project.final_project.mapcontest.dto.MapContestDTO;
import com.project.final_project.mapcontest.dto.MapContestRegisterDTO;
import com.project.final_project.mapcontest.service.MapContestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/map-contest")
@RequiredArgsConstructor
public class MapContestController {

  private final MapContestService mapContestService;

  /**
   * 이미지 업로드
   * @param file 업로드할 이미지 파일
   * @return 성공 메시지와 HTTP 상태 코드
   */

  @PostMapping(
      value = "/upload-image",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<String> uploadImage(@RequestPart("file") @Parameter(description = "업로드할 파일") MultipartFile file) {

    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body("파일이 비어있습니다.");
    }

    try {
      // 원본 파일의 확장자를 추출
      String originalFileName = file.getOriginalFilename();
      String extension = "";

      if (originalFileName != null && originalFileName.contains(".")) {
        extension = originalFileName.substring(originalFileName.lastIndexOf("."));
      }

      // UUID를 사용하여 랜덤 파일명 생성
      String randomFileName = UUID.randomUUID().toString() + extension;

      // 파일을 S3에 업로드
      mapContestService.upload(file, randomFileName);

      URL url = mapContestService.getUrl(randomFileName);

      return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully with name: " + url);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to upload image: " + e.getMessage());
    }
  }


  @GetMapping("/list")
  public ResponseResult<List<MapContestDTO>> getAllMapContestList() {
    return success(mapContestService.getAllFurnitureList());
  }

  @GetMapping("/furniture-list/{mapContestId}")
  public List<FurnitureDTO> getFurnitureListByMapContestId(@PathVariable("mapContestId") Integer mapContestId) {
    return mapContestService.getFurnitureListByMapContestId(mapContestId);
  }

  @Operation(summary = "맵 콘테스트에 나만의 교실 스냅샷 등록", description = "맵 콘테스트에 나만의 교실 스냅샷을 등록합니다. 이때 furniture, my_classroom에 데이터가 있어야 맵 콘테스트에 등록을 할 수가 있습니다.")
  @PostMapping
  public ResponseResult<MapContestDTO> registerMapContest(@RequestBody MapContestRegisterDTO dto){
    return success(mapContestService.registerMapContest(dto));
  }

  @DeleteMapping
  public ResponseResult<String> removeMapContest(@RequestParam("mapContestId") Integer mapContestId){
    mapContestService.removeMapContest(mapContestId);
    return success("remove success id : " + mapContestId);
  }

}
