package com.pseudowasabi.copycat_webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CopycatWebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CopycatWebserviceApplication.class, args);
	}

}
