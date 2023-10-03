package me.kevin.customerapi.model.log.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.kevin.customerapi.utility.HttpUtils;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static org.apache.commons.lang3.StringUtils.isNotBlank;


@Slf4j
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class AccessLog {

    private String threadId;
    private String contentType;
    private String method;
    private String uri;
    private String authorization;
    private String userAgent;
    private UserAgentInfo userAgentInfo;
    private String referer;
    private String host;
    private String clientIp;

    private String requestParams;
    private String responseParams;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    private LocalDateTime requestAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    private LocalDateTime responseAt;

    @JsonIgnore
    private Long requestAtAsTimestamp;
    private Integer elapsedTimeInMS;
    private int httpStatus;

    @JsonIgnore
    private static UserAgentAnalyzer userAgentAnalyzer = UserAgentAnalyzer.newBuilder()
            .withFields("DeviceClass", "DeviceName", "OperatingSystemName", "OperatingSystemVersion")
            .withCache(25000) // default: 10000
            .build();

    public static AccessLog newAccessLog(HttpServletRequest request) {
        AccessLog accessLog = new AccessLog();
        accessLog.setThreadId(Thread.currentThread().getName());
        accessLog.setAuthorization(request.getHeader("Authorization"));
        accessLog.setUserAgent(request.getHeader("user-agent"));
        accessLog.setReferer(request.getHeader("referer"));
        accessLog.setHost(request.getHeader("host"));
        accessLog.setClientIp(HttpUtils.getIpAddress(request));
        accessLog.setMethod(request.getMethod());
        accessLog.setUri(request.getRequestURI());
        accessLog.setContentType(request.getContentType());

        if (isNotBlank(accessLog.getUserAgent())) {
            UserAgent agent = userAgentAnalyzer.parse(accessLog.getUserAgent());
            UserAgentInfo userAgentInfo = new UserAgentInfo();
            userAgentInfo.setDeviceClass(agent.getValue("DeviceClass"));
            userAgentInfo.setDeviceName(agent.getValue("DeviceName"));
            userAgentInfo.setOsName(agent.getValue("OperatingSystemName"));
            userAgentInfo.setOsVersion(agent.getValue("OperatingSystemVersion"));
            accessLog.setUserAgentInfo(userAgentInfo);
        }

        return accessLog;
    }

    public void begin() {
        requestAt = LocalDateTime.now();
        requestAtAsTimestamp = System.currentTimeMillis();
    }

    public void end(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper) {
        responseAt = LocalDateTime.now();
        elapsedTimeInMS = (int) (System.currentTimeMillis() - requestAtAsTimestamp);
        httpStatus = responseWrapper.getStatus();
        requestParams = cutStringAtLimitLength(HttpUtils.getRequestParams(requestWrapper), 4096);
        responseParams = cutStringAtLimitLength(HttpUtils.getResponseParams(responseWrapper), 4096);
    }

    private String cutStringAtLimitLength(String str, int maxLength) {
        StringBuilder sb = new StringBuilder();
        sb.append(str, 0, Math.min(str.length(), maxLength));
        if (str.length() > maxLength) {
            sb.append("[...OVER MAXIMUM LENGTH...]");
        }
        return sb.toString();
    }
}
