package kz.qonaqzhai.eventservice.config;

import kz.qonaqzhai.eventservice.security.InternalGatewayAuthFilter;
import kz.qonaqzhai.eventservice.config.InternalGatewayAuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(InternalGatewayAuthProperties.class)
public class SecurityConfig {

    private final InternalGatewayAuthProperties internalGatewayAuthProperties;

    public SecurityConfig(InternalGatewayAuthProperties internalGatewayAuthProperties) {
        this.internalGatewayAuthProperties = internalGatewayAuthProperties;
    }

    // Этот фильтр извлекает username из заголовка, добавленного шлюзом
    public static class GatewayUsernameFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {

            // Шлюз добавляет заголовок "username" после проверки токена
            String username = request.getHeader("username");

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Создаем простой UserDetails объект на основе username из заголовка
                // В реальной системе здесь можно загрузить дополнительные данные о пользователе из кэша или другого сервиса
                UserDetails userDetails = new User(username, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

                // Устанавливаем аутентификацию в контекст
                var authentication = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Отключаем CSRF для REST API
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new InternalGatewayAuthFilter(internalGatewayAuthProperties), UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(new GatewayUsernameFilter(), InternalGatewayAuthFilter.class) // Добавляем наш фильтр
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/actuator/**").permitAll() // Разрешить доступ к эндпоинтам Actuator (опционально)
                .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
            );

        return http.build();
    }
}