package com.rental.setu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * Custom HTTP Interceptor that logs every request before
 * forwarding it to the controller.
 * Simulates rate-limiting by generating a random requestId
 * and returning an exception if the requestId is divisible by 5.
 *
 * @author jimil
 */
public class CustomRequestInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(CustomRequestInterceptor.class);

    /**
     * Over-riden preHandle() method that is called for every request
     * before it is forwarded to the controller
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().contains("swagger") || request.getRequestURI().contains("api-docs")) {
            return true;
        }
        int requestId = new Random().nextInt(1000-1)+1;
        LOG.info("preHandle(): requestId(" + requestId + ") - (" + request.getMethod() + ", " + request.getRequestURI() + ")");
        if(requestId%5 == 0) {
            response.sendError(429, "Rate limit exceeded");
            throw new Exception("Rate limit exceeded");
        }
        return true;
    }

}
