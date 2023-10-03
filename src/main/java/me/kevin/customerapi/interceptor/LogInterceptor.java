package me.kevin.customerapi.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.kevin.customerapi.utility.HttpUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import static me.kevin.customerapi.utility.HttpUtils.isAllowedUri;
import static me.kevin.customerapi.utility.MapperUtil.toJson;

@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isAllowedUri(request.getServletPath())) {
            log.info("{} {}, params: {}",
                    request.getMethod(),
                    request.getServletPath(),
                    toJson(HttpUtils.getRequestParams((ContentCachingRequestWrapper) request))
            );
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
