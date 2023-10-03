package me.kevin.customerapi.model.log.dto;

import lombok.Data;

@Data
public class UserAgentInfo {

    private String deviceName;  // Apple iPhone
    private String osName;      // iOS
    private String osVersion;   // 12.3.1
    private String deviceClass; // Phone
}