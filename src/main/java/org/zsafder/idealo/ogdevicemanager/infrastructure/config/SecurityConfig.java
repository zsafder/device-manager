package org.zsafder.idealo.ogdevicemanager.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.zsafder.idealo.ogdevicemanager.presentation.exception.CustomAccessDeniedHandler;

@Configuration
public class SecurityConfig {
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_MAINTAINER = "MAINTAINER";
    private static final String ROLE_VIEWER = "VIEWER";
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/**").hasAnyRole(ROLE_ADMIN, ROLE_MAINTAINER, ROLE_VIEWER)
                        .requestMatchers(HttpMethod.POST, "/**").hasAnyRole(ROLE_ADMIN, ROLE_MAINTAINER)
                        .requestMatchers(HttpMethod.PUT, "/**").hasAnyRole(ROLE_ADMIN, ROLE_MAINTAINER)
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole(ROLE_ADMIN)
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(new CustomAccessDeniedHandler())
                );

        return http.build();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN")
                .and()
                .withUser("maintainer1").password(passwordEncoder.encode("maintainer")).roles("MAINTAINER")
                .and()
                .withUser("maintainer2").password(passwordEncoder.encode("maintainer")).roles("MAINTAINER")
                .and()
                .withUser("viewer1").password(passwordEncoder.encode("viewer")).roles("VIEWER")
                .and()
                .withUser("viewer2").password(passwordEncoder.encode("viewer")).roles("VIEWER")
                .and()
                .withUser("viewer3").password(passwordEncoder.encode("viewer")).roles("VIEWER");
    }
}

