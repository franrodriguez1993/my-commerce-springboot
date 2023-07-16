package com.app.config;

public class Variables {
  private String publicKey;
  private String privateKey;
  private String urlEndpoint;

  public String getPublicKey() {
    return publicKey;
  }

  public String getPrivateKey() {
    return privateKey;
  }

  public String getUrlEndpoint() {
    return urlEndpoint;
  }

  public Variables(String publicKey, String privateKey, String urlEndpoint) {
    this.publicKey = publicKey;
    this.privateKey = privateKey;
    this.urlEndpoint = urlEndpoint;
  }

}
