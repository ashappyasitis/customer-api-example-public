package me.kevin.customerapi.utility;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class HttpUtils {

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null && ip.contains(",")) {
            return ip.split(",")[0];
        }

        return ip;
    }

    public static boolean isAllowedUri(String url) {
        return url.startsWith("/api/") && !url.startsWith("/api/status");
    }

    public static boolean isNotAllowedUri(String url) {
        return !isAllowedUri(url);
    }

    public static String getRequestParams(ContentCachingRequestWrapper requestWrapper) {
        String jsonOrNull = getJsonOrNull(requestWrapper);
        if (jsonOrNull != null) {
            return jsonOrNull;
        }
        String parametersOrNull = getParametersOrNull(requestWrapper);
        if (parametersOrNull != null) {
            return parametersOrNull;
        }

        return "-";
    }

    public static String getResponseParams(ContentCachingResponseWrapper responseWrapper) {
        responseWrapper.setCharacterEncoding(CharEncoding.UTF_8);
        byte[] buf = responseWrapper.getContentAsByteArray();
        if (ArrayUtils.isEmpty(buf)) {
            return "-";
        }

        try {
            String s = new String(buf, 0, buf.length, responseWrapper.getCharacterEncoding());
            responseWrapper.copyBodyToResponse();
            return s;
        } catch (Exception e) { // UnsupportedEncodingException | IOException
            return "-";
        }
    }

    private static String getParametersOrNull(ContentCachingRequestWrapper requestWrapper) {
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> form = requestWrapper.getParameterMap();
        if (MapUtils.isEmpty(form)) {
            return null;
        }
        for (Iterator<String> nameIterator = form.keySet().iterator(); nameIterator.hasNext(); ) {
            String name = nameIterator.next();
            List<String> values = Arrays.asList(form.get(name));
            for (Iterator<String> valueIterator = values.iterator(); valueIterator.hasNext(); ) {
                String value = valueIterator.next();
                try {
                    sb.append(URLDecoder.decode(name, requestWrapper.getCharacterEncoding()));
                    if (value != null) {
                        sb.append('=');
                        sb.append(URLDecoder.decode(value, requestWrapper.getCharacterEncoding()));
                        if (valueIterator.hasNext()) {
                            sb.append('&');
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
            }
            if (nameIterator.hasNext()) {
                sb.append('&');
            }
        }
        return sb.toString();
    }


    private static String getJsonOrNull(ContentCachingRequestWrapper requestWrapper) {
        if (requestWrapper.getContentType() != null
                && requestWrapper.getContentType().contains(APPLICATION_JSON_VALUE)
                && (POST.matches(requestWrapper.getMethod())
                || PUT.matches(requestWrapper.getMethod())
                || PATCH.matches(requestWrapper.getMethod()))
        ) {
            byte[] buf = requestWrapper.getContentAsByteArray();
            if (ArrayUtils.isEmpty(buf)) {
                return null;
            }

            try {
                return removeTabAndLineFeedAndNewLine(new String(buf, 0, buf.length, requestWrapper.getCharacterEncoding()));
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }

        return null;
    }

    private static String removeTabAndLineFeedAndNewLine(String source) {
        return source.replaceAll("\\r", "").replaceAll("\\n", "").replaceAll("\\t", "");
    }
}
