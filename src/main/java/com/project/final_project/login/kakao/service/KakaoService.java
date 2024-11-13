package com.project.final_project.login.kakao.service;

import com.project.final_project.login.kakao.domain.KakaoLoginLog;
import com.project.final_project.login.kakao.dto.KakaoDTO;
import com.project.final_project.login.kakao.repository.KakaoLoginLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class KakaoService {

  @Value("${kakao.client.id}")
  private String KAKAO_CLIENT_ID;

  @Value("${kakao.client.secret}")
  private String KAKAO_CLIENT_SECRET;

  @Value("${kakao.redirect.url}")
  private String KAKAO_REDIRECT_URL;

  private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
  private final static String KAKAO_API_URI = "https://kapi.kakao.com";

  private final KakaoLoginLogRepository kakaoLoginLogRepository;

  @Autowired
  public KakaoService(KakaoLoginLogRepository kakaoLoginLogRepository) {
    this.kakaoLoginLogRepository = kakaoLoginLogRepository;
  }

  public String getKakaoLogin() {
    return KAKAO_AUTH_URI + "/oauth/authorize"
        + "?client_id=" + KAKAO_CLIENT_ID
        + "&redirect_uri=" + KAKAO_REDIRECT_URL
        + "&response_type=code";
  }

  public KakaoDTO getKakaoInfo(String code, String state) throws Exception {
    if (code == null) throw new Exception("Failed get authorization code, need code");

    String accessToken = "";
    String refreshToken = "";

    try {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-type", "application/x-www-form-urlencoded");

      MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
      params.add("grant_type"   , "authorization_code");
      params.add("client_id"    , KAKAO_CLIENT_ID);
      params.add("client_secret", KAKAO_CLIENT_SECRET);
      params.add("code"         , code);
      params.add("redirect_uri" , KAKAO_REDIRECT_URL);

      //RestTemplate을 이용해 https://kauth.kakao.com/oauth/token 주소로 토큰을 요청
      RestTemplate restTemplate = new RestTemplate();
      HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

      ResponseEntity<String> response = restTemplate.exchange(
          KAKAO_AUTH_URI + "/oauth/token",
          HttpMethod.POST,
          httpEntity,
          String.class
      );

      JSONParser jsonParser = new JSONParser();
      JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

      accessToken  = (String) jsonObj.get("access_token");
      refreshToken = (String) jsonObj.get("refresh_token");

      addKakaoLoginLog(state, accessToken);

    } catch (Exception e) {
      throw new Exception("API call failed");
    }

    return gettUserInfoWithToken(accessToken, state);
  }

  // 액세스 토큰을 정상적으로 발급받았다면 이제 사용자 정보를 가져올 수 있습니다. 이 때 Header에 Bearer 액세스 토큰 을 담아 요청
  private KakaoDTO gettUserInfoWithToken(String accessToken, String state) throws Exception {
    //HttpHeader 생성
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    //HttpHeader 담기
    RestTemplate rt = new RestTemplate();
    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
    ResponseEntity<String> response = rt.exchange(
        KAKAO_API_URI + "/v2/user/me",
        HttpMethod.POST,
        httpEntity,
        String.class
    );

    //Response 데이터 파싱
    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObj    = (JSONObject) jsonParser.parse(response.getBody());
    JSONObject account = (JSONObject) jsonObj.get("kakao_account");
    JSONObject profile = (JSONObject) account.get("profile");

    Integer id = (Integer) jsonObj.get("id");
    String email = String.valueOf(account.get("email"));
    String nickname = String.valueOf(profile.get("nickname"));

    return KakaoDTO.builder()
        .id(id)
        .email(email)
        .nickname(nickname).build();
  }

  public void addKakaoLoginLog(String state, String accessToken) {

    KakaoLoginLog kakaoLoginLog = new KakaoLoginLog();
    kakaoLoginLog.setState(state);
    kakaoLoginLog.setAccessToken(accessToken);

    kakaoLoginLogRepository.save(kakaoLoginLog);
  }

  // 로그아웃 API
  public String logout(String accessToken) throws Exception {
    try {
      // HttpHeader 생성
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Bearer " + accessToken);

      // HttpEntity 생성
      HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

      // RestTemplate을 이용해 카카오 로그아웃 API 호출
      RestTemplate rt = new RestTemplate();
      ResponseEntity<String> response = rt.exchange(
          "https://kapi.kakao.com/v1/user/logout",  // 카카오 로그아웃 API 엔드포인트
          HttpMethod.POST,
          httpEntity,
          String.class
      );

      // 로그아웃 성공 여부 확인
      if (response.getStatusCode().is2xxSuccessful()) {
        return "Successfully logged out from Kakao";
      } else {
        throw new Exception("Failed to logout from Kakao");
      }
    } catch (Exception e) {
      throw new Exception("API call failed: " + e.getMessage());
    }
  }

  // 연동 끊기 API
  public String unlink(String accessToken) throws Exception {
    try {
      // HttpHeader 생성
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Bearer " + accessToken);

      // HttpEntity 생성
      HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

      // RestTemplate을 이용해 카카오 연동 끊기 API 호출
      RestTemplate rt = new RestTemplate();
      ResponseEntity<String> response = rt.exchange(
          "https://kapi.kakao.com/v1/user/unlink",  // 카카오 연동 끊기 API 엔드포인트
          HttpMethod.POST,
          httpEntity,
          String.class
      );

      // 연동 끊기 성공 여부 확인
      if (response.getStatusCode().is2xxSuccessful()) {
        return "Successfully unlinked Kakao account";
      } else {
        throw new Exception("Failed to unlink Kakao account");
      }
    } catch (Exception e) {
      throw new Exception("API call failed: " + e.getMessage());
    }
  }
}
