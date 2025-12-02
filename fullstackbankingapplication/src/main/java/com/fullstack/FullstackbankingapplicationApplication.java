package com.fullstack;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@OpenAPIDefinition(
        info = @Info(title = "Banking APPLICATION", version = "1.0", description = "Banking application rest api documentation"),
        servers = @Server(description = "Local Tomcat 11", url = "http://localhost:8080"))
@SecurityScheme(name = "Bearer Auth", description = "Provide JWT Token", scheme = "Bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)

public class FullstackbankingapplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullstackbankingapplicationApplication.class, args);
	}

}
