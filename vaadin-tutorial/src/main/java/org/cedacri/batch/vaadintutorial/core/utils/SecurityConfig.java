package org.cedacri.batch.vaadintutorial.core.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain springSecurityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests((requests) ->
                requests.requestMatchers("/vaadin/", "/vaadin/home", "/vaadin/login", "/vaadin/logout")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> {
                    form.loginPage("/vaadin/login")
                            .usernameParameter("login")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/vaadin/tutorials", true);
                })
                .logout((logout) -> {
                    logout.logoutUrl("/vaadin/logout").permitAll()
                            .logoutSuccessUrl("/vaadin/login")
                            .deleteCookies("JSESSIONID");
                })
        ;
        return http.build();
    }

}
