package com.ciphertext.opencarebackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/files/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/doctors/").hasAuthority("create-doctor")
                        .requestMatchers(HttpMethod.PUT, "/api/doctors/").hasAuthority("update-doctor")
                        .requestMatchers(HttpMethod.DELETE, "/api/doctors/").hasAuthority("delete-doctor")

                        .requestMatchers(HttpMethod.POST, "/api/hospitals/").hasAuthority("create-hospital")
                        .requestMatchers(HttpMethod.PUT, "/api/hospitals/").hasAuthority("update-hospital")
                        .requestMatchers(HttpMethod.DELETE, "/api/hospitals/").hasAuthority("delete-hospital")

                        .requestMatchers(HttpMethod.POST, "/api/social-organization/").hasAuthority("create-social-organization")
                        .requestMatchers(HttpMethod.PUT, "/api/social-organization/").hasAuthority("update-social-organization")
                        .requestMatchers(HttpMethod.DELETE, "/api/social-organization/").hasAuthority("delete-social-organization")

                        .requestMatchers(HttpMethod.POST, "/api/institutions/").hasAuthority("create-institution")
                        .requestMatchers(HttpMethod.PUT, "/api/institutions/").hasAuthority("update-institution")
                        .requestMatchers(HttpMethod.DELETE, "/api/institutions/").hasAuthority("delete-institution")

                        .requestMatchers(HttpMethod.POST, "/api/medical-tests/").hasAuthority("create-master-data")
                        .requestMatchers(HttpMethod.PUT, "/api/medical-tests/").hasAuthority("update-master-data")
                        .requestMatchers(HttpMethod.DELETE, "/api/medical-tests/").hasAuthority("delete-master-data")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthorityPrefix(""); // Removed prefix

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = grantedAuthoritiesConverter.convert(jwt);
            // Log the authorities for debugging
            authorities.forEach(auth -> System.out.println("Authority granted: " + auth.getAuthority()));
            return authorities;
        });
        return jwtAuthenticationConverter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));  // In production, restrict this to your frontend domains
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(false);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(
                        "/api/auth/**",
                        "/api-docs/**",
                        "configuration/**",
                        "/swagger*/**",
                        "/webjars/**",
                        "/swagger-ui/**",
                        "/actuator/**"
                )
                .requestMatchers(HttpMethod.GET,
                        "/api/blood-groups/**",
                        "/api/countries/**",
                        "/api/days-of-week/**",
                        "/api/degree-types/**",
                        "/api/gender/**",
                        "/api/hospital-types/**",
                        "/api/organization-types/**",
                        "/api/social-organization-types/**",
                        "/api/teacher-positions/**")
                .requestMatchers(HttpMethod.GET,
                        "/api/districts/**",
                        "/api/divisions/**",
                        "/api/upazilas/**")
                .requestMatchers(HttpMethod.GET,
                        "/api/specialities/**",
                        "/api/medical-tests/**",
                        "/api/institutions/**",
                        "/api/hospitals/**",
                        "/api/doctors/**",
                        "/api/social-organization/**");
    }
}