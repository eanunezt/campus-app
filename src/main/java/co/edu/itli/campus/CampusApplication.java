package co.edu.itli.campus;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import co.edu.itli.campus.configurations.DefaultRolesPrefixPostProcessor;

@SpringBootApplication
public class CampusApplication {
	  

	public static void main(String[] args) {
		
		SpringApplication.run(CampusApplication.class, args);
	}
	
	 @Bean
	  public ModelMapper modelMapper() {
	    return new ModelMapper();
	  }
	 
     @Bean
	 public DefaultRolesPrefixPostProcessor defaultRolesPrefixPostProcessor() {
	     return new DefaultRolesPrefixPostProcessor(); // Remove the ROLE_ prefix
	 }
	 


	 @PostConstruct
		void init() {
			TimeZone.setDefault(TimeZone.getTimeZone("UTC")); 
		}

	 
	 
}
