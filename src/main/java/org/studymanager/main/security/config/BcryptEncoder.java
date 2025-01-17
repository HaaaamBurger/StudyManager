package org.studymanager.main.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class is needed to create
 * configuration of BcryptEncoder.
 *
 * @author Melnyk Oleksandr
 * @version 1.0
 */
@Configuration
public class BcryptEncoder {
  /**
   * This method creates Bean of BCryptPasswordEncoder.
   *
   * @return PasswordEncoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
