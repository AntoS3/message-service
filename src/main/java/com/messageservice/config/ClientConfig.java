package com.messageservice.config;

import com.messageservice.config.error.MessageServiceErrorDecoder;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class ClientConfig {

    @Bean
    @Primary
    public Decoder feignDecoder() {
        return new CustomDecoder(new JacksonDecoder());
    }

    @Bean
    public MessageServiceErrorDecoder customErrorDecoder() {
        return new MessageServiceErrorDecoder();
    }
}
