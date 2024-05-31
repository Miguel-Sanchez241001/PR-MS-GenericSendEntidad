package com.wgeneric.microservices;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesApplication.class, args);
	}
	@Bean
	public GroupedOpenApi myApi() {
		return GroupedOpenApi.builder()
			.group("Multi Servicio")
			.pathsToMatch("/api/v1/*","/api/entidad/**" )
			.build();
	}
}
