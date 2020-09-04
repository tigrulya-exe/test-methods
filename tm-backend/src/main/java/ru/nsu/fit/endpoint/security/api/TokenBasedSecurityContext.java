package ru.nsu.fit.endpoint.security.api;

import ru.nsu.fit.endpoint.security.domain.Authority;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * {@link SecurityContext} implementation for token-based authentication.
 */
public class TokenBasedSecurityContext implements SecurityContext {
    private final AuthenticationTokenDetails authenticationTokenDetails;
    private final AuthenticatedUserDetails authenticatedUserDetails;
    private final boolean secure;

    public TokenBasedSecurityContext(
            AuthenticatedUserDetails authenticatedUserDetails,
            AuthenticationTokenDetails authenticationTokenDetails,
            boolean secure) {
        this.authenticatedUserDetails = authenticatedUserDetails;
        this.authenticationTokenDetails = authenticationTokenDetails;
        this.secure = secure;
    }

    @Override
    public Principal getUserPrincipal() {
        return authenticatedUserDetails;
    }

    @Override
    public boolean isUserInRole(String s) {
        return authenticationTokenDetails.getAuthorities().contains(Authority.valueOf(s));
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer";
    }

    public AuthenticationTokenDetails getAuthenticationTokenDetails() {
        return authenticationTokenDetails;
    }
}
