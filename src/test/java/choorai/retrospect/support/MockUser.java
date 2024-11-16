package choorai.retrospect.support;

import choorai.retrospect.user.entity.value.Role;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory =  MockSecurityContextFactory.class)
public @interface MockUser {

    String email() default "test@test.com";

    String password() default "afajkldfa131";

    String name() default "테스터";

    Role role() default Role.USER;

}
