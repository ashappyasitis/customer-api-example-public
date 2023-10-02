package me.kevin.customerapi.controller;

import me.kevin.customerapi.model.dto.GenericResponse;
import me.kevin.customerapi.model.status.dto.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class StatusApiController {
    @GetMapping("/health")
    public GenericResponse<HealthResponse> getHealth() {
        return new GenericResponse<>(
                "서버 상태 조회가 완료되었습니다.",
                new HealthResponse()
        );
    }
}
