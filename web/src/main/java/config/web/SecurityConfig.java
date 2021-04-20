package config.web;

import entity.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import service.security.impl.CustomUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@ComponentScan("service.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService ;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.POST, "/api/users");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/api/users/**").hasAnyAuthority((Role.MASTER.name()),
                (Role.ADMIN.name()), (Role.USER.name()))
                .antMatchers(HttpMethod.GET, "/api/users").hasAnyAuthority((Role.ADMIN.name()), (Role.MASTER.name()))
                .antMatchers(HttpMethod.GET, "/api/users/profile").permitAll()
                .antMatchers("/api/users/{\\\\d+}/price").hasAnyAuthority((Role.ADMIN.name()), (Role.MASTER.name()))

                .antMatchers(HttpMethod.POST, "/api/requests/vacation").hasAnyAuthority((Role.ADMIN.name()),
                (Role.MASTER.name()))
                .antMatchers(HttpMethod.GET, "/api/requests").hasAnyAuthority((Role.ADMIN.name()), (Role.MASTER.name()))
                .antMatchers(HttpMethod.PUT, "/api/requests/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/requests/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/requests/profile").permitAll()

                .antMatchers(HttpMethod.GET, "/api/records").hasAnyAuthority((Role.ADMIN.name()), (Role.MASTER.name()))
                .antMatchers(HttpMethod.PUT, "/api/records/**").hasAnyAuthority((Role.ADMIN.name()), (Role.MASTER.name()))
                .antMatchers(HttpMethod.POST, "/api/records/**").hasAnyAuthority((Role.ADMIN.name()), (Role.MASTER.name()))
                .antMatchers(HttpMethod.GET, "/api/records/profile").permitAll()

                .antMatchers(HttpMethod.GET, "/api/appointments/master/**").hasAnyAuthority((Role.ADMIN.name()), (Role.MASTER.name()))
                .antMatchers(HttpMethod.GET, "/api/appointments/**").permitAll()

                .antMatchers(HttpMethod.DELETE).hasAuthority((Role.ADMIN.name()))

                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
