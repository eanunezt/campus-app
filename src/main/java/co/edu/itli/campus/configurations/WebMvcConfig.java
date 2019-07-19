package co.edu.itli.campus.configurations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by eanunezt on 11/12/18.
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	String crossorigen=System.getenv("ENV_CROSS_ORIGEN");
        registry.addMapping("/**")
        		.allowedHeaders("*"/*"Host",
                        "User-Agent",
                        "X-Requested-With",
                        "Accept",
                        "Accept-Language",
                        "Accept-Encoding",
                        "Authorization",
                        "Referer",
                        "Connection",
                        "Content-Type"*/)
                .allowedOrigins("*"/*crossorigen*/)
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .maxAge(MAX_AGE_SECS);
        
    }
}
