package org.web.gamedev.rpg.forum.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.web.gamedev.rpg.forum.config.filter.JwtRequestFilter;

import java.util.Arrays;

@Slf4j
@EnableWebSecurity//(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final int TWO_WEEKS = 86400;
    public static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    @Qualifier("userDetailsAuthenticationProvider")
    private AbstractUserDetailsAuthenticationProvider userDetailsAuthenticationProvider;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Bean("passwordEncoder")
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("roleVoter")
    public RoleVoter getRoleVoter() {
        RoleVoter roleVoter = new RoleVoter();
        roleVoter.setRolePrefix(ROLE_PREFIX);// set roles as it's from Services/DAO/DB
        return roleVoter;
    }

    //https://docs.spring.io/spring-security/site/docs/4.2.4.RELEASE/apidocs/org/springframework/security/access/vote/AuthenticatedVoter.html
    @Bean("authenticatedVoter")
    public AuthenticatedVoter getAuthenticatedVoter() {
        AuthenticatedVoter authenticatedVoter = new AuthenticatedVoter();
        return authenticatedVoter;
    }

    @Bean("accessDecisionManager")
    public AffirmativeBased getAccessDecisionManager(RoleVoter roleVoter, AuthenticatedVoter authenticatedVoter) {
        AffirmativeBased accessDecisionManager = new AffirmativeBased(Arrays.asList(roleVoter, authenticatedVoter));
        accessDecisionManager.getDecisionVoters();
        return accessDecisionManager;
    }

    @Bean("webSecurityExpressionHandler")
    //use the same name as spring boot has: localhost:8080/actuator/beans to override it by own
    public DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setDefaultRolePrefix(ROLE_PREFIX);
        return defaultWebSecurityExpressionHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userDetailsAuthenticationProvider);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        disableCheckingEndpoint("/h2/**", http);
        disableCheckingEndpoint("/actuator/**", http);

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http
                .authorizeRequests()
                .antMatchers("/error*").permitAll()
                .antMatchers("/login*").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/auth").permitAll()
                .antMatchers("/authenticated/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/authenticated")
                .failureForwardUrl("/error")
                .failureUrl("/login?error")
                .and()
                .logout().logoutSuccessUrl("/login")
                .and()
                .exceptionHandling()
                //.authenticationEntryPoint(authenticationEntryPoint)
                /*
                to check how w/o authenticationEntryPoint works go to:
                localhost:8081/auth
                localhost:8081/admin
                 */
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);//STATELESS


        http.httpBasic().and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    private void disableCheckingEndpoint(String url, HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers(url);
        http.authorizeRequests().antMatchers(url).permitAll();
    }


}
