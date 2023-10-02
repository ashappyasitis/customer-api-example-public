package me.kevin.customerapi.model.status.dto;

import lombok.Getter;
import lombok.Setter;
import me.kevin.customerapi.configuration.ProjectInfoComponent;

@Getter
@Setter
public class HealthResponse {
    private final String projectName;
    private final String version;
    private final String description;

    public HealthResponse() {
        this.projectName = ProjectInfoComponent.NAME;
        this.version = ProjectInfoComponent.VERSION;
        this.description = ProjectInfoComponent.DESCRIPTION;
    }
}
