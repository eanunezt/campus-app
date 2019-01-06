package co.edu.itli.campus;



import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import co.edu.itli.campus.configurations.DefaultRolesPrefixPostProcessor;

@SpringBootApplication
public class CampusApplication {
 
    private static final Logger LOG = LoggerFactory.getLogger(CampusApplication.class);
 
	  

	public static void main(String[] args) {
		
		SpringApplication.run(CampusApplication.class, args);
		
	}
	
	 @Bean
	    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
	        return args -> {
	          

	        };
	    }
	
	 @Bean
	  public ModelMapper modelMapper() {
	    return new ModelMapper();
	  }
	 
     @Bean
	 public DefaultRolesPrefixPostProcessor defaultRolesPrefixPostProcessor() {
	     return new DefaultRolesPrefixPostProcessor(); // Remove the ROLE_ prefix
	 }
    


	 
	 
}
