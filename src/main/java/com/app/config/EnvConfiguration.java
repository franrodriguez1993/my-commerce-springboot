package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import lombok.Getter;

@Configuration
@Getter
@PropertySource("classpath:config.properties")
public class EnvConfiguration {

  @Autowired
  Environment env;

  @Bean
  public Variables variables() {
    Variables variables = new Variables(env.getProperty("PublicKey"), env.getProperty("PrivateKey"),
        env.getProperty("UrlEndpoint"));

    return variables;
  }

  // }

}
