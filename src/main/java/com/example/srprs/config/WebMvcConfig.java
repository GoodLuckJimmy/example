package com.example.srprs.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${aws.accessKey}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/docs/**").addResourceLocations("classpath:/docs/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        registrar.registerFormatters(registry);
    }

    @Bean
    public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
        ObjectMapper copy = objectMapper().copy();
        copy.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());
        return new MappingJackson2HttpMessageConverter(copy);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return objectMapper;
    }

    @Bean
    public RestTemplate inicisRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

//        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
//        jacksonConverter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML));
//
//        restTemplate.getMessageConverters().removeIf(converter -> converter.getClass().isAssignableFrom(MappingJackson2HttpMessageConverter.class));
//        restTemplate.getMessageConverters().add(1, jacksonConverter);

        return restTemplate;
    }

    @Bean
    public AmazonSNS amazonSNS() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        AmazonSNSClientBuilder builder = AmazonSNSClientBuilder.standard();

        return builder.withRegion(Regions.AP_NORTHEAST_1).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
    }
}
