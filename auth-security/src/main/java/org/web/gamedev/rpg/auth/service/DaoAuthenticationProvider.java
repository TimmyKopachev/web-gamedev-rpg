package org.web.gamedev.rpg.auth.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userDetailsAuthenticationProvider")
public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private static final String PROPERTY_CREDENTIALS_NOT_PROVIDED = "AbstractUserDetailsAuthenticationProvider.badCredentials";
    private static final String PROPERTY_CREDENTIALS_INCORRENT_PASSWORD = "AbstractUserDetailsAuthenticationProvider.password.incorrect";
    private static final String BAD_CREDENTIALS = "Bad credentials";
    private static final String USER_DETAIL_NOT_FOUND = "UserDetailsService returned null, which is an interface contract violation";

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Lazy
    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        if (usernamePasswordAuthenticationToken.getCredentials() == null) {
            logger.debug(messages.getMessage(PROPERTY_CREDENTIALS_NOT_PROVIDED, BAD_CREDENTIALS));

            throw new BadCredentialsException(messages.getMessage(PROPERTY_CREDENTIALS_NOT_PROVIDED, BAD_CREDENTIALS));
        }

        String presentedPassword = usernamePasswordAuthenticationToken.getCredentials().toString();

        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            logger.debug(messages.getMessage(PROPERTY_CREDENTIALS_INCORRENT_PASSWORD, BAD_CREDENTIALS));
            throw new BadCredentialsException(messages.getMessage(PROPERTY_CREDENTIALS_INCORRENT_PASSWORD, BAD_CREDENTIALS));
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        try {
            UserDetails loadedUser = this.userDetailsService.loadUserByUsername(username);
            //move all exception to UserDetailsService
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(USER_DETAIL_NOT_FOUND);
            }
            return loadedUser;
        } catch (UsernameNotFoundException | InternalAuthenticationServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }
}