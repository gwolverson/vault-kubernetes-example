package helmsmandemo.springbootapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private static final String ROLE_MANAGEMENT_USER = "MANAGEMENT_ACCESS";

  private final SecuredRoutesConfig securedRoutesConfig;

  @Value("${appsecurity.management.username}")
  private String managementUsername;

  @Value("${appsecurity.management.password}")
  private String managementPassword;

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .formLogin().disable()
        .csrf().disable()
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers(securedRoutesConfig.getUnprotected()).permitAll()
            .requestMatchers(securedRoutesConfig.getManagement()).hasRole(ROLE_MANAGEMENT_USER)
        )
        .httpBasic(withDefaults());

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user =
        User.builder()
            .username(managementUsername)
            .password(encoder().encode(managementPassword))
            .roles(ROLE_MANAGEMENT_USER)
            .build();

    return new InMemoryUserDetailsManager(user);
  }
}
