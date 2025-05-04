package com.example.swapSafe.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reads the "roles" claim from the JWT and turns it into ROLE_*
 * authorities understood by Spring Security.
 */
@Component
public class MyRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Object rolesObj = jwt.getClaim("roles");          // roles claim added at token creation
        if (rolesObj instanceof List<?> list) {
            return list.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r)) // ROLE_ADMIN / ROLE_CUSTOMER
                    .collect(Collectors.toSet());
        }
        return List.of();
    }
}
