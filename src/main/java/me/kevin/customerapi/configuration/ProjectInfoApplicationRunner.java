package me.kevin.customerapi.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProjectInfoApplicationRunner implements ApplicationRunner {
    private final ProjectInfoComponent projectInfoComponent;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        projectInfoComponent.showProjectInfo();
    }
}
