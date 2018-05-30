package com.wwjd.canal.canaltest;

import com.wwjd.starter.canal.annotation.EnableCanalClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCanalClient
@MapperScan(basePackages = "com.wwjd.canal.canaltest.mapper")

public class CanalTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CanalTestApplication.class, args);
	}
}
