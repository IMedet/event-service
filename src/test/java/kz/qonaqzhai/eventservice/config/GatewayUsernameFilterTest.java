package kz.qonaqzhai.eventservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GatewayUsernameFilterTest {

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void filter_setsAuthentication_whenUsernameHeaderPresent() throws Exception {
        SecurityConfig.GatewayUsernameFilter filter = new SecurityConfig.GatewayUsernameFilter();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("username")).thenReturn("alice");
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        filter.doFilter(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("alice", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(chain).doFilter(request, response);
    }

    @Test
    void filter_doesNotOverrideAuthentication_whenAlreadyPresent() throws Exception {
        SecurityConfig.GatewayUsernameFilter filter = new SecurityConfig.GatewayUsernameFilter();

        SecurityContextHolder.getContext().setAuthentication(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken("existing", null, java.util.List.of())
        );

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("username")).thenReturn("alice");

        filter.doFilter(request, response, chain);

        assertEquals("existing", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(chain).doFilter(request, response);
    }
}
