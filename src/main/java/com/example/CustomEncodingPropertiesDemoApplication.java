package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class CustomEncodingPropertiesDemoApplication {

    @Value("${message}")
    private String messageFromStandardPropertyLoader;

    @Value("#{Utf8PropertiesFactoryBean['message']}")
    private String messageFromCustomPropertyLoader;

	public static void main(String[] args) {
        SpringApplication.run(CustomEncodingPropertiesDemoApplication.class, args);
    }

	@Bean
	public CommandLineRunner runner() {
        return (args) -> {

            // Approach 1: convert String during runtime from standard property charset("ISO-8859-1")
            System.out.println(new String(messageFromStandardPropertyLoader.getBytes("ISO-8859-1"), "UTF-8"));

            // Approach 2: use custom PropertiesFactoryBean
            System.out.println(messageFromCustomPropertyLoader);
        };
	}

	@Bean(name="Utf8PropertiesFactoryBean")
    public PropertiesFactoryBean customizePropertyEncoding() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("application.properties"));
        propertiesFactoryBean.setFileEncoding("UTF-8");
        return propertiesFactoryBean;
    }
}
