package com.example.FoodDeliveryApp.config;

import com.example.FoodDeliveryApp.security.CustomLoginSuccessHandler;
import com.example.FoodDeliveryApp.security.CustomUserDetailsService;
import com.example.FoodDeliveryApp.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

        private final CustomUserDetailsService customUserDetailsService;
        private final CustomLoginSuccessHandler successHandler;
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(
                        CustomUserDetailsService customUserDetailsService,
                        CustomLoginSuccessHandler successHandler,
                        JwtAuthenticationFilter jwtAuthenticationFilter) {

                this.customUserDetailsService = customUserDetailsService;
                this.successHandler = successHandler;
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {

                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration configuration)
                        throws Exception {

                return configuration.getAuthenticationManager();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:5173"));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowCredentials(true);
                configuration.setExposedHeaders(List.of("Authorization"));

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity http)
                        throws Exception {

                http
                                .cors(Customizer.withDefaults())
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth

                                                .requestMatchers(
                                                                "/register",
                                                                "/login",
                                                                "/api/auth/login",
                                                                "/css/**",
                                                                "/js/**")
                                                .permitAll()

                                                .requestMatchers(
                                                                "/admin/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(
                                                                "/user/**")
                                                .hasRole("USER")

                                                .requestMatchers(
                                                                "/delivery/**")
                                                .hasRole("DELIVERY_PARTNER")

                                                .requestMatchers(
                                                                "/api/admin/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(
                                                                "/api/delivery/**")
                                                .hasRole("DELIVERY_PARTNER")

                                                .requestMatchers(
                                                                "/api/**")
                                                .authenticated()

                                                .anyRequest()
                                                .authenticated())

                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .successHandler(successHandler)
                                                .permitAll())

                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login?logout")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll())
                                .addFilterBefore(
                                                jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}