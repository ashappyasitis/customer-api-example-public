package me.kevin.customerapi.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProjectInfoComponent {
    public static String NAME = "CUSTOMER-API";
    public static String VERSION = "v0.0.6";
    public static String SEQUENCE = "FB8-1";
    public static String DESCRIPTION = "Add handlers and change theirs transactional location";

    private final Environment environment;
    private final ServerProperties serverProperties;

    public void showProjectInfo() {
        log.info("");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("PROJECT_NAME: {}", NAME);
        log.info("VERSION: {}", VERSION);
        log.info("SEQUENCE: {}", SEQUENCE);
        log.info("DESCRIPTION: {}", DESCRIPTION);
        log.info("ACTIVE_PROFILE: {}", getActiveProfiles());
        log.info("SERVER_PORT: {}", serverProperties.getPort());
        log.info("TIMEZONE: {}", TimeZone.getDefault().toZoneId());
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("");
    }

    private String getActiveProfiles() {
        return String.join(",", environment.getActiveProfiles());
    }

}
