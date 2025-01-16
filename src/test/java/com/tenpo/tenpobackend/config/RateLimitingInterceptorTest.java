package com.tenpo.tenpobackend.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
class RateLimitingInterceptorTest {

    private RateLimitingInterceptor rateLimitingInterceptor;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        rateLimitingInterceptor = new RateLimitingInterceptor();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        // Mock the response writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        try {
            when(response.getWriter()).thenReturn(writer);
        } catch (Exception e) {
            fail("Setup failed to mock response writer.");
        }
    }

    @Test
    @DisplayName("Should allow request when bucket has tokens")
    void shouldAllowRequestWhenBucketHasTokens() throws Exception {
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        // First request should be allowed
        boolean result = rateLimitingInterceptor.preHandle(request, response, new Object());

        assertTrue(result);
        verify(response, never()).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
    }

    @Test
    @DisplayName("Should deny request when bucket exhausted")
    void shouldDenyRequestWhenBucketExhausted() throws Exception {
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        // Consume all available tokens
        for (int i = 0; i < 3; i++) {
            rateLimitingInterceptor.preHandle(request, response, new Object());
        }

        // Fourth request should be denied
        boolean result = rateLimitingInterceptor.preHandle(request, response, new Object());

        assertFalse(result);
        verify(response).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
    }

    @Test
    @DisplayName("Should create new bucket for each client")
    void shouldCreateNewBucketForEachClient() throws Exception {
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        // First request for client 1
        assertTrue(rateLimitingInterceptor.preHandle(request, response, new Object()));

        // Change the client IP
        when(request.getRemoteAddr()).thenReturn("192.168.1.1");

        // First request for client 2 should also be allowed
        assertTrue(rateLimitingInterceptor.preHandle(request, response, new Object()));
    }

}