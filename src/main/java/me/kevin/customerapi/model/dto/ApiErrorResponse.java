package me.kevin.customerapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kevin.customerapi.model.valueobject.ErrorDetail;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
    private String message;
    private String url;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime errorAt;
    private List<ErrorDetail> errors;

    @Builder
    public ApiErrorResponse(String message, String url, LocalDateTime errorAt, List<ErrorDetail> errors) {
        this.message = message;
        this.url = url;
        this.errorAt = errorAt;
        this.errors = errors;
    }
}
