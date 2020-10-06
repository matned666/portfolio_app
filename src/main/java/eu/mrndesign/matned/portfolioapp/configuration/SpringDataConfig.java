package eu.mrndesign.matned.portfolioapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import javax.servlet.MultipartConfigElement;

@Configuration
@PropertySource("classpath:application.properties")
public class SpringDataConfig {

    @Value("${spring.mail.username}")
    private String incomingMailBox;

    @Value("${google.recaptcha.key.secret}")
    private String captchaSecretKey;

    @Value("${google.recaptcha.key.site}")
    private String captchaSiteKey;

    @Value("${secret.key.for.session.token}")
    private String secretKey;

    @Value("${www.domain.url}")
    private String webUrl;

    @Value("${token.expiry.minutes}")
    private Integer tokenExpMinutes;

    @Value("${token.length.signs}")
    private Integer tokenLength;

    @Value("${default.admin.username}")
    private String defaultAdminLogin;

    @Value("${default.admin.password}")
    private String defaultAdminPassword;

    @Value("${captcha.size}")
    private Integer captchaSize;

//    @Value("files.path")
//    private String filesPath;


//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        return new MultipartConfigElement(filesPath);
//    }
//
//    @Bean
//    public MultipartResolver multipartResolver() {
//        org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(1000000);
//        return multipartResolver;
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Integer tokenLength() {
        return this.tokenLength;
    }

    @Bean
    public String captchaSecretKey() {
        return this.captchaSecretKey;
    }

    @Bean
    public String captchaSiteKey() {
        return this.captchaSiteKey;
    }

    @Bean
    public String secretKey() {
        return this.secretKey;
    }

    @Bean
    public Integer tokenExpMinutes() {
        return this.tokenExpMinutes;
    }

    @Bean
    public String webUrl() {
        return this.webUrl;
    }

    @Bean
    public String incomingMailBox() { return this.incomingMailBox; }

    @Bean
    public String defaultAdminLogin() { return this.defaultAdminLogin; }

    @Bean
    public String defaultAdminPassword() { return this.defaultAdminPassword; }

    @Bean
    public Integer captchaSize() { return this.captchaSize; }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
