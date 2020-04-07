package com.hqj.filter;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * mdc filter
 */
public class MDCFilter implements Filter {
    private static final String TRACE_ID = "trace-id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        if (uri.contains("favicon.ico")) {
            chain.doFilter(request, response);
        } else {
            String traceId = httpRequest.getHeader(TRACE_ID);
            if (StringUtils.isEmpty(traceId)) {
                traceId = UUID.randomUUID().toString().replace("-", "");
            }
            MDC.put(TRACE_ID, traceId);
            try {
                chain.doFilter(request, response);
            } finally {
                MDC.clear();
            }
        }
    }

}
