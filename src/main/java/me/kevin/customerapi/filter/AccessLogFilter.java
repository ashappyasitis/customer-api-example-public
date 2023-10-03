package me.kevin.customerapi.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kevin.customerapi.model.log.dto.AccessLog;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.UUID;

import static me.kevin.customerapi.model.log.dto.AccessLog.newAccessLog;
import static me.kevin.customerapi.utility.HttpUtils.isNotAllowedUri;
import static me.kevin.customerapi.utility.MapperUtil.toJson;


@Slf4j
@Component
@RequiredArgsConstructor
public class AccessLogFilter implements Filter {
    public static final String TRACE_ID = "TraceId";


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            MDC.put(TRACE_ID, UUID.randomUUID().toString());

            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;

            if (isNotAllowedUri(request.getServletPath())) {
                chain.doFilter(req, resp);
                return;
            }

            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
            AccessLog accessLog = newAccessLog(request);

            accessLog.begin();
            chain.doFilter(requestWrapper, responseWrapper);
            accessLog.end(requestWrapper, responseWrapper);

            log.info("HTTP_ACCESS_LOG_FILTER: {}", toJson(accessLog));
        } finally {
            MDC.remove(TRACE_ID);
        }
    }
}
