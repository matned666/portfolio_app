package eu.mrndesign.matned.portfolioapp.configuration;

import eu.mrndesign.matned.portfolioapp.model.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final String secretKey;
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;


    public WebSecurityConfig(String secretKey,
                             DataSource dataSource,
                             PasswordEncoder passwordEncoder) {
        this.secretKey = secretKey;
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/about").permitAll()
                .antMatchers("/contact").permitAll()
                .antMatchers("/token/*", "/resetPassword").permitAll()
                .antMatchers("/search-graphic", "/search-project").permitAll()
                .antMatchers("/graphics", "/graphics/**").permitAll()
                .antMatchers("/projects", "/projects/**").permitAll()
                .antMatchers("/contact").permitAll()
                .antMatchers("/register/**").permitAll()

//                Front elements
                .antMatchers("/cv_pdf").permitAll()
                .antMatchers("/nav").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/sound/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/pdf/**").permitAll()
                .antMatchers("/file/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/").permitAll()

//                Restricted areas
                .antMatchers("/actuator/**").hasRole(UserRole.Role.ADMIN.name())
                .antMatchers("/graphics-admin","/graphics-admin/**").hasRole(UserRole.Role.ADMIN.name())
                .antMatchers("/account").authenticated()
                .antMatchers("/account/**").hasRole(UserRole.Role.ADMIN.name())

//                all other requests are for authenticated users only
                .anyRequest().authenticated()

                .and()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())

                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login-process")
                .failureUrl("/login?error=1")
                .defaultSuccessUrl("/")

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")

                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied")

                .and()
                .logout()
                .deleteCookies("JSESSIONID")

                .and()
                .rememberMe().key(secretKey).tokenValiditySeconds(86400);  //  Session is valid 24h
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("select u.LOGIN, u.PASSWORD, 1 from USER_ENTITY u where u.LOGIN = ?")
                .authoritiesByUsernameQuery(
                        "select u.LOGIN, r.ROLE_NAME from USER_ENTITY u " +
                                "join USER_ENTITY_ROLES ur on u.ID = ur.USER_ID " +
                                "join USER_ROLE r on ur.ROLES_ID = r.ID " +
                                "where u.LOGIN = ?")
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
    }

    //cors configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
