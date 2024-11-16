package choorai.retrospect.support;

import choorai.retrospect.user.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class MockSecurityContextFactory implements WithSecurityContextFactory<MockUser> {

    @Override
    public SecurityContext createSecurityContext(MockUser annotation) {
        final SecurityContext context = SecurityContextHolder.createEmptyContext();

        final User user = new User(annotation.email(), annotation.password(), annotation.name(), "", "", "",
                                   annotation.role());
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
            user, null, user.getAuthorities());

        context.setAuthentication(token);
        return context;
    }
}
