package kz.qonaqzhai.eventservice;

import kz.qonaqzhai.eventservice.config.InternalGatewayAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // Позволяет сервису регистрироваться в Eureka
@EnableConfigurationProperties(InternalGatewayAuthProperties.class)
public class EventServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventServiceApplication.class, args);
	}

}