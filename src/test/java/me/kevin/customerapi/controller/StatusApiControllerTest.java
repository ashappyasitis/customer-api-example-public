package me.kevin.customerapi.controller;

import me.kevin.customerapi.configuration.ProjectInfoComponent;
import me.kevin.customerapi.model.dto.GenericResponse;
import me.kevin.customerapi.model.status.dto.HealthResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class StatusApiControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private static String HEALTH_API_PATH = "/api/status/health";

    @Test
    @DisplayName("Health API 호출 - 서버 상태 정상일 때 - OK 응답 확인")
    void getHealthApi_whenServerIsOk_receiveOk() {
        // given
        // when
        ResponseEntity<Object> responseEntity = testRestTemplate.getForEntity(HEALTH_API_PATH, Object.class);
        // then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Health API 호출 - 서버 상태 정상일 때 - OK 응답 메시지 확인")
    void getHealthApi_whenServerIsOk_receiveMessage() {
        // given
        // when
        ResponseEntity<GenericResponse> responseEntity = testRestTemplate.exchange(HEALTH_API_PATH, HttpMethod.GET, null, new ParameterizedTypeReference<GenericResponse>() {
        });
        GenericResponse genericResponse = responseEntity.getBody();

        // then
        Assertions.assertThat(genericResponse.getMessage()).isEqualTo("서버 상태 조회가 완료되었습니다.");
    }

    @Test
    @DisplayName("Health API 호출 - 서버 상태 정상일 때 - Health Response 확인")
    public void getHealth_whenServerIsValid_receiveHealthResponse() {
        // given
        // when
        ResponseEntity<HealthResponse> responseEntity = testRestTemplate.exchange(HEALTH_API_PATH, HttpMethod.GET, null, new ParameterizedTypeReference<HealthResponse>() {
        });
        HealthResponse healthResponse = responseEntity.getBody();

        // then
        Assertions.assertThat(healthResponse).isNotNull();
        Assertions.assertThat(healthResponse.getProjectName()).isEqualTo(ProjectInfoComponent.NAME);
        Assertions.assertThat(healthResponse.getVersion()).isEqualTo(ProjectInfoComponent.VERSION);
        Assertions.assertThat(healthResponse.getDescription()).isEqualTo(ProjectInfoComponent.DESCRIPTION);
    }


}