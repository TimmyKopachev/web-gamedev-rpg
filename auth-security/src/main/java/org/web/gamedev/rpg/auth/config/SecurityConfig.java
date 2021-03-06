package org.web.gamedev.rpg.auth.config;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  public static final String ROLE_PREFIX = "ROLE_";

  @Autowired
  @Qualifier("userDetailsAuthenticationProvider")
  private AbstractUserDetailsAuthenticationProvider userDetailsAuthenticationProvider;

  @Autowired private JwtRequestFilter jwtRequestFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public RoleVoter roleVoter() {
    RoleVoter roleVoter = new RoleVoter();
    roleVoter.setRolePrefix(ROLE_PREFIX);
    return roleVoter;
  }

  @Bean
  public AuthenticatedVoter authenticatedVoter() {
    return new AuthenticatedVoter();
  }

  @Bean
  public AffirmativeBased accessDecisionManager(
      RoleVoter roleVoter, AuthenticatedVoter authenticatedVoter) {
    return new AffirmativeBased(List.of(roleVoter, authenticatedVoter));
  }

  @Bean
  @Primary
  public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
    var defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
    defaultWebSecurityExpressionHandler.setDefaultRolePrefix(ROLE_PREFIX);
    return defaultWebSecurityExpressionHandler;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(userDetailsAuthenticationProvider);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    disableCheckingEndpoint("/h2-console/**", http);
    disableCheckingEndpoint("/actuator/**", http);

    http.csrf().disable();
    http.httpBasic().disable();
    http.headers().frameOptions().disable();
    http.authorizeRequests()
        .antMatchers("/error*")
        .permitAll()
        .antMatchers("/login*")
        .permitAll()
        .antMatchers("/")
        .permitAll()
        .antMatchers("/auth")
        .permitAll() // ?
        .antMatchers("/sign-in")
        .permitAll()
        .antMatchers("/sign-up")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/refresh-token")
        .permitAll()
        .antMatchers("/authenticated/**")
        .authenticated()
        .antMatchers("/sign-out/**")
        .authenticated()
        .antMatchers("/admin/**")
        .hasRole("ADMIN")
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.httpBasic()
        .and()
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  private void disableCheckingEndpoint(String url, HttpSecurity http) throws Exception {
    http.csrf().ignoringAntMatchers(url);
    http.authorizeRequests().antMatchers(url).permitAll();
  }
}
