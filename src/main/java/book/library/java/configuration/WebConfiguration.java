package book.library.java.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "book.library")
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/");
        registry.addResourceHandler("*.css").addResourceLocations("/");
        registry.addResourceHandler("*.js").addResourceLocations("/");
    }

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://127.0.0.1:8080")
            .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
            .allowedHeaders("Authorization", "Content-Type").exposedHeaders("custom-header1", "custom-header2")
            .allowCredentials(false).maxAge(4800);
    }*/
}
