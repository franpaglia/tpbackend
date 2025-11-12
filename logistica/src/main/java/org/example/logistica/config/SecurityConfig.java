package org.example.logistica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Swagger UI - Sin autenticación
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                        // Endpoints de Solicitudes
                        .requestMatchers(HttpMethod.GET, "/api/solicitudes").hasAnyRole("operador", "cliente")
                        .requestMatchers(HttpMethod.GET, "/api/solicitudes/**").hasAnyRole("operador", "cliente")
                        .requestMatchers(HttpMethod.POST, "/api/solicitudes").hasRole("cliente")

                        // Endpoints de Rutas
                        .requestMatchers(HttpMethod.GET, "/api/rutas/**").hasAnyRole("operador", "transportista")
                        .requestMatchers(HttpMethod.POST, "/api/rutas").hasRole("operador")

                        // Endpoints de Tramos
                        .requestMatchers(HttpMethod.GET, "/api/tramos/**").hasAnyRole("operador", "transportista")
                        .requestMatchers(HttpMethod.POST, "/api/tramos").hasRole("operador")
                        .requestMatchers(HttpMethod.PUT, "/api/tramos/*/inicio").hasRole("transportista")
                        .requestMatchers(HttpMethod.PUT, "/api/tramos/*/fin").hasRole("transportista")

                        // Endpoints de Tipos de Tramo
                        .requestMatchers(HttpMethod.GET, "/api/tipos-tramo").hasRole("operador")

                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("realm_access.roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return jwtAuthenticationConverter();
    }
}