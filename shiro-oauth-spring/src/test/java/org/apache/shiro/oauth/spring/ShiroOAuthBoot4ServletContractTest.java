package org.apache.shiro.oauth.spring;

import jakarta.servlet.Filter;
import org.apache.shiro.oauth.authc.OAuth2UserFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShiroOAuthBoot4ServletContractTest {

    @Test
    void oauthFilterCanRegisterWithBoot4ServletApi() {
        OAuth2UserFilter filter = new OAuth2UserFilter();
        FilterRegistrationBean<OAuth2UserFilter> registration = new FilterRegistrationBean<>(filter);

        assertTrue(Filter.class.isAssignableFrom(filter.getClass()));
        assertSame(filter, registration.getFilter());
    }
}
