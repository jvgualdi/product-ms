package tec.jvgualdi.product_ms.configuration;

import jakarta.ws.rs.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import tec.jvgualdi.product_ms.security.RequestFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final RequestFilter securityFilter;

    public SecurityConfiguration(RequestFilter securityFilter) {
        this.securityFilter = securityFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth ->
                    auth
                            .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/product/**").hasRole("EMPLOYEE")
                            .requestMatchers(HttpMethod.PUT, "/product/**").hasRole("EMPLOYEE")
                            .requestMatchers(HttpMethod.DELETE, "/product/**").hasRole("EMPLOYEE")
                            .anyRequest().authenticated()
            ).addFilterBefore(securityFilter, RequestFilter.class);
        return http.build();
    }

}
