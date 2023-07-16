package com.app.util;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.multipart.MultipartFile;

import com.app.config.Variables;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import io.imagekit.sdk.utils.Utils;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class ImageKitManager {

  private ImageKit imageKit;
  @Autowired
  Variables variables;

  public ImageKitManager() {
    imageKit = ImageKit.getInstance();
  }

  public String uploadImage(MultipartFile file) throws Exception {

    String publickey = variables.getPublicKey();
    String privateKey = variables.getPrivateKey();
    String urlEndPoint = variables.getUrlEndpoint();
    Configuration config = new Configuration(publickey,
        privateKey, urlEndPoint);

    imageKit.setConfig(config);

    byte[] bytesImg = file.getBytes();

    String fileName = file.getOriginalFilename();
    String imageBase64 = Utils.bytesToBase64(bytesImg);

    FileCreateRequest fileCreateRequest = new FileCreateRequest(imageBase64,
        fileName);
    Result result = imageKit.upload(fileCreateRequest);

    return result.getUrl();

  }

  // creating a bean for imagekit:
  @Bean
  public ImageKitManager ikm() {
    ImageKitManager ikm = new ImageKitManager();
    return ikm;
  }
}