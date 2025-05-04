package innopolis.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
@Component
public class CustomOidcUserService extends OidcUserService {
    @Override
    public OidcUser loadUser(OidcUserRequest oidcUserRequest) {
        var user = super.loadUser(oidcUserRequest);
        Set<GrantedAuthority> authority = new HashSet<>(user.getAuthorities());
        if ("t.tim.production@gmail.com".equalsIgnoreCase(user.getEmail())) {
            authority.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return new DefaultOidcUser(authority, oidcUserRequest.getIdToken(), user.getUserInfo(), "email");
    }
}
