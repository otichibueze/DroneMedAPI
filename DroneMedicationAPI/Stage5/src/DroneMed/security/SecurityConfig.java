package DroneMed.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User.withUsername("user1")
                .password(passwordEncoder().encode("pass1"))
                .roles("USER")
                .build();
        UserDetails user2 = User.withUsername("user2")
                .password(passwordEncoder().encode("pass2"))
                .roles("ADMIN")
                .build();
        UserDetails user3 = User.withUsername("user3")
                .password(passwordEncoder().encode("pass3"))
                .roles()
                .build();


        return new InMemoryUserDetailsManager(user1, user2);
    }

     */

    //Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(matcherRegistry -> matcherRegistry
                        .antMatchers("/api/drones/**").hasRole("ADMIN")
                        .antMatchers("/api/users/get_user/*").hasAnyRole("USER", "ADMIN")
                        .antMatchers("/api/users/**").hasRole("ADMIN")
                        .antMatchers("/api/dispatch/get_dispatch/*").hasAnyRole("USER", "ADMIN")
                        .antMatchers("/api/dispatch/**").hasRole("ADMIN")
                        .antMatchers("/api/medications/get_by_name/*","/api/medications/get_all_medications","/api/medications/get_medication/*" ).permitAll()
                        .antMatchers("/api/medications/**").hasRole("ADMIN")
                        .antMatchers("/api/register_user").permitAll()
                        .anyRequest().denyAll()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(csrfConfigurer -> csrfConfigurer.disable())  // for POST requests via Postman
                .build();
    }


}
