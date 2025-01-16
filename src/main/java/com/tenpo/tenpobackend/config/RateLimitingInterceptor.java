package com.tenpo.tenpobackend.config;

import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@Slf4j
public class RateLimitingInterceptor implements HandlerInterceptor {

    // A concurrent map to store the buckets of each client identified by their clientId.
    private final ConcurrentMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // Gets the unique identifier of the client, in this case the IP address.
        String clientId = getClientId(request);
        // Gets or creates a new bucket for the client.
        Bucket bucket = buckets.computeIfAbsent(clientId, this::createNewBucket);

        // Try to consume 1 token from the bucket.
        if (bucket.tryConsume(1)) {
            // If tokens are available, allow the request.
            return true;
        } else {
            // If no tokens are available, respond with a status of 429 (Too Many Requests).
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests");
            return false;
        }
    }

    private String getClientId(HttpServletRequest request) {
        // get the client's IP address
        return request.getRemoteAddr();
    }

    private Bucket createNewBucket(String clientId) {
        // Create a new bucket with a limit of 3 tokens per minute.
        return Bucket.builder()
                .addLimit(limit -> limit.capacity(3).refillIntervally(3, Duration.ofMinutes(1)))
                .build();
    }
}
