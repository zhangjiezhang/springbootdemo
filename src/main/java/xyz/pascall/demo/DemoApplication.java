package xyz.pascall.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication /*extends SpringBootServletInitializer*/ {

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DemoApplication.class);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
