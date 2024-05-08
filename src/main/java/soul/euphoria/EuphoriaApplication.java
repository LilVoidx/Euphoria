package soul.euphoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = {"soul.euphoria.controllers", "soul.euphoria.services","soul.euphoria.dto","soul.euphoria.repositories" , "soul.euphoria.security", "soul.euphoria.models"})
public class EuphoriaApplication implements ApplicationListener<ContextRefreshedEvent> {

	@Bean
	public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(EuphoriaApplication.class, args);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		try {
			Runtime.getRuntime().exec("xdg-open http://localhost:8069/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
