//package com.wll.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.time.Duration;
//import java.util.Collections;
//
///**
// * @time 2025/3/22 0:31 周六
// */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        String[] allowedPaths = {"/api/user/login", "/api/user/registry"};
//        http
//
//                .formLogin(formLogin -> formLogin
//                        .loginProcessingUrl("/api/user/login").permitAll()
//                )
////                .httpBasic(Customizer.withDefaults())
//
//                .authorizeHttpRequests(authorize -> authorize
//                                .requestMatchers(allowedPaths).permitAll()
//                        // any URL request needs to be authenticated
////                                .anyRequest().authenticated()
//                )
//                // only handle to specified pattern
//                .securityMatcher("/api/**")
//        ;
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder().username("user").password("123").roles("USER").build();
//        return new InMemoryUserDetailsManager(user);
//    }
//
////    @Bean
////    public CorsConfigurationSource corsConfigurationSource() {
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowCredentials(true);
////        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
////        configuration.setAllowedMethods(Collections.singletonList("*"));
////        configuration.setAllowedHeaders(Collections.singletonList("*"));
////        configuration.setMaxAge(Duration.ofMinutes(30));
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
//
//
////    @Bean
////    public AuthenticationProvider authenticationProvider() {
////        return new AuthenticationProvider() {
////
////            @Override
////            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
////                return null;
////            }
////
////            @Override
////            public boolean supports(Class<?> authentication) {
////                return false;
////            }
////        };
////    }
//}
