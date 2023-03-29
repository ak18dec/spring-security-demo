package com.ankit.demospringauth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GoogleCloudAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());

        // We fetch user from Google API "in theory"
        User user = getUserFromGoogleCloud(username, password);

        if(user != null) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            return authenticationToken;
        }

        throw new BadCredentialsException("Error!!!");
    }

    // Let's assume Google API will return the user in this method.
    private User getUserFromGoogleCloud(String username, String password) {
        Map<String, String> users = new HashMap<>();
        users.put("martin", "123");
        if (users.get(username) != null){
            return new User(username, password, Collections.emptyList());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
