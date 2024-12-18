package com.project.final_project.common.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

  private final Dotenv dotenv;

  public CloudinaryConfig() {
    dotenv = Dotenv.load(); // .env 파일 로드
  }

  @Bean
  public Cloudinary cloudinary() {
    return new Cloudinary(ObjectUtils.asMap(
        "cloud_name", dotenv.get("CLOUDINARY_CLOUD_NAME"),
        "api_key", dotenv.get("CLOUDINARY_API_KEY"),
        "api_secret", dotenv.get("CLOUDINARY_API_SECRET")
    ));
  }
}
