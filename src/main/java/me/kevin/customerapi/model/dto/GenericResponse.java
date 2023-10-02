package me.kevin.customerapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class GenericResponse<T> {
    private String message;
    private T data;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime responseAt;

    public GenericResponse() {
        responseAt = LocalDateTime.now();
    }

    public GenericResponse(String message, T data) {
        this();
        this.message = message;
        this.data = data;
    }
}
