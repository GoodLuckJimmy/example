package com.example.srprs.member.domain;

import com.example.srprs.config.WithMockCustomUserSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface  WithMockMember {
    long no() default 123L;

    String id() default "bp@bp.com";

    String name() default "bp";

    String phoneNumber() default "0101231234";

    String password() default "1234";

    String roles() default "CUSTOMER";
}
