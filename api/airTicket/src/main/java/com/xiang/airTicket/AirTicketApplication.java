package com.xiang.airTicket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
public class AirTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirTicketApplication.class, args);
	}

}
