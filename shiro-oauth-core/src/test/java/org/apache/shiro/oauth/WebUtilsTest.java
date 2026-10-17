package org.apache.shiro.oauth;

import java.lang.reflect.Proxy;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebUtilsTest {

    @Test
    void forwardedAddressTakesPrecedence() {
        assertEquals("198.51.100.1", WebUtils.getRemoteAddr(request("198.51.100.1", "198.51.100.2", "198.51.100.3")));
    }

    @Test
    void realIpIsUsedWhenForwardedAddressIsEmpty() {
        assertEquals("198.51.100.2", WebUtils.getRemoteAddr(request("", "198.51.100.2", "198.51.100.3")));
    }

    @Test
    void socketAddressIsUsedWhenProxyHeadersAreAbsent() {
        assertEquals("198.51.100.3", WebUtils.getRemoteAddr(request(null, null, "198.51.100.3")));
    }

    private static HttpServletRequest request(String forwarded, String realIp, String remote) {
        return (HttpServletRequest) Proxy.newProxyInstance(
                HttpServletRequest.class.getClassLoader(),
                new Class<?>[]{HttpServletRequest.class},
                (proxy, method, args) -> {
                    if ("getHeader".equals(method.getName())) {
                        if ("X-Forwarded-For".equals(args[0])) {
                            return forwarded;
                        }
                        if ("X-Real-IP".equals(args[0])) {
                            return realIp;
                        }
                    }
                    if ("getRemoteAddr".equals(method.getName())) {
                        return remote;
                    }
                    return null;
                });
    }
}
