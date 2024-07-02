package com.shankarstudy.todo.config;

import com.shankarstudy.todo.security.JwtAuthenticationEntryPoint;
import com.shankarstudy.todo.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;    // It will load users and roles from actual DB automatically

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SpringSecurityConfig(UserDetailsService theUserDetailsService,
                                JwtAuthenticationEntryPoint theJwtAuthenticationEntryPoint,
                                JwtAuthenticationFilter theJwtAuthenticationFilter) {
        this.userDetailsService = theUserDetailsService;
        this.jwtAuthenticationEntryPoint = theJwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = theJwtAuthenticationFilter;
    }

    // Create the bean for authentication manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // You have entered a password with no PasswordEncoder. Need to implement password encoder
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // 1.1 Basic Authorization for any role
//        httpSecurity.csrf((csrf) -> csrf.disable())
//                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults());
//
//        return httpSecurity.build();

        // 1.2 Role Based Authorization
//        httpSecurity.csrf((csrf) -> csrf.disable())
//                .authorizeHttpRequests((authorize) -> {
//                    // Need to pass credentials and with correct role only, the response would be shown
////                    authorize.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");
////                    authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
////                    authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
////                    authorize.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER");
////                    authorize.requestMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole("ADMIN", "USER");
//
//                    // public expose GET APIs, no need to pass any credential for this
//                    authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll();
//
//                    authorize.anyRequest().authenticated();
//                })
//                .httpBasic(Customizer.withDefaults());

        // 2. Method Level Security, for this annotate class with @EnableMethodSecurity annotation which enables
        // @PreAuthorization at REST controller, we can add it at method level which will be verified here
        httpSecurity.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/api/auth/**").permitAll();        // Expose all endpoints for auth
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();   // Expose preflight requests
                    authorize.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults());

        httpSecurity.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint));

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    // Store in memory authentication with users and their roles by manually creating UserDetailsService
//    @Bean
//    public UserDetailsService userDetailsService() {
////        UserDetails shan = User.builder().username("shan").password("shan").roles("USER").build();
//        UserDetails shan = User.builder()
//                .username("shan")
//                .password(passwordEncoder().encode("shan"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(shan, admin);
//    }

    // Use Database Authentication
    // Here, With Spring Security 6 onwards, No need to provide UserDetailsService reference to Authentication Manager
    // Whenever we inject UserDetailsService in Spring Security Config Class, then spring security will automatically
    // call its loadUserByUsername()
}
